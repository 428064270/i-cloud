package com.icloud.common.components.annotation;

import com.icloud.common.components.base.BaseController;
import com.icloud.common.utils.http.HttpResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 42806
 */
@Aspect
@Component
public class HandlePermission {

    /**
     * 开始时间戳
     */
    private long startTime = 0;

    private final static Logger LOG = LoggerFactory.getLogger(HandlePermission.class);

    @Pointcut("@annotation(com.icloud.common.components.annotation.Permission)")
    public void permission() {
    }

    /**
     * 调用方法之前，记录时间
     */
    @Before("permission()")
    public synchronized void before() {
        //记录方法调用开始时间
        startTime = System.currentTimeMillis();
    }

    /**
     * 方法响应之后，保存日志
     *
     * @param jp
     */
    @After("permission()")
    public synchronized void after(JoinPoint jp) {
        Permission permission = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(Permission.class);
        LOG.info("执行操作描述：" + permission.description());
        LOG.info("运行总时长：" + (System.currentTimeMillis() - startTime));
    }

    /**
     * 权限判断
     *
     * @param pjp
     * @return
     */
    @Around("permission()")
    public synchronized Object around(ProceedingJoinPoint pjp) {
        try {
            BaseController target = (BaseController) pjp.getTarget();
            Permission permission = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(Permission.class);
            LOG.info("执行操作：" + permission.operation());
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return HttpResponse.error("系统发生内部错误");
        }
    }

}
