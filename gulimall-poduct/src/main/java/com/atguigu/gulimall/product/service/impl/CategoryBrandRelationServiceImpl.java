package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryBrandRelationDao;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    BrandDao brandDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据品牌id查询关联属性信息
     * @param brandId
     * @return
     */
    @Override
    public List<CategoryBrandRelationEntity> selectDetail(Long brandId) {
        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = baseMapper.selectList(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        return categoryBrandRelationEntityList;
    }

    /**
     * 保存关联属性信息
     * @param categoryBrandRelation
     */
    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        //根据分类id查询分类维护相关信息
        CategoryEntity categoryEntity = categoryDao.selectById(categoryBrandRelation.getCatelogId());
        //根据品牌id查询品牌管理相关信息
        BrandEntity brandEntity = brandDao.selectById(categoryBrandRelation.getBrandId());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        categoryBrandRelation.setBrandName(brandEntity.getName());
        this.save(categoryBrandRelation);
    }

}