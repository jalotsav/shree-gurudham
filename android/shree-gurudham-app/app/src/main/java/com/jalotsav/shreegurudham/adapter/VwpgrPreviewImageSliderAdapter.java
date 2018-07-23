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
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.models.images.MdlImagesListResData;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/23/2018.
 */
public class VwpgrPreviewImageSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<MdlImagesListResData> mArrylstMdlImagesList;
    private Drawable mDrwblDefault;

    public VwpgrPreviewImageSliderAdapter(Context context, ArrayList<MdlImagesListResData> arrylstMdlImagesList, Drawable drwblDefault) {

        this.mContext = context;
        this.mArrylstMdlImagesList = arrylstMdlImagesList;
        mDrwblDefault = drwblDefault;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lo_vwpgr_item_dialog_preview_image, container, false);
        ImageView mImgvwPrevwImg = itemView.findViewById(R.id.imgvw_vwpgr_item_dialog_prevwimage_preview);

        String imagePath = mArrylstMdlImagesList.get(position).getImagePath();
        if (!TextUtils.isEmpty(imagePath)) {

            Glide.with(mContext)
                    .load(imagePath)
                    .apply(new RequestOptions().placeholder(mDrwblDefault))
                    .into(mImgvwPrevwImg);
        }
        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return mArrylstMdlImagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
