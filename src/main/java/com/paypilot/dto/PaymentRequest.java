package com.paypilot.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    @NotBlank(message = "Payment method is required")
    @Pattern(
            regexp = "UPI|CARD",
            message = "Payment method must be either UPI or CARD"
    )
    private String paymentMethod;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "SUCCESSFUL|FAILED",
            message = "Status must be either SUCCESSFUL or FAILED"
    )
    private String status;

    private String failureReason;

    @NotBlank(message = "Customer type is required")
    private String customerType;

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
}