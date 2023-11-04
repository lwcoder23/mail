package com.mail.member.controller;

import java.util.Arrays;
import java.util.Map;

// import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.common.exception.BizCodeEnum;
import com.common.vo.MemberResponseVo;
import com.mail.member.exception.PhoneException;
import com.mail.member.exception.UsernameException;
import com.mail.member.feign.CouponFeignService;
import com.mail.member.vo.MemberRegistVo;
import com.mail.member.vo.MemberUserLoginVo;
import com.mail.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mail.member.entity.MemberEntity;
import com.mail.member.service.MemberService;
import com.common.utils.PageUtils;
import com.common.utils.R;

/**
 * 会员
 *
 * @author wei
 * @email lanlianhualw@goutlook.com
 * @date 2023-07-04 16:52:06
 */
@RestController
@RequestMapping("member/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    /*
    *   open feign test
    * */
    @RequestMapping("/test")
    public R test() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("name");
        R r = couponFeignService.memberCoupons();
        return R.ok().put("memberCoupons", memberEntity).put("coupons", r.get("coupons"));
    }

    @PostMapping("/regist")
    public R regist(@RequestBody MemberRegistVo memberRegistVo) {
        try {
            memberService.regist(memberRegistVo);
        } catch (UsernameException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMessage());
        } catch (PhoneException phoneException) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMessage());
        }
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody MemberUserLoginVo vo) {

        MemberEntity memberEntity = memberService.login(vo);

        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    @PostMapping("/oauth2/login")
    public R oauthLogin(@RequestBody SocialUser socialUser) throws Exception {

        MemberEntity memberEntity = memberService.login(socialUser);

        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
