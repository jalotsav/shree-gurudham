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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.adapter.RcyclrAudiosAdapter;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.RecyclerViewEmptySupport;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.audios.MdlAudiosListRes;
import com.jalotsav.shreegurudham.models.audios.MdlAudiosListResData;
import com.jalotsav.shreegurudham.retrofitapi.APIGetAudios;
import com.jalotsav.shreegurudham.retrofitapi.APIRetroBuilder;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 7/16/2018.
 */
public class FrgmntAudios extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FrgmntAudios";

    @BindView(R.id.cordntrlyot_frgmnt_audios_list) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_frgmnt_audios_list) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_frgmnt_audios_list) RecyclerViewEmptySupport mRecyclerView;

    @BindString(R.string.audios_appear_here) String mAudiosAppearHere;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    UserSessionManager session;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrAudiosAdapter mAdapter;
    ArrayList<MdlAudiosListResData> mArrylstMdlAudios;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_audios_list, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

        initSwipeRefreshLayout();

        mLayoutManager = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mAudiosAppearHere);

        mArrylstMdlAudios = new ArrayList<>();
        mAdapter = new RcyclrAudiosAdapter(getActivity(), mArrylstMdlAudios);
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
            fetchAudiosData();
        else {
            mSwiperfrshlyot.setRefreshing(false);
            Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    // call API for Get Audios List
    private void fetchAudiosData() {

        APIGetAudios apiGetAudios = APIRetroBuilder.getRetroBuilder(true).create(APIGetAudios.class);
        Call<MdlAudiosListRes> callMdlAudiosList = apiGetAudios.callGetAudios(session.getSelectedLanguage());
        callMdlAudiosList.enqueue(new Callback<MdlAudiosListRes>() {
            @Override
            public void onResponse(Call<MdlAudiosListRes> call, Response<MdlAudiosListRes> response) {

                mSwiperfrshlyot.setRefreshing(false);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus()) {

                            if(response.body().isShowMsg())
                                Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            else {

                                if(isAdded()) { // to check fragment is attached

                                    mArrylstMdlAudios = new ArrayList<>();
                                    mArrylstMdlAudios.addAll(response.body().getArrylstMdlAudiosListData());
                                    mAdapter = new RcyclrAudiosAdapter(getActivity(), mArrylstMdlAudios);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        } else
                            Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "fetchAudiosData() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlAudiosListRes> call, Throwable t) {

                mSwiperfrshlyot.setRefreshing(false);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
