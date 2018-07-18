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
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.models.pagecontent.MdlPageCntntHomeSliderImages;
import com.jalotsav.shreegurudham.models.pagecontent.MdlPageContentHomeGetRes;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/9/2018.
 */
public class VwpgrHomeSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<MdlPageCntntHomeSliderImages> mArrylstMdlPageCntntSliderImages;
    private Drawable mDrwblDefault;

    public VwpgrHomeSliderAdapter(Context context, ArrayList<MdlPageCntntHomeSliderImages> arrylstMdlPageCntntSliderImages, Drawable drwblDefault) {

        this.mContext = context;
        this.mArrylstMdlPageCntntSliderImages = arrylstMdlPageCntntSliderImages;
        mDrwblDefault = drwblDefault;
    }

    @Override
    public int getCount() {
        return this.mArrylstMdlPageCntntSliderImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.lo_vwpgr_item_home, container, false);

        ImageView mImgvwImage = viewLayout.findViewById(R.id.imgvw_vgpgr_item_home_image);

        try {

            MdlPageCntntHomeSliderImages objMdlSliderImages = mArrylstMdlPageCntntSliderImages.get(position);

            if(!TextUtils.isEmpty(objMdlSliderImages.getImagePath())) {
                Glide.with(mContext)
                        .load(objMdlSliderImages.getImagePath())
                        .apply(new RequestOptions().placeholder(mDrwblDefault))
                        .into(mImgvwImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);
    }
}
