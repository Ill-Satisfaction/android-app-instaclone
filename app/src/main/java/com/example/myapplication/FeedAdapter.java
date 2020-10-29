package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    List<Post> feed;

    public FeedAdapter (Context context) {
        this.context = context;
        //this.feed = feed;
        this.feed = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_post, parent, false);
        return new FeedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        Post post = feed.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return feed.size();
    }

    public void addAll (Stack<Post> posts) {
        feed.clear();
        int length = posts.size();
        for (int i=0; i<length; i++) {
            feed.add(posts.pop());
        }
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;
        private ImageView ivMessageContent;
        private TextView tvUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tv_post_description);
            ivMessageContent = itemView.findViewById(R.id.iv_message_content);
            tvUsername = itemView.findViewById(R.id.tv_username);
        }

        public void bind (Post post) {
            tvDescription.setText(post.getDescription());

            ParseFile parseImage = post.getImage();
            String url = parseImage.getUrl();
            Uri uri = Uri.parse(url);

            Glide.with(context).load(url).into(ivMessageContent);

            //ivMessageContent.setImageURI(uri);
            tvUsername.setText( post.getUser().getUsername() );
        }
    }
}
