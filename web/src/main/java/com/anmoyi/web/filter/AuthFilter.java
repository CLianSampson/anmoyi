package com.anmoyi.web.filter;


import com.alibaba.fastjson.JSON;
import com.anmoyi.common.*;
import com.anmoyi.model.po.User;
import com.anmoyi.service.UserService;
import com.anmoyi.web.UrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)   //@Order注解表示执行过滤顺序，值越小，越先执行
@Component
@ComponentScan
@WebFilter(urlPatterns = "/*",filterName = "authFilter")
public class AuthFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("enter filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {


            //解决跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            //解决跨域



            String contextPath = request.getContextPath();
            String uri = request.getRequestURI();

            logger.info("contextPath=" + contextPath);
            logger.info("uri=" + uri);

            // 去除工程名
            if (null != contextPath && !contextPath.equals("") && !contextPath.equals("/")) {
                uri = uri.substring(contextPath.length());
            }

            logger.info("processuri=" + uri);
            for (String urlConfig: UrlConfig.urlAyyy) {
                if (uri.equals(urlConfig)){
                    //不需要校验
                    filterChain.doFilter(servletRequest,servletResponse);
                    return;
                }
            }


            // 其他请求，需要鉴权
            String tokenStr = request.getHeader("token");
            if (StringUtil.isNullOrBlank(tokenStr)){
                logger.info("token为空");
                returnToClient(response, AppError.APP_TOKEN_IS_NULL_ERROR);
                return;
            }


            String phone = TokenUtil.getEmail(tokenStr);
            User user = userService.getByPhone(phone);
            if (null == user) {
                logger.info("token无效，用户不存在");
                returnToClient(response,AppError.APP_TOKEN_INVALID_ERROR);
                return;

            }


            long dif = DateUtil.getMinute(user.getUpdateTime());
            if (dif > Const.USER_LOGIN_INVALID_TIME) {// 单位，分钟
                //需要用户重新登陆
                logger.info("token无效，过期");
                returnToClient(response,AppError.APP_TOKEN_INVALID_ERROR);
                return;

            }

            //使用的用户之前的token ，该token未过期
            if(!user.getToken().equals(tokenStr) ){
                logger.info("token无效，不正确");
                returnToClient(response,AppError.APP_TOKEN_INVALID_ERROR);
                return;
            }

            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }catch (Exception e){
            logger.info("" , e);
            returnToClient(response,AppError.APP_ERROR);
        }

    }


    @Override
    public void destroy() {

    }

    private void returnToClient(HttpServletResponse response , AppError appError){
        Packet rtvPacket = new Packet();
        rtvPacket.setCode(appError.getCode());
//        rtvPacket.setData(appError.getMessage());
        rtvPacket.setMessage(appError.getMessage());
        PrintWriterUtil.writeResultToClient(response, JSON.toJSON(rtvPacket).toString());
    }

}
