package com.myshop.modules.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.common.security.token.Token;
import com.myshop.modules.member.entity.dos.Member;

public interface MemberService extends IService<Member> {

    /**
     * Đăng nhập: Tên đăng nhập, mật khẩu
     *
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return token
     */
    Token login(String username, String password);
}
