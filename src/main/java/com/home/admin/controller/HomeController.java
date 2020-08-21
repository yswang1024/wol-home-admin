package com.home.admin.controller;

import com.home.admin.base.util.GoogleAuthenticatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录相关
 */
@Slf4j
@RestController
@RequestMapping("/")
public class HomeController {

    /**
     * 首页
     * @param request
     * @return
     */
    @RequestMapping("")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("index");
    }

    /**
     * 登录页
     * @param request
     * @return
     */
    @RequestMapping("login")
    public ModelAndView jump2LoginPage(HttpServletRequest request) {
        String currentUser = request.getSession().getAttribute("allowedUser") == null ? "" : request.getSession().getAttribute("allowedUser").toString();
        boolean isInternalNetwork = request.getRemoteAddr().contains("192.168.31.") || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") || request.getRemoteAddr().equals("127.0.0.1");
        String localNetwork  = isInternalNetwork ? "1" : "0";
        request.setAttribute("currentClient", localNetwork);
        return new ModelAndView("login");
    }

    /**
     * 进行登录
     * @param authCode
     * @param request
     * @return
     */
    @RequestMapping("doLogin")
    public String doLogin(@RequestParam String authCode, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            //在家里不需要进行验证，根据情况修改
            boolean isInternalNetwork = request.getRemoteAddr().contains("192.168.31.") || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") || request.getRemoteAddr().equals("127.0.0.1");
            if (isInternalNetwork) {
                session.setAttribute("allowedUser", request.getRemoteAddr());
                return "success";
            } else {
                boolean authed = GoogleAuthenticatorUtil.authcode(authCode, "PUT_YOUR_GENERATED_SECRET"); //放入生成的密钥
                if (authed) {
                    session.setAttribute("allowedUser", request.getRemoteAddr());
                    return "success";
                } else {
                    return "fail";
                }
            }
        } catch (Exception e) {
            log.error("ERROR :" + e);
            return "fail";
        }
    }

    /**
     * 欢迎页
     * @param request
     * @return
     */
    @RequestMapping("welcome")
    public ModelAndView jump2WelcomePage(HttpServletRequest request) {
        return new ModelAndView("welcome");
    }

    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("logout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("allowedUser");
        return "OK";
    }


}
