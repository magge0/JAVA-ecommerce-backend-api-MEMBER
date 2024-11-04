package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.security.token.Token;
import com.myshop.modules.member.entity.dos.Member;
import com.myshop.modules.member.service.MemberService;
import com.myshop.modules.member.token.MemberTokenProvider;
import com.myshop.modules.product.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberTokenProvider memberTokenProvider;

    @Override
    public Token login(String username, String password) {
        Member foundMember = this.findMember(username);
        // Kiểm tra xem người dùng có tồn tại hay không
        if (foundMember == null || !foundMember.getDisabled()) {
            throw new ServiceException(ResultCode.USER_NOT_FOUND);
        }
        // Kiểm tra xem mật khẩu có chính xác hay không
        if (!new BCryptPasswordEncoder().matches(password, foundMember.getPassword())) {
            throw new ServiceException(ResultCode.USER_PASSWORD_ERROR);
        }
        return memberTokenProvider.createToken(foundMember, false);
    }

    /**
     * Truyền số điện thoại hoặc tên đăng nhập
     *
     * @param userName Số điện thoại hoặc tên đăng nhập
     * @return Thông tin thành viên
     */
    private Member findMember(String userName) {
        QueryWrapper<Member> query = new QueryWrapper<>();
        query.eq("username", userName).or().eq("mobile", userName);
        return this.getOne(query, false);
    }


}
