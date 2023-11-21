package com.example.salonbookingsystem.schedulers;

import com.example.salonbookingsystem.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewsScheduler {

    private final NewsService newsService;

    @Autowired
    public NewsScheduler(NewsService newsService) {
        this.newsService = newsService;
    }

    @Scheduled(fixedRate = 43200000)
    public void resetNewsScheduler(){
        this.newsService.resetNews();
    }

    @Scheduled(fixedRate = 1800000)
    public void callApiScheduler(){
        this.newsService.visualizeWeather();
    }
}
