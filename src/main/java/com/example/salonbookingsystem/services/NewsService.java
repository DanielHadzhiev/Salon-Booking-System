package com.example.salonbookingsystem.services;

import com.example.salonbookingsystem.model.dto.ExportNewsDTO;
import com.example.salonbookingsystem.model.dto.ImportNewsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NewsService {

    void postNews(ImportNewsDTO importNewsDTO);

    List<ExportNewsDTO> visualizeNews();

    Map<String,String> visualizeWeather();

    void resetNews();

}
