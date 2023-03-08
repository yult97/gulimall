package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.AttrRelationVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 批量保存商品关联关系
     *
     * @param attrRelationVOS
     * @date 2023/03/08
     */
    void saveBatchRelation(List<AttrRelationVO> attrRelationVOS);
}

