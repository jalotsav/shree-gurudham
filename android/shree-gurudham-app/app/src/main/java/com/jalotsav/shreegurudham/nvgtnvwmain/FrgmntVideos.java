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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.adapter.RcyclrVideosAdapter;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.RecyclerViewEmptySupport;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.videos.MdlVideosListRes;
import com.jalotsav.shreegurudham.models.videos.MdlVideosListResData;
import com.jalotsav.shreegurudham.retrofitapi.APIGetVideos;
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
 * Created by Jalotsav on 7/12/2018.
 */
public class FrgmntVideos extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FrgmntVideos";

    @BindView(R.id.cordntrlyot_frgmnt_videos_list) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_frgmnt_videos_list) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_frgmnt_videos_list) RecyclerViewEmptySupport mRecyclerView;

    @BindString(R.string.videos_appear_here) String mVideosAppearHere;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    UserSessionManager session;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrVideosAdapter mAdapter;
    ArrayList<MdlVideosListResData> mArrylstMdlVideos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_videos_list, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

        initSwipeRefreshLayout();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mVideosAppearHere);

        mArrylstMdlVideos = new ArrayList<>();
        mAdapter = new RcyclrVideosAdapter(getActivity(), mArrylstMdlVideos, mDrwblDefault);
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
            fetchVideosData();
        else {
            mSwiperfrshlyot.setRefreshing(false);
            Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    // call API for Get Videos List
    private void fetchVideosData() {

        APIGetVideos apiGetVideos = APIRetroBuilder.getRetroBuilder(true).create(APIGetVideos.class);
        Call<MdlVideosListRes> callMdlAlbumsImages = apiGetVideos.callGetVideos(session.getSelectedLanguage());
        callMdlAlbumsImages.enqueue(new Callback<MdlVideosListRes>() {
            @Override
            public void onResponse(Call<MdlVideosListRes> call, Response<MdlVideosListRes> response) {

                mSwiperfrshlyot.setRefreshing(false);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus()) {

                            if(response.body().isShowMsg())
                                Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            else {

                                if(isAdded()) { // to check fragment is attached

                                    mArrylstMdlVideos = new ArrayList<>();
                                    mArrylstMdlVideos.addAll(response.body().getArrylstMdlVideosListData());
                                    mAdapter = new RcyclrVideosAdapter(getActivity(), mArrylstMdlVideos, mDrwblDefault);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        } else
                            Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "fetchVideosData() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlVideosListRes> call, Throwable t) {

                mSwiperfrshlyot.setRefreshing(false);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
