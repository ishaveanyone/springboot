/**
 * Date: 2020-09-02 17:40
 * Author: xupp
 */

package com.xupp.springbootmybatis.define;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Job {

    @Scheduled(cron = "0/5 * * * * ? ")
    public void  test(){
        System.out.println(1);
    }


}
