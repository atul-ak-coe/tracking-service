package com.fleet.management.tracking.exception;

import reactor.kafka.receiver.ReceiverRecord;

import javax.validation.constraints.NotNull;

public class ReceiverRecordException extends RuntimeException{
    @NotNull
    private final ReceiverRecord receiverRecord;

    public ReceiverRecordException(ReceiverRecord receiverRecord, Throwable throwable) {
        super(throwable);
        this.receiverRecord = receiverRecord;
    }
    public ReceiverRecord getReceiverRecord() {
        return this.receiverRecord;
    }
}
