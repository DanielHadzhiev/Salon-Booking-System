package com.example.salonbookingsystem.tests.schedulers;

import com.example.salonbookingsystem.schedulers.NewsScheduler;
import com.example.salonbookingsystem.services.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsSchedulerTest {

    @Mock
    private NewsService newsService;

    private NewsScheduler newsScheduler;

    @BeforeEach
    void setUp() {
        this.newsScheduler = new NewsScheduler(this.newsService);
    }

    @Test
    void resetNewsSchedulerShouldCallResetNewsInNewsService() {
        // Arrange

        // Act
        newsScheduler.resetNewsScheduler();

        // Assert
        verify(newsService, times(1)).resetNews();
    }

    @Test
    void callApiSchedulerShouldCallVisualizeWeatherInNewsService() {
        // Arrange

        // Act
        newsScheduler.callApiScheduler();

        // Assert
        verify(newsService, times(1)).visualizeWeather();
    }
}
