package com.mail.ware.feign;

import com.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient("mail-product")
public interface ProductFeignService {

    /**
     *      /product/skuinfo/info/{skuId}
     *
     *
     *   1)、让所有远程调用请求过网关, 请求路径对应网关请求，并且FeignClient 指定网关服务；
     *          1、@FeignClient("mail-gateway")：给mail-gateway所在的机器发请求
     *          2、/renren-admin/product/skuinfo/info/{skuId}
     *   2）、直接让后台指定服务处理远程调用
     *          1、@FeignClient("mail-product")
     *          2、/product/skuinfo/info/{skuId}
     *
     */

    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);

}
