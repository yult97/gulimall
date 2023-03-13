package com.atguigu.gulimall.product.vo;

import com.atguigu.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName AttrWithGroupVO.java
 * @Description 获取分类下所有分组&关联属性实体类
 * @createTime 2023年03月12日 19:01:00
 */
@Data
public class AttrWithGroupVO {
    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    /**
     * 商品属性信息集合
     */
    private List<AttrEntity> attrs;
}
