package com.mail.member.dao;

import com.mail.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 16:52:06
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
