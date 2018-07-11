/*
 * Copyright (c) 2018 Jalotsav
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jalotsav.shreegurudham.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.models.images.MdlAlbumsImagesResData;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/10/2018.
 */
public class RcyclrAlbumsImagesAdapter extends RecyclerView.Adapter<RcyclrAlbumsImagesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MdlAlbumsImagesResData> mArrylstAlbumsImages;
    private Drawable mDrwblDefault;

    public RcyclrAlbumsImagesAdapter(Context context, ArrayList<MdlAlbumsImagesResData> arrylstAlbumsImages, Drawable drwblDefault) {

        mContext = context;
        mArrylstAlbumsImages = new ArrayList<>();
        mArrylstAlbumsImages.addAll(arrylstAlbumsImages);
        mDrwblDefault = drwblDefault;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_albums_images, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.mTvName.setText(mArrylstAlbumsImages.get(position).getName());
        String imageURL = mArrylstAlbumsImages.get(position).getCoverImageURL();
        if(!TextUtils.isEmpty(imageURL)) {
            Glide.with(mContext)
                    .load(imageURL)
                    .apply(new RequestOptions().placeholder(mDrwblDefault))
                    .into(holder.mImgvwImage);
        }
    }

    @Override
    public int getItemCount() {
        return mArrylstAlbumsImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgvwImage;
        TextView mTvName;

        ViewHolder(View itemView) {
            super(itemView);

            mImgvwImage = itemView.findViewById(R.id.imgvw_recylrvw_item_albums_images_image);
            mTvName = itemView.findViewById(R.id.tv_recylrvw_item_albums_images_name);
        }
    }

    // Remove item at given position
    private void removeAt(int position) {

        mArrylstAlbumsImages.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mArrylstAlbumsImages.size());
        notifyDataSetChanged();
    }

    // Add new Item at last position
    public void addItem(MdlAlbumsImagesResData mdlAlbumsImages) {

        mArrylstAlbumsImages.add(mdlAlbumsImages);
        notifyItemInserted(mArrylstAlbumsImages.size() - 1);
        notifyDataSetChanged();
    }

    // Get All Items
    public ArrayList<MdlAlbumsImagesResData> getAllItems() {
        return this.mArrylstAlbumsImages;
    }

    // Set Filter and Notify
    public void setFilter(ArrayList<MdlAlbumsImagesResData> arrylstAlbumsImages) {

        mArrylstAlbumsImages = new ArrayList<>();
        mArrylstAlbumsImages.addAll(arrylstAlbumsImages);
        notifyDataSetChanged();
    }
}
