package com.mail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mail.product.entity.CategoryEntity;
import com.mail.product.service.CategoryService;
import com.common.utils.PageUtils;
import com.common.utils.R;



/**
 * 商品三级分类
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 15:08:40
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 以树形结构返回所有分类以及其子分类
     */
    @RequestMapping("/list/tree")
    public R list(){

        List<CategoryEntity> categoryEntities = categoryService.listWithTree();

        return R.ok().put("data", categoryEntities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    // @RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);
        return R.ok();
    }

    /*
    *  批量修改
    * */
    @RequestMapping("/update/updateByArray")
    public R update(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        return  R.ok();
    }

    /**
     * 删除
     * @RequestBody 请求体中带参数，post请求才有请求体
     * springMVC 自动将请求体中的参数（json）转换为对应的对象
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds){

		// categoryService.removeByIds(Arrays.asList(catIds));

        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
