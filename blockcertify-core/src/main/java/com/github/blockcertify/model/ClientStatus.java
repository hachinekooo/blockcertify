package com.github.blockcertify.model;

import com.github.blockcertify.common.enums.ClientStatusEnum;

import java.time.LocalDateTime;

public class ClientStatus {

    /*
    * 客户端状态，CONNECTED/DISCONNECTED/ERROR/CONNECTING
    * */
    private ClientStatusEnum status;

    /*
    * 检查时刻
    * */
    private LocalDateTime checkTime;

}
