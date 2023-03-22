/*
 * CS5520 In class assignment 06
 * Name: Ziyang Wang
 * Date: 2023-03-13
 * */
package com.example.cs4520_inclass_ziyang8317;

public class Article {
    private String title;
    private String author;
    private String publishedAt;
    private String urlToImage;
    private String description;


    public Article(String title, String author, String publishTime, String urlImage, String description) {
        this.title = title;
        this.author = author;
        this.publishedAt = publishTime;
        this.urlToImage = urlImage;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishTime='" + publishedAt + '\'' +
                ", urlImage='" + urlToImage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
