/**
 * Date: 2021-02-24 16:06
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

public class KafkaMessageListener implements AcknowledgingMessageListener {


    @Override
    public void onMessage(Object o, Acknowledgment acknowledgment) {

    }
}
