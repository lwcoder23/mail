package com.mail.product.feign;

import com.common.to.SkuReductionTo;
import com.common.to.SpuBoundTo;
import com.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient("mail-coupon")
public interface CouponFeignService {

    /**
     *  spring cloud feign 流程
     *  1. @RequestBody 将参数对象转换成 JSON
     *  2. 找到对应服务对应 controller，发送该请求，将JSON数据放在请求体中
     *  3. 对应服务接收到请求，将接收到的JSON数据封装
     *   ！！！ 所以说对应服务中不一定要形参相同，SpringMVC会自动封装（即服务双方无需使用同一个 TO）
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveSkuReductionInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
