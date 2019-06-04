package com.example.demoamqp.DeadAmqpController.config;

import com.example.demoamqp.DeadAmqpController.bean.User;
import org.apache.tomcat.util.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class WebExceptionAspest {

    private static final Logger logger = LoggerFactory.getLogger(WebExceptionAspest.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void webPointcut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void beforPointcut() {
    }

    //@Before("beforPointcut()")
    public void beforMethod(JoinPoint joinPoint){
//        result.put("data",animal);//成功返回数据
        logger.info("目标方法名为:" + joinPoint.getSignature().getName());
        logger.info("目标方法所属类的简单类名:" +        joinPoint.getSignature().getDeclaringType().getSimpleName());
        logger.info("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }
        logger.info("被代理的对象:" + joinPoint.getTarget());
        logger.info("代理对象自己:" + joinPoint.getThis());
    }

   // @Around("beforPointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjp){
        logger.info("-------------------------------------controller start------------------------------------------------");
        try{
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();

            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            logger.info("Request, url: {"+url+"}, method: {"+method+"}, uri: {"+uri+"}, params: {"+queryString+"}");

            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method methodObject = methodSignature.getMethod();
            String operationType = "";
            String operationName = "";

            //operationType = methodObject.getAnnotation(Log.class).operationType();
            //operationName = methodObject.getAnnotation(Log.class).operationName();

           // logger.info("Method Type:" + operationType);
           // logger.info("Method Desc:" + operationName);

            Object args[] = pjp.getArgs();
            for (Object arg : args) {
                logger.info(arg.toString());
            }
        }catch(Exception e){
            logger.error(e.getMessage());



        }
        // result的值就是被拦截方法的返回
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            logger.error("异常");
        }

        logger.info("Response: " + result);

        logger.info("--------------------------------------controller end-------------------------------------------------");
        return null;
    }


   //@AfterThrowing(pointcut = "webPointcut()", throwing = "e")
    public void handleThrowing(JoinPoint joinPoint, Exception e) {

        //不需要再记录ServiceException，因为在service异常切面中已经记录过
       logger.error(e.getMessage());
        /*String errorMsg = StringUtils.isEmpty(e.getMessage()) ? "系统异常" : e.getMessage();
        writeContent(errorMsg);*/
    }

    private void writeContent(String content) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            logger.error("writeContent,error:"+e.getMessage());
        }
        writer.print(content);
        writer.flush();
        writer.close();
    }

}
