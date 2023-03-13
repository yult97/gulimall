package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.vo.BaseAttrs;
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

import com.atguigu.gulimall.product.dao.ProductAttrValueDao;
import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;
import com.atguigu.gulimall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {
    @Autowired
    private AttrDao attrDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @throws
     * @title saveBaseAttrs
     * @description 保存商品基本属性信息
     * @author yubo
     * @updateTime 2023-03-13 0:15
     * @Return void
     * @Param [baseAttrs, id]
     */
    @Override
    public void saveBaseAttrs(List<BaseAttrs> baseAttrs, Long id) {
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(baseAttrs1 -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(baseAttrs1.getAttrId());
            AttrEntity attrEntity = attrDao.selectById(baseAttrs1.getAttrId());
            productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            productAttrValueEntity.setAttrValue(baseAttrs1.getAttrValues());
            productAttrValueEntity.setQuickShow(baseAttrs1.getShowDesc());
            productAttrValueEntity.setSpuId(id);
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }

}