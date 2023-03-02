package com.atguigu.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import org.springframework.util.StringUtils;

/**
 * 品牌管理实现类
 * @author yubo
 */
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    /**
     * 品牌管理分页查询
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //搜索查询参数值
        String key = (String) params.get("key");
        //定义拼接 SQL 查询条件
        QueryWrapper queryWrapper = new QueryWrapper<BrandEntity>();
        if (!StringUtils.isEmpty(key)) {
            queryWrapper = new QueryWrapper<BrandEntity>().eq("brand_id", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}