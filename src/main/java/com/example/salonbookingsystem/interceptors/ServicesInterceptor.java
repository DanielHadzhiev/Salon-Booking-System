package com.example.salonbookingsystem.interceptors;

import com.example.salonbookingsystem.model.dto.ServicesDTO;
import com.example.salonbookingsystem.services.ServiceService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ServicesInterceptor implements HandlerInterceptor {

    private final CacheManager cacheManager;

    private final ServiceService serviceService;

    @Autowired
    public ServicesInterceptor(CacheManager cacheManager,
                               ServiceService serviceService) {

        this.cacheManager = cacheManager;
        this.serviceService = serviceService;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView){

        Cache cache = this.cacheManager.getCache("services");

        Cache.ValueWrapper valueWrapper=null;
        if (cache != null) {
            valueWrapper = cache.get("getAllServices");
        }

        if (valueWrapper != null) {
            List<ServicesDTO> cachedServices = (List<ServicesDTO>) valueWrapper.get();
            request.setAttribute("services", cachedServices);
        } else {

            List<ServicesDTO> services = fetchServices();
            cache.put("getAllServices", services);
            request.setAttribute("services", services);
        }
    }

    private List<ServicesDTO> fetchServices() {

        return this.serviceService.getAllServices();
    }
}
