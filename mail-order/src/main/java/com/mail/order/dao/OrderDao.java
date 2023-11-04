package com.mail.order.dao;

import com.mail.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 17:02:15
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void updateOrderStatus(String orderSn, Integer code, Integer payType);
}
