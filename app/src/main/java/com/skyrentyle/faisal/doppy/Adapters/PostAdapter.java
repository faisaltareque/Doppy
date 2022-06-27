package com.skyrentyle.faisal.doppy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skyrentyle.faisal.doppy.PostActivity.PostViewActivity;
import com.skyrentyle.faisal.doppy.PostClass.Post;
import com.skyrentyle.faisal.doppy.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolderHere> {

    Context mContext;
    List<Post> postList;

    public PostAdapter(Context mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;
    }
    @NonNull
    @Override
    public ViewHolderHere onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(mContext).inflate(R.layout.activity_post_row_view,parent,false);
        return new ViewHolderHere(row);

    }

    @Override
    public void onBindViewHolder(ViewHolderHere holder, int position) {
        holder.title.setText(postList.get(position).getTitle());
        Glide.with(mContext).load(postList.get(position).getPickedImageUrl()).into(holder.imagePost);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolderHere extends RecyclerView.ViewHolder{
        TextView title;
        ImageView imagePost;

        public ViewHolderHere(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.postRowViewTitle);
            imagePost=itemView.findViewById(R.id.postRowViewImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postView=new Intent(mContext, PostViewActivity.class);
                    int position=getAdapterPosition();
                    postView.putExtra("title",postList.get(position).getTitle());
                    postView.putExtra("postImage",postList.get(position).getPickedImageUrl());
                    postView.putExtra("description",postList.get(position).getDescription());
                    postView.putExtra("contact",postList.get(position).getContact());
                    mContext.startActivity(postView);

                }
            });
        }

    }
}

