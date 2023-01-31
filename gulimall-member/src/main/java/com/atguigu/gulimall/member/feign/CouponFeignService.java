package com.atguigu.gulimall.member.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 调用优惠券服务
 * @author yubo
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    /**
     * 优惠券查询
     * @return
     */
    @RequestMapping("/coupon/coupon/member/coupons/list")
    public R membercouponsList();
}
