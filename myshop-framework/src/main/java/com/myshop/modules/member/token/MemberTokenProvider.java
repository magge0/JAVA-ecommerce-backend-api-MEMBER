package com.myshop.modules.member.token;

import com.myshop.common.context.RequestScopeContext;
import com.myshop.common.enums.ClientType;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.enums.UserEnums;
import com.myshop.common.security.token.JwtTokenUtil;
import com.myshop.common.security.token.Token;
import com.myshop.common.security.token.base.TokenGeneratorBase;
import com.myshop.modules.member.entity.dos.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class MemberTokenProvider extends TokenGeneratorBase<Member> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Token createToken(Member member, Boolean longTerm) {

        ClientType clientType;
        try {
            // Lấy loại client
            String getClientType = RequestScopeContext.getHttpRequest().getHeader("clientType");
            // Nếu loại client rỗng, mặc định là PC, khi đăng nhập bằng bên thứ ba trên PC sẽ không truyền tham số này
            if (getClientType == null) {
                clientType = ClientType.PC;
            } else {
                clientType = ClientType.valueOf(getClientType);
            }
        } catch (Exception e) {
            clientType = ClientType.UNKNOWN;
        }
        // Ghi lại thời gian đăng nhập cuối cùng và loại client
        member.setLastLoginDate(new Date());
        member.setClientType(clientType.name());

        //TODO: trien mq de goi thong tin user khi login de ghi lai log

        AuthUser user = AuthUser.builder().role(UserEnums.MEMBER).username(member.getUsername()).face(member.getFace()).id(member.getId()).nickName(member.getNickName()).longTerm(longTerm).build();
        // Tạo token khi đăng nhập thành công
        return jwtTokenUtil.createToken(user);
    }

    @Override
    public Token refreshToken(String refreshToken) {
        return jwtTokenUtil.refreshToken(refreshToken);
    }
}
