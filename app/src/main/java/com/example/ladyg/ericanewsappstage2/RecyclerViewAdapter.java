package com.example.ladyg.ericanewsappstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> mData;

    public RecyclerViewAdapter(Context mContext, List<News> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.news_sectionname.setText(mData.get(position).getSectionName());
        holder.news_author.setText(mData.get(position).getAuthorName());
        holder.news_date.setText(mData.get(position).getWebPublicationDate());
        holder.news_title.setText(mData.get(position).getWebTitle());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clear() {
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView news_title;
        TextView news_sectionname;
        TextView news_author;
        TextView news_date;


        public MyViewHolder(View itemView) {
            super(itemView);

            news_title = itemView.findViewById(R.id.news_title);
            news_author = itemView.findViewById(R.id.author);
            news_date = itemView.findViewById(R.id.date);
            news_sectionname = itemView.findViewById(R.id.section_name);
        }
    }

}
