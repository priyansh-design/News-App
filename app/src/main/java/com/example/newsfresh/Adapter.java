package com.example.newsfresh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<News> news;
    Context context;

    public Adapter(Context context,ArrayList<News> news) {
        this.context=context;
        this.news = news;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);

        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String t=news.get(position).getTitle();
        String au=news.get(position).getAuthor();
        String img=news.get(position).getImageUrl();
        final String ur=news.get(position).getUrl();
        holder.title.setText(t);
        holder.author.setText(au);
        Glide.with(holder.itemView.getContext()).load(img).into(holder.imageUrl);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {


                                                   Uri uri=Uri.parse(ur);
                                                   context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
                                               }
                                           }
        );

    }

    @Override
    public int getItemCount() {
        return news.size();
    }
    public void updateNews(ArrayList<News> newss){
        news.clear();
        news.addAll(newss);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView title;
        private ImageView imageUrl;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            author= itemView.findViewById(R.id.newsAuthor);
            title= itemView.findViewById(R.id.newsTitle);
            imageUrl=itemView.findViewById(R.id.newsImage);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }

}
