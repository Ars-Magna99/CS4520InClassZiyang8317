package com.example.cs4520_inclass_ziyang8317;

import java.util.ArrayList;

public class Articles {
    private ArrayList<Article> articles = new ArrayList<Article>();

    public ArrayList<Article> getArticles() {
        return articles;
    }


    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "articles=" + articles +
                '}';
    }
}
