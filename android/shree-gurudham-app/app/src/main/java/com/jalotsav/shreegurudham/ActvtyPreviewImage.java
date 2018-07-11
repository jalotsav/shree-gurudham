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

package com.jalotsav.shreegurudham;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jalotsav.shreegurudham.common.AppConstants;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 7/11/2018.
 */
public class ActvtyPreviewImage extends AppCompatActivity {

    @BindView(R.id.imgvw_actvty_prevwimage_preview) ImageView mImgvwPrevwImg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_previewimage);
        ButterKnife.bind(this);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("");
        } catch (Exception e) { e.printStackTrace(); }

        String imageURL = getIntent().getStringExtra(AppConstants.PUT_EXTRA_IMAGE_URL);
        if(!TextUtils.isEmpty(imageURL)) {
            Glide.with(this)
                    .load(imageURL)
                    .apply(new RequestOptions().placeholder(mDrwblDefault))
                    .into(mImgvwPrevwImg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
