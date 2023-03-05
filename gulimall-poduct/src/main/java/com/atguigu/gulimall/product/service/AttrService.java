package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.AttrRspVO;
import com.atguigu.gulimall.product.vo.AttrVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 商品属性分页查询
     *
     * @param params
     * @param catelogId
     * @return
     */
    PageUtils queryPageAttr(Map<String, Object> params, Long catelogId);

    /**
     * 商品属性关联保存
     *
     * @param attr
     */
    void saveAttr(AttrVO attr);

    /**
     * 根据规则参数id查询详情
     *
     * @param attrId
     * @return
     */
    AttrRspVO infoById(Long attrId);

    /**
     * 更新规格参数属性值
     *
     * @param attr
     */
    void updateAttr(AttrVO attr);
}

