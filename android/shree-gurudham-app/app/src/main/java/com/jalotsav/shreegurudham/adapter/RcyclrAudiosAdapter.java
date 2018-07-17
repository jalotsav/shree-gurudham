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
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.models.audios.MdlAudiosListResData;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/16/2018.
 */
public class RcyclrAudiosAdapter extends RecyclerView.Adapter<RcyclrAudiosAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MdlAudiosListResData> mArrylstAudios;

    public RcyclrAudiosAdapter(Context context, ArrayList<MdlAudiosListResData> arrylstVideos) {

        mContext = context;
        mArrylstAudios = new ArrayList<>();
        mArrylstAudios.addAll(arrylstVideos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lo_recyclritem_audios_list, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final MdlAudiosListResData objMdlAudios = mArrylstAudios.get(position);
        holder.mTvTitle.setText(objMdlAudios.getAudioTitle());
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(objMdlAudios.getAudioPath())) {

                    mContext.startActivity(new Intent(Intent.ACTION_VIEW)
                            .setDataAndType(Uri.parse(objMdlAudios.getAudioPath()), "audio/*"));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrylstAudios.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View mItemView;
        TextView mTvTitle;

        ViewHolder(View itemView) {
            super(itemView);

            this.mItemView = itemView;
            mTvTitle = itemView.findViewById(R.id.tv_recylrvw_item_audios_list_title);
        }
    }
}
