package com.techelevator.controller;

import com.techelevator.ApiService.ApiBook;
import com.techelevator.ApiService.ApiBookService;
import com.techelevator.ApiService.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin

public class ApiBookController {
    private ApiBookService apiBookService;

    public ApiBookController(ApiBookService apiBookService) {
        this.apiBookService = apiBookService;
    }
    @GetMapping(path = "/books")
    public List<ApiBook> response(@RequestParam String q){
        return apiBookService.getBooks(q);
    }
}
