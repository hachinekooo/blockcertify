package com.github.blockcertify.annotation;

import org.springframework.stereotype.Service;

@Service
class TestService {
    public static final String BIZ_TYPE = "mock";
    public static final String RETURN_VALUE = "Success";
    public static final String USER_ID = "user-123";

        @Certify(bizType = BIZ_TYPE)
        public String doSomething(String userId) {
            System.out.println("Executing original method logic for user: " + userId);
            return RETURN_VALUE;
        }
    }