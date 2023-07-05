package com.mail.member.feign;

import com.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mail-coupon")
@Service
public interface CouponFeignService {

    @RequestMapping("/coupon/coupon/testCoupon")
    R memberCoupons();

}
