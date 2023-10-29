package com.mail.cart.to;

import lombok.Data;

@Data
public class UserInfoTo {

    // 已登录的用户使用数据库保存的 Id
    private Long userId;

    // 这是为零时用户生成的 user-key
    private String userKey;

    /**
     * 是否临时用户
     */
    private Boolean tempUser = false;

}
