package com.techelevator.ApiService;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ApiBookService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL ="https://www.googleapis.com/books/v1/volumes?q=";

    public List<ApiBook> getBooks(String searchTerm){
        List<ApiBook> apiBooks=new ArrayList<>();
        ApiResponse response=new ApiResponse();
        response=restTemplate.getForObject(API_BASE_URL+searchTerm,ApiResponse.class);
       for(ApiItems item:response.getItems()){
           ApiBook book=new ApiBook();

           if(item.getVolumeInfo().getTitle()==null)continue;
           book.setTitle(item.getVolumeInfo().getTitle());

           if(item.getVolumeInfo().getAuthors()==null)continue;
           book.setAuthors(item.getVolumeInfo().getAuthors().get(0));

           if(item.getVolumeInfo().getImageLinks()==null||item.getVolumeInfo().getImageLinks().getThumbnail()==null)continue;
           book.setBookCoverUrl(item.getVolumeInfo().getImageLinks().getThumbnail());

           if(item.getVolumeInfo().getDescription()==null)continue;
           book.setDescription(item.getVolumeInfo().getDescription());

           if(item.getVolumeInfo().getIndustryIdentifiers()==null)continue;
           book.setIsbn(item.getVolumeInfo().getIndustryIdentifiers()[item.getVolumeInfo().getIndustryIdentifiers().length-1].getIdentifier());
           apiBooks.add(book);
       }
       return apiBooks;


    }

}
