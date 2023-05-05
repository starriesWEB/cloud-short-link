package com.starry.aspect;

import com.starry.annotation.RepeatSubmit;
import com.starry.constant.RedisKey;
import com.starry.enums.BizCodeEnum;
import com.starry.exception.BizException;
import com.starry.interceptor.LoginInterceptor;
import com.starry.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RepeatSubmitAspect {


    private final StringRedisTemplate redisTemplate;


    private final RedissonClient redissonClient;

    @Pointcut("@annotation(repeatSubmit)")
    public void pointCutNoRepeatSubmit(RepeatSubmit repeatSubmit) {

    }

    @Around("pointCutNoRepeatSubmit(repeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        //用于记录成功或者失败
        boolean res = false;
        //防重提交类型
        String type = repeatSubmit.limitType().name();
        if (type.equalsIgnoreCase(RepeatSubmit.Type.PARAM.name())) {
            //方式一，参数形式防重提交
            long lockTime = repeatSubmit.lockTime();
            String ipAddr = CommonUtil.getIpAddr(request);

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            String className = method.getDeclaringClass().getName();
            String key = "order-server:repeat_submit:" + CommonUtil.MD5(String.format("%s-%s-%s-%s", ipAddr, className, method, accountNo));
            RLock lock = redissonClient.getLock(key);
            res = lock.tryLock(0, lockTime, TimeUnit.SECONDS);

        } else {
            //方式二，令牌形式防重提交
            String requestToken = request.getHeader("request-token");
            if (StringUtils.isBlank(requestToken)) {
                throw new BizException(BizCodeEnum.ORDER_CONFIRM_TOKEN_EQUAL_FAIL);
            }
            String key = String.format(RedisKey.SUBMIT_ORDER_TOKEN_KEY, accountNo, requestToken);
            res = Boolean.TRUE.equals(redisTemplate.delete(key));
        }
        if (!res) {
            log.error("请求重复提交");
            throw new BizException(BizCodeEnum.OPS_REPEAT);
        }
        return joinPoint.proceed();

    }


}