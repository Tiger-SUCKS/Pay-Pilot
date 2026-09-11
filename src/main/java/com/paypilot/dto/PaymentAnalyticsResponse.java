package com.paypilot.dto;
import java.util.Map;

public class PaymentAnalyticsResponse {

    private long totalPayments;
    private long successfulPayments;
    private long failedPayments;
    private double failureRate;
    private Map<String, Long> failureCategoryCounts;
    private Map<String, Long> failurePaymentMethodCounts;

    public long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public long getSuccessfulPayments() {
        return successfulPayments;
    }

    public void setSuccessfulPayments(long successfulPayments) {
        this.successfulPayments = successfulPayments;
    }

    public long getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(long failedPayments) {
        this.failedPayments = failedPayments;
    }

    public double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(double failureRate) {
        this.failureRate = failureRate;
    }
    public Map<String, Long> getFailureCategoryCounts() {
        return failureCategoryCounts;
    }

    public void setFailureCategoryCounts(
            Map<String, Long> failureCategoryCounts) {

        this.failureCategoryCounts = failureCategoryCounts;
    }
    public Map<String, Long> getFailurePaymentMethodCounts() {
        return failurePaymentMethodCounts;
    }
    public void setFailurePaymentMethodCounts(
            Map<String, Long> failurePaymentMethodCounts) {

        this.failurePaymentMethodCounts = failurePaymentMethodCounts;
    }
}