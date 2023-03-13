package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName BoundsTo.java
 * @Description TODO
 * @createTime 2023年03月13日 22:29:00
 */
@Data
public class BoundsTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
