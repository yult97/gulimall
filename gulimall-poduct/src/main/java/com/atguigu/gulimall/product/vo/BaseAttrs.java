/**
 * Copyright 2020 bejson.com
 */
package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName BaseAttrs.java
 * @Description 基本属性实体类
 * @createTime 2023年03月09日 22:14:00
 */

@Data
public class BaseAttrs {

    private Long attrId;
    private String attrValues;
    private int showDesc;

}
