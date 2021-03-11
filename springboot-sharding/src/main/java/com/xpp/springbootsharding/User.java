/**
 * Date: 2021-03-11 10:52
 * Author: xupp
 */

package com.xpp.springbootsharding;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Date createTime;
}
