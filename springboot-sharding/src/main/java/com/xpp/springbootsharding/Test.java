/**
 * Date: 2021-03-11 11:36
 * Author: xupp
 */

package com.xpp.springbootsharding;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Test {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;

}
