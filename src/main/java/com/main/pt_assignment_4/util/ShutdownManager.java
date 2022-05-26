package com.main.pt_assignment_4.util;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class ShutdownManager {

    @PreDestroy
    public void onShutdown(){
        Serializer.serializeAll();
        System.out.println("Successfully serialized!\n");

    }
}
