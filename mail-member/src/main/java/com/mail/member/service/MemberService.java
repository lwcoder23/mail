package com.mail.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.mail.member.entity.MemberEntity;
import com.mail.member.exception.PhoneException;
import com.mail.member.exception.UsernameException;
import com.mail.member.vo.MemberRegistVo;
import com.mail.member.vo.MemberUserLoginVo;
import com.mail.member.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 16:52:06
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo memberRegistVo);

    void checkPhoneUnique(String phone) throws PhoneException;

    void checkUsernameUnique(String userName) throws UsernameException;

    MemberEntity login(MemberUserLoginVo vo);

    MemberEntity login(SocialUser socialUser) throws Exception;
}

