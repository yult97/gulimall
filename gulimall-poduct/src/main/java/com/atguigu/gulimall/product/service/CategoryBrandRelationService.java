package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 关联关系根据关联编号查询属性名称
     *
     * @param brandId
     * @return
     * @author yubo
     */
    List<CategoryBrandRelationEntity> selectDetail(Long brandId);

    /**
     * 保存关联属性信息
     *
     * @param categoryBrandRelation
     * @author yubo
     */
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    /**
     * 修改层级关联信息属性
     *
     * @param catId
     * @param name
     */
    void updateRelation(Long catId, String name);

    /**
     * @return
     * @throws
     * @title
     * @description 根据分类id查询品牌信息
     * @author yubo
     * @updateTime 2023-03-09 22:12
     */
    List<BrandEntity> selectBrandsList(long catId);
}

