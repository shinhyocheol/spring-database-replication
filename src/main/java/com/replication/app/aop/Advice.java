package com.replication.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Aspect: 해당 클래스의 객체가 Aspect를 구현한 것이라는 것을 Spring에게 알린다.
 * @Component: AOP와 관계는 없다. 그러나 스프링에서는 AOP는 Bean에만 적용할 수 있으므로 해당 어노테이션을 선언해 Bean으로 등록한다.
 */
@Aspect
@Component
@Slf4j
public class Advice {

    /**
     * @Before: 해당 클래스의 메소드 실행 전
     * within : ProductController 클래스를 지정
     */
    @Before("within (com.replication.app.api.product.controller.ProductController)")
    public void productControllerBeforeAdvice() {
        log.debug("Product Controller Before");
    }

}
