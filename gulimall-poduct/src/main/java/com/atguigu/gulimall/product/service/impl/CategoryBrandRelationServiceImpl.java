package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryBrandRelationDao;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
@Slf4j
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    BrandDao brandDao;
    @Autowired
    BrandService brandService;

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
     *
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
     *
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

    /**
     * 更新关联分类中分类名称信息
     *
     * @param catId
     * @param name
     */
    @Override
    public void updateRelation(Long catId, String name) {
        this.baseMapper.updateRelationBrand(catId, name);
    }


    /**
     * @return
     * @throws
     * @title
     * @description 根据分类id查询品牌信息
     * @author yubo
     * @updateTime 2023-03-09 22:12
     */
    @Override
    public List<BrandEntity> selectBrandsList(long catId) {
        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = this.baseMapper.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        log.info("查询商品分类关联信息{}", categoryBrandRelationEntityList);
        List<BrandEntity> cateIds = categoryBrandRelationEntityList.stream().map(item -> {
            Long brandId = item.getBrandId();
            BrandEntity brandEntity = brandService.getById(brandId);
            return brandEntity;
        }).collect(Collectors.toList());
        return cateIds;
    }

    /**
     * 根据品牌编号,品牌名称更新信息
     *
     * @param brandId
     */
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(name);
        this.update(categoryBrandRelationEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }
}