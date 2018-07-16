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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jalotsav.shreegurudham.ActvtyPreviewVideo;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.models.videos.MdlVideosListResData;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/12/2018.
 */
public class RcyclrVideosAdapter extends RecyclerView.Adapter<RcyclrVideosAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MdlVideosListResData> mArrylstVideos;
    private Drawable mDrwblDefault;

    public RcyclrVideosAdapter(Context context, ArrayList<MdlVideosListResData> arrylstVideos, Drawable drwblDefault) {

        mContext = context;
        mArrylstVideos = new ArrayList<>();
        mArrylstVideos.addAll(arrylstVideos);
        mDrwblDefault = drwblDefault;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_videos_list, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final MdlVideosListResData objMdlVideos = mArrylstVideos.get(position);
        holder.mTvTitle.setText(objMdlVideos.getVideoTitle());
        if(!TextUtils.isEmpty(objMdlVideos.getYoutubeID())) {
            Glide.with(mContext)
                    .load(AppConstants.ROOT_URL_YOUTUBE_THUMBNAIL
                            .concat(objMdlVideos.getYoutubeID())
                            .concat(AppConstants.YOUTUBE_THUMBNAIL_IMAGE_NAME))
                    .apply(new RequestOptions().placeholder(mDrwblDefault))
                    .into(holder.mImgvwImage);
        }
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, ActvtyPreviewVideo.class)
                        .putExtra(AppConstants.PUT_EXTRA_YOUTUBE_VIDEOID, objMdlVideos.getYoutubeID()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrylstVideos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View mItemView;
        ImageView mImgvwImage;
        TextView mTvTitle;

        ViewHolder(View itemView) {
            super(itemView);

            this.mItemView = itemView;
            mImgvwImage = itemView.findViewById(R.id.imgvw_recylrvw_item_videos_list_thumbnail);
            mTvTitle = itemView.findViewById(R.id.tv_recylrvw_item_videos_list_title);
        }
    }
}
