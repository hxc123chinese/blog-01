package com.ithxc.blogdemo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hxc
 * @create 2020-03-07 20:56
 *
 * 拦截所有的异常来进行统一处理
 */
//@ControllerAdvice可以拦截掉所有注有@Controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    //注解是标识此方法可做异常处理,只要是Exception.class级别的都拦截
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        //记录请求的信息
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e );

        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null){
            //如果存在ResponseStatus这样的一个异常，就抛出，让SpringBoot处理
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //返回到的页面error/error.html
        mv.setViewName("error/error");
        return mv;
    }
}
