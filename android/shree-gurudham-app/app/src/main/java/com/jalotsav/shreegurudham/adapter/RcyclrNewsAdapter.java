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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.models.news.MdlNewsListResData;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/17/2018.
 */
public class RcyclrNewsAdapter extends RecyclerView.Adapter<RcyclrNewsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MdlNewsListResData> mArrylstNews;

    public RcyclrNewsAdapter(Context context, ArrayList<MdlNewsListResData> arrylstNews) {

        mContext = context;
        mArrylstNews = new ArrayList<>();
        mArrylstNews.addAll(arrylstNews);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_news_list, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final MdlNewsListResData objMdlNews = mArrylstNews.get(position);
        holder.mTvTitle.setText(objMdlNews.getNewsTitle());
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*mContext.startActivity(new Intent(mContext, .class)
                        .putExtra(AppConstants.PUT_EXTRA_NEWS_ID, objMdlNews.getNewsID()));*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrylstNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View mItemView;
        TextView mTvTitle;

        ViewHolder(View itemView) {
            super(itemView);

            this.mItemView = itemView;
            mTvTitle = itemView.findViewById(R.id.tv_recylrvw_item_news_list_title);
        }
    }
}
