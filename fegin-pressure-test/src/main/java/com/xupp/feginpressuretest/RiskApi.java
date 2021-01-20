package com.xupp.feginpressuretest;



import feign.*;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.atomic.AtomicInteger;

public class RiskApi {

    public static void main(String[] args) {


    }
   static AtomicInteger index=new AtomicInteger(0);



    public static DataCenterApi getDataCenterApi(String url) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        DataCenterApi service = Feign.builder()
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        System.out.println("fegin请求："+index.incrementAndGet());
                    }
                })
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .contract(new Contract.Default())
                .options(new Request.Options(10000, 35000))
                .client(new ApacheHttpClient(httpClient))
                .retryer(new Retryer.Default(5000, 5000, 1))
                .target(DataCenterApi.class, url);
        return service;
    }

    public static Feign.Builder getDataCenterApi() {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(2);
        cm.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
       return Feign.builder()
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        System.out.println("fegin请求："+index.incrementAndGet());
                    }
                })
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .contract(new Contract.Default())
                .options(new Request.Options(10000, 35000))
                .client(new ApacheHttpClient(httpClient))
                .retryer(new Retryer.Default(5000, 5000, 1));

    }


}
