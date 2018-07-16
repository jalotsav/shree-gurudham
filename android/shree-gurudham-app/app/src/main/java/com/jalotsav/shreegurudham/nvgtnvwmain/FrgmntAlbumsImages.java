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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.adapter.RcyclrAlbumsImagesAdapter;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.RecyclerViewEmptySupport;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.images.MdlAlbumsImagesRes;
import com.jalotsav.shreegurudham.models.images.MdlAlbumsImagesResData;
import com.jalotsav.shreegurudham.retrofitapi.APIGetAlbum;
import com.jalotsav.shreegurudham.retrofitapi.APIRetroBuilder;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 7/10/2018.
 */
public class FrgmntAlbumsImages extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FrgmntAlbumsImages";

    @BindView(R.id.cordntrlyot_frgmnt_albums_images) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_frgmnt_albums_images) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_frgmnt_albums_images) RecyclerViewEmptySupport mRecyclerView;

    @BindString(R.string.images_appear_here) String mImagesAppearHere;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    UserSessionManager session;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrAlbumsImagesAdapter mAdapter;
    ArrayList<MdlAlbumsImagesResData> mArrylstMdlAlbumsImages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_albums_images, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

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
            fetchImageAlbums();
        else {
            mSwiperfrshlyot.setRefreshing(false);
            Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    // Call Retrofit API
    private void fetchImageAlbums() {

        APIGetAlbum apiGetAlbum = APIRetroBuilder.getRetroBuilder(true).create(APIGetAlbum.class);
        Call<MdlAlbumsImagesRes> callMdlAlbumsImages = apiGetAlbum.callGetAlbum(session.getSelectedLanguage());
        callMdlAlbumsImages.enqueue(new Callback<MdlAlbumsImagesRes>() {
            @Override
            public void onResponse(Call<MdlAlbumsImagesRes> call, Response<MdlAlbumsImagesRes> response) {

                mSwiperfrshlyot.setRefreshing(false);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus()) {

                            if(response.body().isShowMsg())
                                Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            else {

                                if(isAdded()) { // to check fragment is attached

                                    mArrylstMdlAlbumsImages = new ArrayList<>();
                                    mArrylstMdlAlbumsImages.addAll(response.body().getArrylstMdlAlbumsImagesData());
                                    mAdapter = new RcyclrAlbumsImagesAdapter(getActivity(), mArrylstMdlAlbumsImages, mDrwblDefault);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        } else
                            Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "fetchImageAlbums() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlAlbumsImagesRes> call, Throwable t) {

                mSwiperfrshlyot.setRefreshing(false);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
