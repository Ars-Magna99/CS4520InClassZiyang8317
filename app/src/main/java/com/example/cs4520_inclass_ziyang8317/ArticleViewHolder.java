/*
 * CS5520 In class assignment 06
 * Name: Ziyang Wang
 * Date: 2023-03-13
 * */
package com.example.cs4520_inclass_ziyang8317;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    protected TextView articleTitle;
    protected TextView articleAuthor;
    protected TextView articleDate;
    protected TextView articleDescription;
    protected ImageView articleImage;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);

        articleTitle = itemView.findViewById(R.id.articleTitle);
        articleAuthor = itemView.findViewById(R.id.article_Author);
        articleDate = itemView.findViewById(R.id.articleDate);
        articleDescription = itemView.findViewById(R.id.articleDescription);
        articleImage = itemView.findViewById(R.id.article_image);



    }
}
