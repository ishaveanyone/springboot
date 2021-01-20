package com.xupp.springbootkafka;

/**
 * @Description: 风控kafka常量
 * @Author: ChenPan
 * @CreateDate: 2020/1/2 11:16
 */
public class RiskKafkaConstants {
    public static final String THIRD_PARTY_RISK_TOPIC = "third_party_risk_topic";
    public static final String THIRD_PARTY_RISK_BEAN = "third_party_risk_bean";

    public static final String BLACKLIST_RISK_TOPIC = "blacklist_risk_topic";
    public static final String BLACKLIST_RISK_BEAN = "blacklist_risk_bean";

    public static final String PRE_RISK_TOPIC = "pre_risk_topic";
    public static final String PRE_RISK_BEAN = "pre_risk_bean";

    public static final String MARKET_PRE_SELECT_TOPIC = "market_pre_select_topic";
    public static final String MARKET_PRE_SELECT_BEAN = "market_pre_select_bean";
    public static Integer MARKET_PRE_SELECT_NUM = 3;


    public static final String BUSSINESS_MONITOR = "business-monitor";


}
