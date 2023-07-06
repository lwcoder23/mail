package com.mail.coupon.controller;

import java.util.Arrays;
import java.util.Map;

// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mail.coupon.entity.CouponEntity;
import com.mail.coupon.service.CouponService;
import com.common.utils.PageUtils;
import com.common.utils.R;

/**
 * 优惠券信息
 *
 * @author wei
 * @email lanlianhualw@outlook.com
 * @date 2023-07-04 16:43:23
 */
@RestController
@RequestMapping("coupon/coupon")
@RefreshScope // 刷新配置，动态获取配置中心的配置内容
public class CouponController {

    @Autowired
    private CouponService couponService;

    /*
     *  test nacos discovery
     * */
    @RequestMapping("/testCoupon")
    public R memberCoupons() {
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("test coupon");
        return R.ok().put("coupons", Arrays.asList(couponEntity));
    }

    /*
     *   test nacos config
     * */
    @Value("${coupon.user.name}")
    private String name;

    @RequestMapping("/testConfig")
    public R testConfig() {
        return R.ok().put("name", name);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("coupon:coupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:coupon:info")
    public R info(@PathVariable("id") Long id){
        CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("coupon:coupon:save")
    public R save(@RequestBody CouponEntity coupon){
        couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:coupon:update")
    public R update(@RequestBody CouponEntity coupon){
        couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:coupon:delete")
    public R delete(@RequestBody Long[] ids){
        couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
