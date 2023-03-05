package com.atguigu.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRspVO extends AttrVO {
    /**
     * 属性分组id
     */
    private Long attrGroupId;
    /**
     * 分类名称
     */
    private String attrGroupName;

    /**
     * 分组名称
     */
    private String catelogName;
    /**
     * 所属分类路径
     * 因不是表中字段、使用此注解排除
     */
    private Long[] catelogPath;
}
