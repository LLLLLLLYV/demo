package com.example.demofeign.apiService;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.ResponseTimeWeightedRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {

    /**
     * 随机规则
     */
    @Bean
    public IRule ribbonRule() {
       // return new WeightedResponseTimeRule();
       return new RandomRule();
    }


}
