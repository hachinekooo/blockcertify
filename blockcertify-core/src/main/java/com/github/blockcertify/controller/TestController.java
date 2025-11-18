package com.github.blockcertify.controller;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.business.BusinessException;
import com.github.blockcertify.exception.system.SystemException;
import com.github.blockcertify.exception.thirdparty.ThirdPartyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器 - 用于测试异常处理
 */
@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/business-error")
    public String testBusinessError() {
        throw new BusinessException(ErrorCode.BUSINESS_LOGIC_ERROR, "测试业务异常");
    }
    
    @GetMapping("/system-error")
    public String testSystemError() {
        throw new SystemException(ErrorCode.SYSTEM_ERROR, "测试系统异常");
    }
    
    @GetMapping("/third-party-error")
    public String testThirdPartyError() {
        throw new ThirdPartyException(ErrorCode.EXTERNAL_SERVICE_ERROR, "测试第三方服务异常");
    }
    
    @GetMapping("/blockchain-error")
    public String testBlockchainError() {
        throw new SystemException(ErrorCode.BLOCKCHAIN_ERROR, "测试区块链异常");
    }
    
    @GetMapping("/success")
    public String testSuccess(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }
}
