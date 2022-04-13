package com.techelevator.controller;

import com.techelevator.ApiService.ApiBookService;
import com.techelevator.ApiService.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin

public class ApiBookController {
    private ApiBookService apiBookService;

    public ApiBookController(ApiBookService apiBookService) {
        this.apiBookService = apiBookService;
    }
    @GetMapping(path = "/books")
    public ApiResponse response(){
        return apiBookService.getBooks();
    }
}
