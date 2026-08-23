package com.paypilot.controller;

import com.paypilot.entity.AnalyticsResponse;
import com.paypilot.entity.Payment;
import com.paypilot.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }
    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "Payment deleted successfully";
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/analytics")
    public AnalyticsResponse getAnalytics() {

        long total = paymentService.getTotalPayments();
        long failed = paymentService.getFailedPayments();
        long successful = paymentService.getSuccessfulPayments();
        double failureRate = paymentService.getFailureRate();

        return new AnalyticsResponse(
                total,
                successful,
                failed,
                failureRate
        );
    }

    @GetMapping("/{id}/recommendation")
    public String getRecommendation(@PathVariable Long id) {

        Payment payment = paymentService.getPaymentById(id);

        return paymentService.getRecommendation(payment);
    }
}