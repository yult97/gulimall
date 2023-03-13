package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName MemberPrice.java
 * @Description TODO
 * @createTime 2023年03月13日 22:44:00
 */
@Data
public class MemberPrice {
    private Long id;
    private String name;
    private BigDecimal price;
}
