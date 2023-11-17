package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.dto.ExportNewsDTO;
import com.example.salonbookingsystem.model.dto.ImportNewsDTO;
import com.example.salonbookingsystem.model.entity.News;
import com.example.salonbookingsystem.model.entity.User;
import com.example.salonbookingsystem.repositories.NewsRepository;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.NewsService;
import com.example.salonbookingsystem.session.LoggedUser;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

    @Value("${openweathermap.apikey}")
    private String apiKey;

    private final RestTemplate restTemplate;


    private final NewsRepository newsRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final LoggedUser loggedUser;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository,
                           ModelMapper modelMapper,
                           UserRepository userRepository,
                           LoggedUser loggedUser,
                           RestTemplate restTemplate) {

        this.newsRepository = newsRepository;

        this.modelMapper = modelMapper;

        this.userRepository = userRepository;

        this.loggedUser = loggedUser;

        this.restTemplate =restTemplate;
    }

    @Override
    public void postNews(ImportNewsDTO importNewsDTO) {

        Optional<User> user = this.userRepository.findByEmail(this.loggedUser.getEmail());

        if(user.isPresent()){

            News news = this.modelMapper.map(importNewsDTO, News.class);
            news.setPublisher(user.get());
            this.newsRepository.save(news);

        }

    }

    @Override
    public List<ExportNewsDTO> visualizeNews() {

        List<News> news = this.newsRepository.findAll();
        List<ExportNewsDTO> newsDTOS = new ArrayList<>();

        if(news.isEmpty()){
            return new ArrayList<>();
        }

        for(News currentNews :news){

            ExportNewsDTO newsDTO = this.modelMapper.map(currentNews, ExportNewsDTO.class);
            newsDTO.setPublisherName(currentNews.getPublisher().getName());
            newsDTOS.add(newsDTO);

        }

        return newsDTOS;

    }

    @Override
    public Map<String, String> visualizeWeather() {

        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + "Sofia" + "&appid=" + this.apiKey + "&units=metric";

        try {

            String response = restTemplate.getForObject(apiUrl, String.class);
            Map<String,String>result = new HashMap<>();

            JSONObject json = new JSONObject(response);

            String main = json.getJSONArray("weather").getJSONObject(0).getString("main");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            String icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");
            String temp = String.valueOf(Math.round(json.getJSONObject("main").getDouble("temp")));

            result.put("weather",main);
            result.put("description",description);
            result.put("icon",icon);
            result.put("temp",temp);

            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
