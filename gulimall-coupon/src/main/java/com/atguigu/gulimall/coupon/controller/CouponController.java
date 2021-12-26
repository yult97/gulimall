package com.atguigu.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gulimall.coupon.entity.CouponEntity;
import com.atguigu.gulimall.coupon.service.CouponService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;

import javax.validation.Valid;


/**
 * 优惠券信息
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-19 15:12:30
 *
 *  1、nacos配置调用配置文件，修改配置文件，及时生效，需使用
 *    RefreshScope注解
 *  2、也可指定在nacos客户端，配置多环境配置文件，根据 Data Id（通用id） 指定配置文件
 */
@RestController
@RefreshScope
@RequestMapping("coupon/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * 在配置文件中获取姓名属性
     */
    @Value("${coupon.user.name}")
    private String name;
    /**
     * 在配置文件中获取年龄属性
     */
    @Value("${coupon.user.age}")
    private  Integer age;

    /**
     * 测试nacos配置中心方法
     * @return
     */
    @RequestMapping("/test")
    public R test(){
        return R.ok().put("name",name).put("age",age);
    }
    /**
     * 优惠券方法
     * @return
     */
    @RequestMapping("/member/list")
    public R membercoupons(){
        CouponEntity couponEntity=new CouponEntity();
        couponEntity.setCouponName("满100减10");
        return R.ok().put("coupons",Arrays.asList(couponEntity));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
