package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:39
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
