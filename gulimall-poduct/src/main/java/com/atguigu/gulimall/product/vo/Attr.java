/** Copyright 2020 bejson.com */
package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName Attr.java
 * @Description 商品属性实体类
 * @createTime 2023年03月09日 22:14:00
 */

@Data
public class Attr {

  private Long attrId;
  private String attrName;
  private String attrValue;
}
