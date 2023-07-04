package com.mail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mail.ware.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 17:06:04
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

