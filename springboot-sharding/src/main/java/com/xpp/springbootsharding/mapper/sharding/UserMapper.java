/**
 * Date: 2021-03-11 10:52
 * Author: xupp
 */

package com.xpp.springbootsharding.mapper.sharding;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xpp.springbootsharding.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {
    @Insert("INSERT INTO  user (`name`,`create_time`) values (#{user.name},#{user.createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id", keyColumn = "id")
    Integer save(@Param("user") User user);

    @Update("${sql}")
    void createTable(@Param("sql") String sql);
}
