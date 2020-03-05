package com.dev.furniturestore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dev.furniturestore.Activity.PublicProfile;
import com.dev.furniturestore.R;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {


    private ArrayList<String> mNames2;
    private ArrayList<String> mImageUrls2;
    private ArrayList<String> mId2;
    private Context mContext;

    AdapterCart(Context context, ArrayList<String> names, ArrayList<String> imageUrls, ArrayList<String> id) {
        mNames2 = names;
        mImageUrls2 = imageUrls;
        mId2 = id;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_post_view_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(mImageUrls2.get(position))
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(holder.userprofile);
        Glide.with(mContext)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(mImageUrls2.get(position))
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(holder.post);

        holder.Sellername.setText(mNames2.get(position));
        holder.Uid.setText("Items (" + mId2.get(position) + ")");
        holder.PostTitle.setText("Items (" + mId2.get(position) + ")");
        holder.PostDescription.setText("Items (" + mId2.get(position) + ")");

        holder.userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, String.valueOf(mId2.get(position)), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext, PublicProfile.class);
                mContext.startActivity(intent);
            }
        });
        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, String.valueOf(mId2.get(position)), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,PublicProfile.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageUrls2.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userprofile;
        ImageView post;
        TextView Sellername;
        TextView Uid,PostTitle,PostDescription;

        ViewHolder(View itemView) {
            super(itemView);
            userprofile = itemView.findViewById(R.id.UserProfileImage);
            post = itemView.findViewById(R.id.PostImage);
            Sellername = itemView.findViewById(R.id.SellerName);
            Uid = itemView.findViewById(R.id.Uid);
            PostTitle = itemView.findViewById(R.id.PostTitle);
            PostDescription = itemView.findViewById(R.id.PostDescription);
        }
    }

}