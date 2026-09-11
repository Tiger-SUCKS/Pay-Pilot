package com.paypilot.service;

import org.springframework.transaction.annotation.Transactional;
import com.paypilot.enums.PaymentStatus;
import com.paypilot.dto.PaymentAnalyticsResponse;
import com.paypilot.retry.RetryPolicy;
import com.paypilot.retry.RetryPolicyEngine;
import com.paypilot.enums.FailureCategory;
import java.util.LinkedHashMap;
import java.util.Map;
import com.paypilot.recovery.RecoveryStrategyEngine;
import com.paypilot.analyzer.FailureAnalyzer;
import com.paypilot.dto.PaymentRequest;
import com.paypilot.dto.PaymentResponse;
import com.paypilot.entity.Payment;
import com.paypilot.exception.PaymentNotFoundException;
import com.paypilot.repository.PaymentRepository;
import com.paypilot.specification.PaymentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final FailureAnalyzer failureAnalyzer;
    private final RecoveryStrategyEngine recoveryStrategyEngine;
    private final RetryPolicyEngine retryPolicyEngine;

    public PaymentService(
            PaymentRepository paymentRepository,
            FailureAnalyzer failureAnalyzer,
            RecoveryStrategyEngine recoveryStrategyEngine,
            RetryPolicyEngine retryPolicyEngine) {

        this.paymentRepository = paymentRepository;
        this.failureAnalyzer = failureAnalyzer;
        this.recoveryStrategyEngine = recoveryStrategyEngine;
        this.retryPolicyEngine = retryPolicyEngine;
    }


    // CREATE PAYMENT
    @Transactional
    public PaymentResponse createPayment(
            PaymentRequest request,
            String idempotencyKey) {
        Payment existingPayment =
                paymentRepository
                        .findByIdempotencyKey(idempotencyKey)
                        .orElse(null);

        if (existingPayment != null) {
            return convertToResponse(existingPayment);
        }

        Payment payment = new Payment();

        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(request.getStatus());
        payment.setFailureReason(request.getFailureReason());
        payment.setCustomerType(request.getCustomerType());
        payment.setIdempotencyKey(idempotencyKey);
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        return convertToResponse(savedPayment);
    }

    // GET ALL PAYMENTS
    public Page<PaymentResponse> getAllPayments(
            int page,
            int size,
            String status,
            String paymentMethod,
            String customerType,
            String sortBy,
            String direction) {

        String safeSortBy;

        switch (sortBy) {
            case "id":
            case "amount":
            case "createdAt":
            case "status":
            case "paymentMethod":
            case "customerType":
                safeSortBy = sortBy;
                break;

            default:
                safeSortBy = "createdAt";
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {

            throw new IllegalArgumentException(
                    "Direction must be either 'asc' or 'desc'"
            );
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(safeSortBy).descending()
                : Sort.by(safeSortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Payment> specification =
                Specification.where(
                        PaymentSpecification.hasStatus(status)
                ).and(
                        PaymentSpecification.hasPaymentMethod(paymentMethod)
                ).and(
                        PaymentSpecification.hasCustomerType(customerType)
                );

        return paymentRepository
                .findAll(specification, pageable)
                .map(this::convertToResponse);
    }

    // GET PAYMENT BY ID
    public Payment getPaymentById(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with id: " + id
                        )
                );
    }

    // GET RECOMMENDATION
    public String getRecommendation(Long id) {

        Payment payment = getPaymentById(id);

        return generateRecommendation(payment);
    }

    // PAYMENT ANALYTICS

    public long getTotalPayments() {
        return paymentRepository.count();
    }

    public long getFailedPayments() {
        return paymentRepository.countByStatus(
                PaymentStatus.FAILED.name()
        );
    }

    public long getSuccessfulPayments() {
        return paymentRepository.countByStatus(
                PaymentStatus.SUCCESSFUL.name()
        );
    }

    public double getFailureRate() {

        long total = getTotalPayments();

        if (total == 0) {
            return 0;
        }

        return ((double) getFailedPayments() / total) * 100;
    }
    public PaymentAnalyticsResponse getAnalytics() {

        PaymentAnalyticsResponse response =
                new PaymentAnalyticsResponse();

        response.setTotalPayments(
                getTotalPayments()
        );

        response.setSuccessfulPayments(
                getSuccessfulPayments()
        );

        response.setFailedPayments(
                getFailedPayments()
        );

        response.setFailureRate(
                getFailureRate()
        );

        Map<String, Long> categoryCounts =
                new LinkedHashMap<>();

        for (FailureCategory category : FailureCategory.values()) {

            long count = getFailedPaymentsByCategory(category);

            if (count > 0) {
                categoryCounts.put(
                        category.name(),
                        count
                );
            }
        }

        response.setFailureCategoryCounts(
                categoryCounts
        );
        Map<String, Long> paymentMethodCounts =
                new LinkedHashMap<>();

        String[] paymentMethods = {
                "UPI",
                "CARD"
        };

        for (String paymentMethod : paymentMethods) {

            long count =
                    paymentRepository
                            .countByStatusAndPaymentMethod(
                                    "FAILED",
                                    paymentMethod
                            );

            if (count > 0) {
                paymentMethodCounts.put(
                        paymentMethod,
                        count
                );
            }
        }

        response.setFailurePaymentMethodCounts(
                paymentMethodCounts
        );

        return response;
    }
    private long getFailedPaymentsByCategory(
            FailureCategory category) {

        long count = 0;

        switch (category) {

            case TIMEOUT:
                count += paymentRepository
                        .countByStatusAndFailureReason(
                                "FAILED",
                                "BANK_TIMEOUT"
                        );

                count += paymentRepository
                        .countByStatusAndFailureReason(
                                "FAILED",
                                "UPI_TIMEOUT"
                        );
                break;

            case NETWORK:
                count = paymentRepository
                        .countByStatusAndFailureReason(
                                "FAILED",
                                "NETWORK_ERROR"
                        );
                break;

            case CUSTOMER_ERROR:
                count = paymentRepository
                        .countByStatusAndFailureReason(
                                "FAILED",
                                "INSUFFICIENT_FUNDS"
                        );
                break;

            case PAYMENT_METHOD_ERROR:
                count = paymentRepository
                        .countByStatusAndFailureReason(
                                "FAILED",
                                "INVALID_CARD"
                        );
                break;

            case SYSTEM_ERROR:
            case UNKNOWN:
                break;
        }

        return count;
    }

    // FAILURE RECOVERY ENGINE
    private String generateRecommendation(Payment payment) {

        if (payment.getFailureReason() == null ||
                payment.getFailureReason().isBlank()) {

            return "No failure reason available.";
        }

        switch (failureAnalyzer.analyze(payment.getFailureReason())) {

            case TIMEOUT:
                return "Timeout failure detected. Retry the payment after a short delay.";

            case NETWORK:
                return "Network failure detected. Retry after checking connectivity.";

            case CUSTOMER_ERROR:
                return "Customer-related failure detected. Ask the customer to verify funds or account details.";

            case PAYMENT_METHOD_ERROR:
                return "Payment method failure detected. Ask the customer to verify the payment details or use another method.";

            case SYSTEM_ERROR:
                return "System failure detected. Retry later or route the transaction to a fallback processor.";

            case UNKNOWN:
            default:
                return "Unknown payment failure. Review the failure details before retrying.";
        }
    }

    // ENTITY → DTO MAPPER
    private PaymentResponse convertToResponse(Payment payment) {

        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getStatus());
        response.setFailureReason(payment.getFailureReason());
        response.setCustomerType(payment.getCustomerType());
        response.setCreatedAt(payment.getCreatedAt());

        response.setRecommendation(
                generateRecommendation(payment)
        );
        FailureCategory category =
                failureAnalyzer.analyze(
                        payment.getFailureReason()
                );

        response.setFailureCategory(
                category.name()
        );

        response.setRetryable(
                failureAnalyzer.isRetryable(
                        payment.getFailureReason()
                )
        );

        response.setRecoveryStrategy(
                recoveryStrategyEngine
                        .determineStrategy(category)
                        .name()
        );
        RetryPolicy retryPolicy =
                retryPolicyEngine.determinePolicy(category);

        response.setMaxRetryAttempts(
                retryPolicy.getMaxAttempts()
        );

        response.setRetryDelayMillis(
                retryPolicy.getInitialDelayMillis()
        );
        response.setRetryAttempt1DelayMillis(
                retryPolicyEngine.calculateDelay(
                        retryPolicy,
                        1
                )
        );

        response.setRetryAttempt2DelayMillis(
                retryPolicyEngine.calculateDelay(
                        retryPolicy,
                        2
                )
        );
        return response;
    }
}