package com.mail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.to.mq.SeckillOrderTo;
import com.common.utils.PageUtils;
import com.mail.order.entity.OrderEntity;
import com.mail.order.vo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 17:02:15
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity orderEntity);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    void createSeckillOrder(SeckillOrderTo orderTo);

    PayVo getOrderPay(String orderSn);

    PageUtils queryPageWithItem(Map<String, Object> params);

    @Transactional(rollbackFor = Exception.class)
    String handlePayResult(PayAsyncVo asyncVo);

    String asyncNotify(String notifyData);
}

