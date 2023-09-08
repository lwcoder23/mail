package com.mail.product.feign;

import com.common.utils.R;
import com.common.vo.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("mail-ware")
public interface WareFeignService {

    @PostMapping("ware/waresku/hasStock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);

}
