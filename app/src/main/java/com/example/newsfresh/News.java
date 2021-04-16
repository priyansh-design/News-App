package com.example.newsfresh;

public class News {
    private String author;
    private String title;
    private String url;
    private String imageUrl;



    public News(String author, String title,String url,String imageUrl){
        this.author=author;
        this.title=title;
        this.url=url;
        this.imageUrl=imageUrl;


    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
