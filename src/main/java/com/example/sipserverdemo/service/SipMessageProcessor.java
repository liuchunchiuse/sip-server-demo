package com.example.sipserverdemo.service;

import javax.sip.*;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;

/**
 * @Author: cth
 * @Date: 2019/10/22 13:37
 * @Description:
 */
public interface SipMessageProcessor {

    /**
     * 接收IPCamera发来的SIP协议消息的时候产生的回调函数
     */
    public void processRequest(RequestEvent requestEvent, AddressFactory addressFactory, MessageFactory messageFactory, HeaderFactory headerFactory, SipProvider sipProvider);

    public void processRequest(RequestEvent requestEvent, AddressFactory addressFactory, MessageFactory messageFactory,
                               HeaderFactory headerFactory, SipProvider sipProvider, ListeningPoint listeningPoint);

    /**
     * 接收IPCamera发来的SIP协议消息的时候产生的回调函数
     */
    public void processResponse(ResponseEvent responseEvent, AddressFactory addressFactory, MessageFactory messageFactory, HeaderFactory headerFactory, SipProvider sipProvider) throws InvalidArgumentException;

    public void processError(String errorMessage);
}
