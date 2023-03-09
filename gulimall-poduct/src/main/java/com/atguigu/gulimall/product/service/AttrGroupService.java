package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.vo.AttrRelationVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 属性分组分页查询
     *
     * @param params
     * @param attrGroupId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, Long attrGroupId);


    /**
     * 根据属性分组编号获取属性分组的关联的所有属性
     *
     * @param attrgroupId
     * @return
     */
    List<AttrEntity> attrGroupRelation(Long attrgroupId);

    /**
     * 删除属性关联关系
     *
     * @param attrRelationVOS
     * @date 2023/03/07
     */
    void relationDelete(AttrRelationVO[] attrRelationVOS);

}

