package com.paypilot.entity;

public class AnalyticsResponse {

    private long totalPayments;
    private long successfulPayments;
    private long failedPayments;
    private double failureRate;

    public AnalyticsResponse() {
    }

    public AnalyticsResponse(long totalPayments,
                             long successfulPayments,
                             long failedPayments,
                             double failureRate) {

        this.totalPayments = totalPayments;
        this.successfulPayments = successfulPayments;
        this.failedPayments = failedPayments;
        this.failureRate = failureRate;
    }

    public long getTotalPayments() {
        return totalPayments;
    }

    public long getSuccessfulPayments() {
        return successfulPayments;
    }

    public long getFailedPayments() {
        return failedPayments;
    }

    public double getFailureRate() {
        return failureRate;
    }
}