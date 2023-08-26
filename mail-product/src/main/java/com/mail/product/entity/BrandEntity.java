package com.mail.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.common.valid.UpdateStatusGroup;
import com.common.valid.validAnnotation.ListValue;
import com.common.validator.group.AddGroup;
import com.common.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 14:38:34
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 * 分组校验 1.标明组别 2.controller 中标明注解 @Validated({UpdateGroup.class等分组})
	 *  指定 @Validated(group) 后校验注解不是该分组则不生效，若注解无分组则无分组的校验生效
	 */
	@Null(message = "id must null when add new", groups = {AddGroup.class})
	@NotNull(message = "update need the id", groups = {UpdateGroup.class})
	@TableId()
	private Long brandId;
	/**
	 * 品牌名
	 *  JSR303 数据校验，为 bean 添加校验注解，并在 controller方法加上 @Valid
	 */
	@NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty
	@URL(message = "需要合法的url地址", groups = {AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 * 自定义校验 1.校验注解 2.校验器 3.关联自定义的校验注解和自定义校验器
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@ListValue(values = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 * validation 的正则表达式的校验
	 */
	@NotNull(message = "not null", groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须为单个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "not null", groups = {AddGroup.class})
	private Integer sort;

}
