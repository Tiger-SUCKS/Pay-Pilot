package com.paypilot.service;

import com.paypilot.entity.Payment;
import com.paypilot.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    public long getTotalPayments() {
        return paymentRepository.count();
    }

    public long getFailedPayments() {
        return paymentRepository.countByStatus("FAILED");
    }

    public long getSuccessfulPayments() {
        return paymentRepository.countByStatus("SUCCESSFUL");
    }
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public double getFailureRate() {

        long total = getTotalPayments();

        if (total == 0) {
            return 0;
        }

        return ((double) getFailedPayments() / total) * 100;
    }

    public String getRecommendation(Payment payment) {

        if (payment.getFailureReason() == null) {
            return "No failure reason available.";
        }

        String reason = payment.getFailureReason().toUpperCase();

        switch (reason) {

            case "BANK_TIMEOUT":
                return "Bank timeout detected. Retry the payment after a short delay.";

            case "UPI_TIMEOUT":
                return "UPI timeout detected. Retry the UPI payment or use another payment method.";

            case "INSUFFICIENT_FUNDS":
                return "Insufficient funds. Ask the customer to use another payment method.";

            case "INVALID_CARD":
                return "Invalid card details. Ask the customer to verify their card information.";

            case "NETWORK_ERROR":
                return "Network error detected. Retry the payment after checking connectivity.";

            default:
                return "Payment failed due to an unknown reason. Review the failure details and retry.";
        }
    }
}