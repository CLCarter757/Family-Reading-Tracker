package com.techelevator.ApiService;

import java.util.List;

public class ApiVolumeInfo {
    private IndustryIdentifier[] industryIdentifiers;
    private String description;
    private List<String> authors;
    private String title;
    private ImageLink imageLinks;


    public ImageLink getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLink imageLinks) {
        this.imageLinks = imageLinks;
    }



    public IndustryIdentifier[] getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(IndustryIdentifier[] industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
