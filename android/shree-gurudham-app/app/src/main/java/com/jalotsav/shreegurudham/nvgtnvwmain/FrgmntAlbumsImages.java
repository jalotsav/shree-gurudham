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

package com.jalotsav.shreegurudham.nvgtnvwmain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.adapter.RcyclrAlbumsImagesAdapter;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.RecyclerViewEmptySupport;
import com.jalotsav.shreegurudham.models.images.MdlAlbumsImagesResData;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 7/10/2018.
 */
public class FrgmntAlbumsImages extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.cordntrlyot_frgmnt_albums_images) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_frgmnt_albums_images) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_frgmnt_albums_images) RecyclerViewEmptySupport mRecyclerView;

    @BindString(R.string.images_appear_here) String mImagesAppearHere;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    RecyclerView.LayoutManager mLayoutManager;
    RcyclrAlbumsImagesAdapter mAdapter;
    ArrayList<MdlAlbumsImagesResData> mArrylstMdlAlbumsImages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_albums_images, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        initSwipeRefreshLayout();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mImagesAppearHere);

        mArrylstMdlAlbumsImages = new ArrayList<>();
        mAdapter = new RcyclrAlbumsImagesAdapter(getActivity(), mArrylstMdlAlbumsImages, mDrwblDefault);
        mRecyclerView.setAdapter(mAdapter);

        callOnRefresh();

        return rootView;
    }

    // Initialization of SwipeRefreshLayout
    private void initSwipeRefreshLayout() {

        mSwiperfrshlyot.setOnRefreshListener(this);
        mSwiperfrshlyot.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryGreen),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryRed),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryBlue),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryOrange));
    }

    // call onRefresh method without swipe gesture
    private void callOnRefresh() {

        mSwiperfrshlyot.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {

        if(GeneralFunctions.isNetConnected(getActivity()))
            fetchImagesData();
        else {
            mSwiperfrshlyot.setRefreshing(false);
            Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    // call API for Get Images List
    private void fetchImagesData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mSwiperfrshlyot.setRefreshing(false);

                mArrylstMdlAlbumsImages = new ArrayList<>();
                mArrylstMdlAlbumsImages.add(new MdlAlbumsImagesResData(getString(R.string.app_name), "https://img.youtube.com/vi/J4c92aL6VlQ/0.jpg"));
                mArrylstMdlAlbumsImages.add(new MdlAlbumsImagesResData(getString(R.string.app_name), "https://img.youtube.com/vi/8G0FH3SCg4g/0.jpg"));

                mAdapter = new RcyclrAlbumsImagesAdapter(getActivity(), mArrylstMdlAlbumsImages, mDrwblDefault);
                mRecyclerView.setAdapter(mAdapter);
            }
        }, 3000);
    }
}
