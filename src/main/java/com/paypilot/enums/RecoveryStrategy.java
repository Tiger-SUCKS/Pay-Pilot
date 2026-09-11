package com.paypilot.enums;

public enum RecoveryStrategy {

    RETRY,
    FALLBACK_PAYMENT_METHOD,
    CUSTOMER_ACTION,
    NO_ACTION
}