package com.xpp.springbootsharding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 *
 * 使用静态内部类配置一些规则
 * Created by yaoxinwei on 2020/3/17.
 */

@Slf4j
public class TableShardingAlgorithm {

    //定义时间分片
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class MonthPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
        private String TABLE_FIX;
        @Override
        public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
            //获取月份(测试时获取小时)
            String suffix = getYYYYMM(preciseShardingValue.getValue());
            return TABLE_FIX + suffix;
        }
    }


    public static String getYYYYMM(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        return simpleDateFormat.format(date);
    }

}
