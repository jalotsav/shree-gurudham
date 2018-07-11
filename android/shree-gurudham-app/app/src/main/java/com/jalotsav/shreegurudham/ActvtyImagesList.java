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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalotsav.shreegurudham.adapter.RcyclrImagesListAdapter;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.RecyclerViewEmptySupport;
import com.jalotsav.shreegurudham.models.images.MdlImagesListResData;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 7/11/2018.
 */
public class ActvtyImagesList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.cordntrlyot_actvty_images_list) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_actvty_images_list) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_actvty_images_list) RecyclerViewEmptySupport mRecyclerView;

    @BindString(R.string.images_appear_here) String mImagesAppearHere;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    RecyclerView.LayoutManager mLayoutManager;
    RcyclrImagesListAdapter mAdapter;
    ArrayList<MdlImagesListResData> mArrylstMdlImages;

    String mAlbumName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_images_list);
        ButterKnife.bind(this);

        mAlbumName = getIntent().getStringExtra(AppConstants.PUT_EXTRA_ALBUM_NAME);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(mAlbumName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initSwipeRefreshLayout();

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mImagesAppearHere);

        mArrylstMdlImages = new ArrayList<>();
        mAdapter = new RcyclrImagesListAdapter(this, mArrylstMdlImages, mDrwblDefault);
        mRecyclerView.setAdapter(mAdapter);

        callOnRefresh();
    }

    // Initialization of SwipeRefreshLayout
    private void initSwipeRefreshLayout() {

        mSwiperfrshlyot.setOnRefreshListener(this);
        mSwiperfrshlyot.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimaryGreen),
                ContextCompat.getColor(this, R.color.colorPrimaryRed),
                ContextCompat.getColor(this, R.color.colorPrimaryBlue),
                ContextCompat.getColor(this, R.color.colorPrimaryOrange));
    }

    // call onRefresh method without swipe gesture
    private void callOnRefresh() {

        mSwiperfrshlyot.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {

        if(GeneralFunctions.isNetConnected(this))
            fetchImagesData();
        else {
            mSwiperfrshlyot.setRefreshing(false);
            Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    // call API for Get Images List of selected Album
    private void fetchImagesData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mSwiperfrshlyot.setRefreshing(false);

                mArrylstMdlImages = new ArrayList<>();
                mArrylstMdlImages.add(new MdlImagesListResData("https://img.youtube.com/vi/J4c92aL6VlQ/0.jpg"));
                mArrylstMdlImages.add(new MdlImagesListResData("https://img.youtube.com/vi/8G0FH3SCg4g/0.jpg"));
                mArrylstMdlImages.add(new MdlImagesListResData("https://img.youtube.com/vi/jMLoiSeJzcI/0.jpg"));

                mAdapter = new RcyclrImagesListAdapter(ActvtyImagesList.this, mArrylstMdlImages, mDrwblDefault);
                mRecyclerView.setAdapter(mAdapter);
            }
        }, 3000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
