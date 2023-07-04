package com.mail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mail.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 17:02:15
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

