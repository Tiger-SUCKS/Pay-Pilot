package com.paypilot.controller;

import io.swagger.v3.oas.annotations.Operation;

import com.paypilot.dto.PaymentAnalyticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import com.paypilot.dto.PaymentRequest;
import com.paypilot.dto.PaymentResponse;
import com.paypilot.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Create a payment",
            description = "Creates a new payment transaction using an idempotency key."
    )
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.createPayment(
                        request,
                        idempotencyKey
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Get payments",
            description = "Returns paginated payments with optional filtering, sorting, and pagination."
    )
    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> getAllPayments(


            @RequestParam(defaultValue = "0") @Min(0) int page,

            @RequestParam(defaultValue = "10")
            @Min(1)
            @Max(100)
            int size,

            @RequestParam(required = false) String status,

            @RequestParam(required = false) String paymentMethod,

            @RequestParam(required = false) String customerType,

            @RequestParam(defaultValue = "createdAt") String sortBy,

            @RequestParam(defaultValue = "desc") String direction) {

        Page<PaymentResponse> payments =
                paymentService.getAllPayments(
                        page,
                        size,
                        status,
                        paymentMethod,
                        customerType,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(payments);
    }

    @Operation(
            summary = "Get payment analytics",
            description = "Returns payment statistics including success rate, failure rate, failure categories, and failed payment methods."
    )

    @GetMapping("/analytics")
    public ResponseEntity<PaymentAnalyticsResponse> getAnalytics() {

        PaymentAnalyticsResponse analytics =
                paymentService.getAnalytics();

        return ResponseEntity.ok(analytics);
    }

    @Operation(
            summary = "Get payment recovery recommendation",
            description = "Returns a recovery recommendation based on the payment failure reason."
    )
    @GetMapping("/{id}/recommendation")
    public ResponseEntity<String> getRecommendation(
            @PathVariable Long id) {

        String recommendation = paymentService.getRecommendation(id);

        return ResponseEntity.ok(recommendation);
    }
}