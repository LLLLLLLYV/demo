package com.example.demoamqp.DeadAmqpController.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class UserCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        String system = env.getProperty("os.name");
        System.out.println("系统环境为 ==="+system);
        // 系统环境在Windows才加载该bean到容器中
        if(system.contains("Windows")){
            return true;
        }else{
            return false;
        }
    }
}
