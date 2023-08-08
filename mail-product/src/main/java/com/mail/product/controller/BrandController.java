package com.mail.product.controller;

import java.util.Arrays;
import java.util.Map;

// import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.common.valid.UpdateStatusGroup;
import com.common.validator.group.AddGroup;
import com.common.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mail.product.entity.BrandEntity;
import com.mail.product.service.BrandService;
import com.common.utils.PageUtils;
import com.common.utils.R;


/**
 * 品牌
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 15:08:40
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    // @RequiresPermissions("product:b
    // *rand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("product:brand:save")
    // BindingResult 校验结果
    public R save(/*@Valid*/ @Validated(AddGroup.class) @RequestBody BrandEntity brand/*BindingResult result*/){

        /*if(result.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            result.getFieldErrors().forEach((item) -> {
                // get error message
                String errorMessage = item.getDefaultMessage();
                // error field name
                String field = item.getField();
                map.put(field, errorMessage);
            });
            return R.error(400, "提交的数据不合法").put("data", map);
        } else {
            brandService.save(brand);
        }*/
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:brand:update")
    public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand){
		brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/status")
    // @RequiresPermissions("product:brand:update")
    public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand){
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
