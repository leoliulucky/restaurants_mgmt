package com.benxiaopao.thrift;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Thrift客户端配置
 *
 * Created by liupoyang
 * 2019-04-21
 */
@Configuration
public class ThriftClientConfig {
    @Value("${thrift.host}")
    private String host;
    @Value("${thrift.port}")
    private int port;

    @Bean(initMethod = "init")
    public ThriftClient thriftClient() {
        ThriftClient thriftClient = new ThriftClient();
        thriftClient.setHost(host);
        thriftClient.setPort(port);
        return thriftClient;
    }
}
