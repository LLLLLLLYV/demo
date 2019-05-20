package com.example.demofeign.apiService;

import com.example.demofeign.apiService.errorApi.ErrorApiService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "eurekaclient",fallback = ErrorApiService.class)
public interface ApiService {

    @RequestMapping(value = "/haha" ,method = RequestMethod.GET)
    public String haha(@RequestParam("id") String id);
}
