package com.atguigu.gulimall.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.coupon.entity.CouponEntity;
import com.atguigu.gulimall.coupon.service.CouponService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 优惠券测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GulimallCouponApplicationTests {

    @Autowired
    CouponService couponService;

    @Test
    public void contextLoads() {
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponType(0);
        couponEntity.setCouponImg("https:www.baidu.com");
        couponEntity.setCouponName("手机100优惠券");
        couponEntity.setNum(1);
        couponEntity.setAmount(new BigDecimal("0"));
        couponEntity.setPerLimit(0);
        couponEntity.setMinPoint(new BigDecimal("0"));
        couponEntity.setStartTime(new Date());
        couponEntity.setEndTime(new Date());
        couponEntity.setUseType(0);
        couponEntity.setNote("");
        couponEntity.setPublishCount(0);
        couponEntity.setUseCount(0);
        couponEntity.setReceiveCount(0);
        couponEntity.setEnableStartTime(new Date());
        couponEntity.setEnableEndTime(new Date());
        couponEntity.setCode("");
        couponEntity.setMemberLevel(0);
        couponEntity.setPublish(0);

        boolean save = couponService.save(couponEntity);
        System.out.println("=============插入优惠券信息============" + save);

        List<CouponEntity> couponEntities = couponService.list(new QueryWrapper<CouponEntity>().eq("id", "1"));
        couponEntities.forEach(item-> System.out.println(item));
    }

}
