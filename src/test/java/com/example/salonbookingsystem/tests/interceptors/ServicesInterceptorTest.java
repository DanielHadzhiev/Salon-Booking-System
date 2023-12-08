package com.example.salonbookingsystem.tests.interceptors;

import com.example.salonbookingsystem.interceptors.ServicesInterceptor;
import com.example.salonbookingsystem.model.dto.ServicesDTO;
import com.example.salonbookingsystem.services.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicesInterceptorTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private ServiceService serviceService;


    private ServicesInterceptor interceptor;

    @BeforeEach
    void setUp() {
        this.interceptor = new ServicesInterceptor(this.cacheManager,
                this.serviceService);
    }

    @Test
    void postHandleWithCachedServices() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        ModelAndView modelAndView = mock(ModelAndView.class);
        Cache cache = mock(Cache.class);

        when(this.cacheManager.getCache("services")).thenReturn(cache);

        List<ServicesDTO> cachedServices = new ArrayList<>();
        when(cache.get("getAllServices")).thenReturn(() -> cachedServices);

        this.interceptor.postHandle(request, response, handler, modelAndView);

        verify(request).setAttribute("services", cachedServices);
        verify(cache, never()).put(any(), any());
    }

    @Test
    void postHandleWithoutCachedServices() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        ModelAndView modelAndView = mock(ModelAndView.class);
        Cache cache = mock(Cache.class);

        when(this.cacheManager.getCache("services")).thenReturn(cache);
        when(cache.get("getAllServices")).thenReturn(null);

        List<ServicesDTO> services = new ArrayList<>();
        when(this.serviceService.getAllServices()).thenReturn(services);

        this.interceptor.postHandle(request, response, handler, modelAndView);

        verify(request).setAttribute("services", services);
        verify(cache).put("getAllServices", services);
    }

}
