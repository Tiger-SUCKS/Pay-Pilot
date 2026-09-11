package com.paypilot.dto;

import java.time.LocalDateTime;

public class PaymentResponse {

    private Long id;
    private Double amount;
    private String paymentMethod;
    private String status;
    private String failureReason;
    private String failureCategory;
    private String recoveryStrategy;
    private String customerType;
    private LocalDateTime createdAt;
    private boolean retryable;
    private int maxRetryAttempts;
    private long retryDelayMillis;
    private String recommendation;
    private long retryAttempt1DelayMillis;
    private long retryAttempt2DelayMillis;


    public String getFailureCategory() {
        return failureCategory;
    }

    public void setFailureCategory(String failureCategory) {
        this.failureCategory = failureCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
    public boolean isRetryable() {
        return retryable;
    }

    public void setRetryable(boolean retryable) {
        this.retryable = retryable;
    }
    public String getRecoveryStrategy() {
        return recoveryStrategy;
    }

    public void setRecoveryStrategy(String recoveryStrategy) {
        this.recoveryStrategy = recoveryStrategy;
    }
    public int getMaxRetryAttempts() {
        return maxRetryAttempts;
    }

    public void setMaxRetryAttempts(int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
    }

    public long getRetryDelayMillis() {
        return retryDelayMillis;
    }

    public void setRetryDelayMillis(long retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
    }
    public long getRetryAttempt1DelayMillis() {
        return retryAttempt1DelayMillis;
    }

    public void setRetryAttempt1DelayMillis(long retryAttempt1DelayMillis) {
        this.retryAttempt1DelayMillis = retryAttempt1DelayMillis;
    }

    public long getRetryAttempt2DelayMillis() {
        return retryAttempt2DelayMillis;
    }

    public void setRetryAttempt2DelayMillis(long retryAttempt2DelayMillis) {
        this.retryAttempt2DelayMillis = retryAttempt2DelayMillis;
    }
}