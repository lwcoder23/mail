package com.mail.product.dao;

import com.mail.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 14:38:34
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
