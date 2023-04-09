package com.example.museumapp.objects;

public class Item {
    private String itemID;
    private String name;
    private String description;
    private int startYear;
    private int endYear;
    private String itemUrl;
    private String imageUrl;
    private int closetID;

    public Item(String itemID, String name, String description, int startYear, int endYear, String itemUrl, String imageUrl, int closetID) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.startYear = startYear;
        this.endYear = endYear;
        this.itemUrl = itemUrl;
        this.imageUrl = imageUrl;
        this.closetID = closetID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getClosetID() {
        return closetID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setClosetID(int closetID) {
        this.closetID = closetID;
    }

    public int getImageResource() {
        return 0;
    }
}
