package com.example.sipserverdemo.service;

import javax.sip.*;


public class SipServer implements SipListener {
    @Override
    public void processRequest(RequestEvent requestEvent) {
        System.out.println("=============>processRequest");
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        System.out.println("=============>processResponse");
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
