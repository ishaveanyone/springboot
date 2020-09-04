/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2019-12-19 11:25
 * Author: xupp
 * Email: xupp@dist.com.cn
 * Desc：
 */
package com.xupp.springbootcloudapi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "provider",
        path = "/test"
)
public interface TestService {
}
