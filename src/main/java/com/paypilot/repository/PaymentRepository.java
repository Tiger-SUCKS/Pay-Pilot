package com.paypilot.repository;

import java.util.Optional;
import com.paypilot.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository
        extends JpaRepository<Payment, Long>,
        JpaSpecificationExecutor<Payment> {

    long countByStatus(String status);

    long countByFailureReason(String failureReason);
    Optional<Payment> findByIdempotencyKey(String idempotencyKey);

    long countByStatusAndFailureReason(
            String status,
            String failureReason
    );
    long countByStatusAndPaymentMethod(
            String status,
            String paymentMethod
    );
}
