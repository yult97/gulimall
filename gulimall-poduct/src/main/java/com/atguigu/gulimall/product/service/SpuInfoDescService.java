package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu信息介绍
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @throws
     * @title saveDesc
     * @description TODO
     * @author yubo
     * @updateTime 2023-03-12 23:42
     * @Return void
     * @Param [spuInfoDescEntity]
     */
    void saveDesc(SpuInfoDescEntity spuInfoDescEntity);

}

