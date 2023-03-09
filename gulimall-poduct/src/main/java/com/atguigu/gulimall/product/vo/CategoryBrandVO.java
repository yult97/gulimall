package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName CategoryBrandVO.java
 * @Description 品牌分类实体类
 * @createTime 2023年03月09日 22:14:00
 */
@Data
public class CategoryBrandVO {

    /**
     * @title
     * @description 品牌编号
     * @author yubo
     * @updateTime 2023-03-09 22:16
     * @throws
     * @return
     */
    private Long brandId;

    /**
     * @title
     * @description 品牌名称
     * @author yubo
     * @updateTime 2023-03-09 22:16
     * @throws
     * @return
     */
    private String brandName;
}
