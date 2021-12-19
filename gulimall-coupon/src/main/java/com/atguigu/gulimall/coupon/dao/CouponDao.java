package com.atguigu.gulimall.coupon.dao;

import com.atguigu.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-19 15:12:30
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
