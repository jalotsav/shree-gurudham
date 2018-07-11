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
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jalotsav.shreegurudham.ActvtyPreviewImage;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.models.images.MdlImagesListResData;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/11/2018.
 */
public class RcyclrImagesListAdapter extends RecyclerView.Adapter<RcyclrImagesListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MdlImagesListResData> mArrylstImages;
    private Drawable mDrwblDefault;

    public RcyclrImagesListAdapter(Context context, ArrayList<MdlImagesListResData> arrylstImages, Drawable drwblDefault) {

        mContext = context;
        mArrylstImages = new ArrayList<>();
        mArrylstImages.addAll(arrylstImages);
        mDrwblDefault = drwblDefault;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_images_list, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final String imageURL = mArrylstImages.get(position).getImageURL();
        if(!TextUtils.isEmpty(imageURL)) {
            Glide.with(mContext)
                    .load(imageURL)
                    .apply(new RequestOptions().placeholder(mDrwblDefault))
                    .into(holder.mImgvwImage);
        }

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, ActvtyPreviewImage.class)
                        .putExtra(AppConstants.PUT_EXTRA_IMAGE_URL, imageURL));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrylstImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View mItemView;
        ImageView mImgvwImage;

        ViewHolder(View itemView) {
            super(itemView);

            this.mItemView = itemView;
            mImgvwImage = itemView.findViewById(R.id.imgvw_recylrvw_item_images_list_image);
        }
    }
}
