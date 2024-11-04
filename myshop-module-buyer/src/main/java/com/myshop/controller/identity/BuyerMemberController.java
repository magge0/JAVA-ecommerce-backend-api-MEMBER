package com.myshop.controller.identity;

import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Phiên bản người mua, giao diện thành viên
 */
@Slf4j
@RestController
@Api(tags = "Phiên bản người mua, giao diện thành viên")
@RequestMapping("/buyer/identity/member")
public class BuyerMemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "Giao diện đăng nhập")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "Tên đăng nhập", required = true, paramType = "query"), @ApiImplicitParam(name = "password", value = "Mật khẩu", required = true, paramType = "query")})
    @PostMapping("/login")
    public ResultMessage<Object> login(@NotNull(message = "Tên đăng nhập không được để trống") @RequestParam String username, @NotNull(message = "Mật khẩu không được để trống") @RequestParam String password) {
        //TODO: trien khai xac minh bang captcha truoc khi cho login
        return ResultUtil.data(this.memberService.login(username, password));
    }
}
