package com.techelevator.ApiService;

import java.util.List;

public class ApiResponse {
    private List<ApiItems> items;

    public List<ApiItems> getItems() {
        return items;
    }

    public void setItems(List<ApiItems> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    private int totalItems;
}
