package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.SpuSaveVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @throws
     * @title saveSpuInfo
     * @description 保存商品信息
     * @author yubo
     * @updateTime 2023-03-12 23:03
     * @Return void
     * @Param [spuVo]
     */
    void saveSpuInfo(SpuSaveVo spuVo);
}

