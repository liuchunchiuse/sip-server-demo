package com.example.sipserverdemo.service;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sip.*;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;
import java.util.Properties;
import java.util.TooManyListenersException;

@Configuration
public class SipInitializer {

    SipStack sipStack;
    SipProvider sipProvider;
    AddressFactory addressFactory;
    HeaderFactory headerFactory;
    MessageFactory messageFactory;

    private ListeningPoint tcp;

    private ListeningPoint udp;

    @PostConstruct
    public void init() {
        System.out.println("===================>开始初始化服务器");

        try {
            // 设置 SIP 栈的属性
            Properties properties = new Properties();
            properties.setProperty("javax.sip.STACK_NAME", "SipServer");
            // 设置路径名称（此处可根据实际情况修改）
            properties.setProperty("gov.nist.javax.sip.PATH_NAME", "gov.nist");

            // 创建 SIP 栈
            sipStack = SipFactory.getInstance().createSipStack(properties);

            // 创建地址、头部和消息工厂
            addressFactory = SipFactory.getInstance().createAddressFactory();
            headerFactory = SipFactory.getInstance().createHeaderFactory();
            messageFactory = SipFactory.getInstance().createMessageFactory();

            String ip = "127.0.0.1";
            int port = 5060;
            // 创建监听地址和端口
//            ListeningPoint listeningPoint = sipStack.createListeningPoint("127.0.0.1", 5060, "udp");


            SipServer sipServer = new SipServer();

            // 创建 SIP 提供者
            /*sipProvider = sipStack.createSipProvider(listeningPoint);
            sipProvider.addSipListener();*/
            createListeningPoint(ip, port, sipServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createListeningPoint(String ip, int port, SipServer sipServer) throws InvalidArgumentException, TransportNotSupportedException,
            ObjectInUseException, TooManyListenersException {
        tcp = sipStack.createListeningPoint(ip, port, "tcp");
        udp = sipStack.createListeningPoint(ip, port, "udp");
        sipProvider = sipStack.createSipProvider(tcp);
        sipProvider.addSipListener(sipServer);
        sipProvider = sipStack.createSipProvider(udp);
        sipProvider.addSipListener(sipServer);
    }


}
