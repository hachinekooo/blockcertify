package com.github.blockcertify.common.enums;

/**
 * Error category classification
 */
public enum ErrorCategory {
    /**
     * General errors (1000-1999)
     */
    GENERAL,
    
    /**
     * Authentication & Authorization (2000-2999)
     */
    AUTH,
    
    /**
     * Database related (3000-3999)
     */
    DATABASE,
    
    /**
     * Business logic (4000-4999)
     */
    BUSINESS,
    
    /**
     * External service (5000-5999)
     */
    EXTERNAL,
    
    /**
     * Blockchain related (6000-6999)
     */
    BLOCKCHAIN,
    
    /**
     * Rate limiting (8000-8999)
     */
    RATE_LIMIT,
    
    /**
     * System errors (9000+)
     */
    SYSTEM
}
