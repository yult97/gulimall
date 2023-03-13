package com.atguigu.gulimall.product.feign;

import com.atguigu.common.to.BoundsTo;
import com.atguigu.common.to.SkuReductionTo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName CouponFeign.java
 * @Description 远程调用优惠券信息
 * @createTime 2023年03月13日 22:15:00
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    /**
     * @throws
     * @title saveBoundsInfo
     * @description 会员积分服务
     * @author yubo
     * @updateTime 2023-03-13 22:56
     * @Return com.atguigu.common.utils.R
     * @Param [boundsTo]
     */
    @PostMapping("/coupon/spubounds/save")
    R saveBoundsInfo(@RequestBody BoundsTo boundsTo);

    /**
     * @throws
     * @title saveSkuReduction
     * @description 商品满减信息
     * @author yubo
     * @updateTime 2023-03-13 22:56
     * @Return com.atguigu.common.utils.R
     * @Param [skuReductionTo]
     */
    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
