package com.mail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mail.product.entity.SpuInfoEntity;
import com.mail.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 14:38:35
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo saveVo);

    void saveBaseSpuInfo(SpuInfoEntity infoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

