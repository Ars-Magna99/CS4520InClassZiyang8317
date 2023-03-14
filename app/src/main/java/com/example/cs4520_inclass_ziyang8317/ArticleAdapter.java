package com.example.cs4520_inclass_ziyang8317;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    Context context;
    ArrayList<Article> articles = null;

    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.article_layout_hori,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        holder.articleTitle.setText(articles.get(position).getTitle());
        holder.articleAuthor.setText("Author: "+articles.get(position).getAuthor());
        holder.articleDate.setText("Date: "+articles.get(position).getPublishedAt());
        if(articles.get(position).getDescription() == null || articles.get(position).getDescription().equals("null")){
            holder.articleDescription.setVisibility(View.GONE);
        }else {
            holder.articleDescription.setText("Description: " + articles.get(position).getDescription());
        }
        String url = articles.get(position).getDescription();



        if(url == null){
            holder.articleImage.setVisibility(View.GONE);
        } else{
            Glide.with(context).load(articles.get(position).getUrlToImage()).into(holder.articleImage);
        }







    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
