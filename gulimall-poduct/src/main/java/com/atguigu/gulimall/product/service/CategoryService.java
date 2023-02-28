package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:39
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询商品所有分类，以及子类，并按照属性结构展示
     * @return
     */
    List<CategoryEntity> listWithTree();


    /**
     * 根据菜单编号,删除分类菜单信息
     * @param categoryId
     */
    void deleteProduct(Long[] categoryId);

    /**
     * 查询属性分组全路径信息
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);
}

