package com.atguigu.gulimall.coupon.service;

import com.atguigu.common.to.SkuReductionTo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-19 15:12:29
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @throws
     * @title saveSkuReduction
     * @description TODO
     * @author yubo
     * @updateTime 2023-03-13 22:51
     * @Return void
     * @Param [skuReductionTo]
     */
    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

