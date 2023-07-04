package com.mail.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mail.product.entity.BrandEntity;
import com.mail.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MailProductApplicationTests {

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {

		BrandEntity brandEntity = new BrandEntity();

		List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
		for (BrandEntity entity : list) {
			System.out.println(entity);
		}

		/*brandEntity.setName("huawei");
		brandService.save(brandEntity);*/

		/*brandEntity.setBrandId(1L);
		brandEntity.setName("xiaomi");
		brandService.updateById(brandEntity);*/

	}

}
