package com.example.sipserverdemo.config;

import com.example.sipserverdemo.service.SipMessageProcessor;
import com.example.sipserverdemo.service.SipServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sip.PeerUnavailableException;

@Slf4j
@Configuration
public class SipConfig {

    @Bean
    public SipServer sipServer(SipMessageProcessor messageProcessor) {
        try {
            SipServer sipServerLayer = new SipServer();
            sipServerLayer.setMessageProcessor(messageProcessor);
            log.info("SIP服务启动完毕");
            return sipServerLayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
