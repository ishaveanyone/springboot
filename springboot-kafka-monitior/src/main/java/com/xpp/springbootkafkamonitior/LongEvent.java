/**
 * Date: 2021-02-24 11:29
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;
// 定义一个线程消息
public class LongEvent {

    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long get(){
        return this.value;
    }

}
