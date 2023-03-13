/**
 * Copyright 2020 bejson.com
 */
package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName MemberPrice.java
 * @Description 商品价格实体类
 * @createTime 2023年03月09日 22:14:00
 */

@Data
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;

}
