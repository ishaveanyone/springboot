/**
 * Date: 2020-09-02 10:09
 * Author: xupp
 */

package com.xupp.springbootmybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserEntity {
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    private String name;
}
