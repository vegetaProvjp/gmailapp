package com.example.gmailapp.adapter;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmailapp.model.EmailItemModel;

import java.util.ArrayList;
import java.util.List;

import com.example.gmailapp.R;


public class EmailItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<EmailItemModel> items;
    List<EmailItemModel> displayItems;
    String keyword;
    boolean isShowingFavorites;

    public EmailItemAdapter(List<EmailItemModel> items) {
        this.items = items;

        displayItems = new ArrayList<>();
        displayItems.addAll(this.items);
        keyword = "";
        isShowingFavorites = false;
    }

    public void showAll(){
        displayItems.clear();
        displayItems.addAll(items);
        isShowingFavorites = false;
        notifyDataSetChanged();
    }

    public void search(String keyword){
        this.keyword = keyword;
        displayItems.clear();;
        for(EmailItemModel item : items){
            if(item.getName().contains(keyword) || item.getSubject().contains(keyword) || item.getContent().contains(keyword)){
                displayItems.add(item);
            }
        }
        isShowingFavorites = false;
        notifyDataSetChanged();
    }

    public void showFavorites(){
        this.keyword = "";
        displayItems.clear();;
        for(EmailItemModel item : items){
            if(item.isFavorite()){
                displayItems.add(item);
            }
        }
        isShowingFavorites = true;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_email, parent, false);
        return new EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmailViewHolder viewHolder = (EmailViewHolder) holder;
        EmailItemModel item = displayItems.get(position);

        viewHolder.textLetter.setText(item.getName().substring(0, 1));
        Drawable background = viewHolder.textLetter.getBackground();
        background.setColorFilter(new PorterDuffColorFilter(item.getColor(), PorterDuff.Mode.SRC_ATOP));

        if(keyword.length() > 2){
            String name = item.getName().replace(keyword, "<b>" + keyword + "</b>");
            String subject = item.getSubject().replace(keyword, "<b>" + keyword + "</b>");
            String content = item.getContent().replace(keyword, "<b>" + keyword + "</b>");
            viewHolder.textName.setText(Html.fromHtml(name));
            viewHolder.textSubject.setText(Html.fromHtml(subject));
            viewHolder.textContent.setText(Html.fromHtml(content));
        } else{
            viewHolder.textName.setText(item.getName());
            viewHolder.textSubject.setText(item.getSubject());
            viewHolder.textContent.setText(item.getContent());
        }

        viewHolder.textTime.setText(item.getTime());
        if (item.isFavorite())
            viewHolder.imageFavorite.setImageResource(R.drawable.ic_star);
        else
            viewHolder.imageFavorite.setImageResource(R.drawable.ic_star_border);
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    class EmailViewHolder extends RecyclerView.ViewHolder {
        TextView textLetter;
        TextView textName;
        TextView textSubject;
        TextView textContent;
        TextView textTime;
        ImageView imageFavorite;


        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);

            textLetter = itemView.findViewById(R.id.text_letter);
            textName = itemView.findViewById(R.id.text_name);
            textSubject = itemView.findViewById(R.id.text_subject);
            textContent = itemView.findViewById(R.id.text_content);
            textTime = itemView.findViewById(R.id.text_time);
            imageFavorite = itemView.findViewById(R.id.image_favorite);

            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isFavorite = displayItems.get(getAdapterPosition()).isFavorite();
                    displayItems.get(getAdapterPosition()).setFavorite(!isFavorite);
                    if(isShowingFavorites){
                        displayItems.remove(getAdapterPosition());
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
