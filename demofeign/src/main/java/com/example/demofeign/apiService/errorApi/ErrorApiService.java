package com.example.demofeign.apiService.errorApi;

import com.example.demofeign.apiService.ApiService;
import org.springframework.stereotype.Component;

@Component
public class ErrorApiService implements ApiService {
    @Override
    public String haha(String id) {
        return "发生故障";
    }
}
