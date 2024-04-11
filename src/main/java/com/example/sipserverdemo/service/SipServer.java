package com.example.sipserverdemo.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sip.*;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;
import java.util.Properties;
import java.util.TooManyListenersException;

@Slf4j
public class SipServer implements SipListener {

    SipStack sipStack;
    SipProvider sipProvider;
    AddressFactory addressFactory;
    HeaderFactory headerFactory;
    MessageFactory messageFactory;

    @Getter
    @Setter
    private SipMessageProcessor messageProcessor;

    private ListeningPoint tcp;

    private ListeningPoint udp;

    public SipServer() {
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

            String ip = "0.0.0.0";
            int port = 5060;

            createListeningPoint(ip, port, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建监听地址和端口
     */
    private void createListeningPoint(String ip, int port, SipServer sipServer) throws InvalidArgumentException,
            TransportNotSupportedException,
            ObjectInUseException, TooManyListenersException {
        tcp = sipStack.createListeningPoint(ip, port, "tcp");
        udp = sipStack.createListeningPoint(ip, port, "udp");
        sipProvider = sipStack.createSipProvider(tcp);
        sipProvider.addSipListener(sipServer);
        sipProvider = sipStack.createSipProvider(udp);
        sipProvider.addSipListener(sipServer);
    }


    @Override
    public void processRequest(RequestEvent requestEvent) {
        log.info("=============>processRequest,messageProcessor:{}", messageProcessor);
        messageProcessor.processRequest(requestEvent, addressFactory, messageFactory, headerFactory, sipProvider, udp);
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        log.info("=============>processResponse");
        try {
            messageProcessor.processResponse(responseEvent, addressFactory, messageFactory, headerFactory, sipProvider);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        System.out.println("=============>processTimeout");
    }

    @Override
    public void processIOException(IOExceptionEvent ioExceptionEvent) {
        System.out.println("=============>processIOException");
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        System.out.println("=============>processTransactionTerminated");
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        System.out.println("=============>processDialogTerminated");
    }
}
