package com.example.demoamqp.DeadAmqpController.config;

import lombok.Data;
import lombok.ToString;

public class Single {

    private static Single instance;

    private Single(){}

    public static Single getInstance() {

        if(null==instance){
            instance = new Single();
        }
        return instance;
    }

    public static void stInstance() {


        instance=null;
    }
}
