package com.mail.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

// import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.common.exception.BizCodeEnum;
import com.common.exception.NoStockException;
import com.mail.ware.vo.SkuHasStockVo;
import com.mail.ware.vo.WareSkuLockVo;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mail.ware.entity.WareSkuEntity;
import com.mail.ware.service.WareSkuService;
import com.common.utils.PageUtils;
import com.common.utils.R;



/**
 * 商品库存
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 17:06:03
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("ware:waresku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 锁定库存
     * @param vo
     *
     * 库存解锁的场景
     *      1）、下订单成功，订单过期没有支付被系统自动取消或者被用户手动取消，都要解锁库存
     *      2）、下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚。之前锁定的库存就要自动解锁
     *
     * @return
     */
    @PostMapping(value = "/lock/order")
    public R orderLockStock(@RequestBody WareSkuLockVo vo) {

        try {
            boolean lockStock = wareSkuService.orderLockStock(vo);
            return R.ok().setData(lockStock);
        } catch (NoStockException e) {
            return R.error(BizCodeEnum.NO_STOCK_EXCEPTION.getCode(),BizCodeEnum.NO_STOCK_EXCEPTION.getMessage());
        }
    }

    @PostMapping("/hasStock")
    public R getSkusHasStock(@RequestBody List<Long> skuIds) {
        List<SkuHasStockVo> vos = wareSkuService.getSkusHasStock(skuIds);
        return R.ok().setData(vos);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("ware:waresku:info")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("ware:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("ware:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("ware:waresku:delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
