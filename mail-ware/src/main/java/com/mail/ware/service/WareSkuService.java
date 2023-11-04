package com.mail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.to.OrderTo;
import com.common.to.mq.StockLockedTo;
import com.common.utils.PageUtils;
import com.mail.ware.entity.WareSkuEntity;
import com.mail.ware.vo.SkuHasStockVo;
import com.mail.ware.vo.WareSkuLockVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 17:06:03
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);

    boolean orderLockStock(WareSkuLockVo vo);

    void unlockStock(StockLockedTo to);

    @Transactional(rollbackFor = Exception.class)
    void unlockStock(OrderTo orderTo);

}

