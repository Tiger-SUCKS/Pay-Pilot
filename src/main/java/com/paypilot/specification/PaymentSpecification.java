package com.paypilot.specification;

import com.paypilot.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

public class PaymentSpecification {

    public static Specification<Payment> hasStatus(String status) {

        return (root, query, criteriaBuilder) -> {

            if (status == null || status.isBlank()) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("status"),
                    status
            );
        };
    }

    public static Specification<Payment> hasPaymentMethod(String paymentMethod) {

        return (root, query, criteriaBuilder) -> {

            if (paymentMethod == null || paymentMethod.isBlank()) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("paymentMethod"),
                    paymentMethod
            );
        };
    }

    public static Specification<Payment> hasCustomerType(String customerType) {

        return (root, query, criteriaBuilder) -> {

            if (customerType == null || customerType.isBlank()) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("customerType"),
                    customerType
            );
        };
    }
}