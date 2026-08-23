package com.paypilot.repository;

import com.paypilot.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    long countByStatus(String status);

    long countByFailureReason(String failureReason);
}