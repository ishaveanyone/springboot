/**
 * Date: 2021-02-24 11:31
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;


import com.lmax.disruptor.EventHandler;

public class LongEventHandler  implements EventHandler<LongEvent> {


    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("consumer:"+Thread.currentThread().getName()+" Event: value=" + event.get()+",sequence="+sequence+",endOfBatch="+endOfBatch);
    }


}