package com.techelevator.ApiService;

import java.util.List;

public class ApiItems {
   private ApiVolumeInfo volumeInfo;

    public ApiVolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(ApiVolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
