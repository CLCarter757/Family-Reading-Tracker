package com.techelevator.ApiService;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiBookService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL ="https://www.googleapis.com/books/v1/volumes?q=";

    public ApiResponse getBooks(){
        List<ApiVolumeInfo> apiBooks=new ArrayList<>();
        ApiResponse response=new ApiResponse();
        response=restTemplate.getForObject(API_BASE_URL+"dale",ApiResponse.class);
       return response;

    }

}
