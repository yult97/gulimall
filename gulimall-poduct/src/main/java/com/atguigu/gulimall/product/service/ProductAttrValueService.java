package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.BaseAttrs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @title saveBaseAttrs
     * @description 保存商品基本属性信息
     * @author yubo
     * @updateTime 2023-03-13 0:04
     * @throws
     * @Return void
     * @Param  [baseAttrs]
     */
    void saveBaseAttrs(List<BaseAttrs> baseAttrs,Long id);
}

