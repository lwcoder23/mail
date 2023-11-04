package com.mail.order.vo;

import lombok.Data;

/**
 * 是否有库存
 */
@Data
public class SkuStockVo {

    private Long skuId;

    private Boolean hasStock;

}
