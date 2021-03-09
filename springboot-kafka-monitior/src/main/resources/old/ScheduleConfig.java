/**
 * Date: 2020-12-08 17:23
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior.old;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //显式为Scheduler指定线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(16));
    }
}
