package com.example.myspark;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

public class RocketMQReceiver extends Receiver {
    public RocketMQReceiver( StorageLevel storageLevel) {
        super(storageLevel);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
