package com.mail.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.mail.product.dao.CategoryDao;
import com.mail.product.entity.CategoryEntity;
import com.mail.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {

        List<CategoryEntity> allCategoryEntity = this.baseMapper.selectList(null);

        List<CategoryEntity> level1Menus = allCategoryEntity.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map( categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, allCategoryEntity));
            return categoryEntity;
        }).sorted( (menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查当前删除的菜单是都在其它地方被引用
        baseMapper.deleteBatchIds(asList);
    }

    // 递归获取子分类
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> allCategoryEntity) {

        List<CategoryEntity> children = allCategoryEntity.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == root.getCatId()
        ).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, allCategoryEntity));
            return categoryEntity;
        }).sorted((menu1, menu2) ->
                (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())
        ).collect(Collectors.toList());

        return children;
    }

}