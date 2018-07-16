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
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalotsav.shreegurudham.adapter.RcyclrImagesListAdapter;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.RecyclerViewEmptySupport;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.images.MdlImagesListRes;
import com.jalotsav.shreegurudham.models.images.MdlImagesListResData;
import com.jalotsav.shreegurudham.retrofitapi.APIGetImage;
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
 * Created by Jalotsav on 7/11/2018.
 */
public class ActvtyImagesList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ActvtyImagesList.class.getSimpleName();

    @BindView(R.id.cordntrlyot_actvty_images_list) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_actvty_images_list) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_actvty_images_list) RecyclerViewEmptySupport mRecyclerView;

    @BindString(R.string.images_appear_here) String mImagesAppearHere;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    UserSessionManager session;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrImagesListAdapter mAdapter;
    ArrayList<MdlImagesListResData> mArrylstMdlImages;

    int mAlbumID;
    String mAlbumName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_images_list);
        ButterKnife.bind(this);

        mAlbumID = getIntent().getIntExtra(AppConstants.PUT_EXTRA_ALBUM_ID, 0);
        mAlbumName = getIntent().getStringExtra(AppConstants.PUT_EXTRA_ALBUM_NAME);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(mAlbumName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session = new UserSessionManager(this);

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

        APIGetImage apiGetImage = APIRetroBuilder.getRetroBuilder(true).create(APIGetImage.class);
        Call<MdlImagesListRes> callMdlPageContent = apiGetImage.callGetImage(session.getSelectedLanguage(), mAlbumID);
        callMdlPageContent.enqueue(new Callback<MdlImagesListRes>() {
            @Override
            public void onResponse(Call<MdlImagesListRes> call, Response<MdlImagesListRes> response) {

                mSwiperfrshlyot.setRefreshing(false);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus()) {

                            if(response.body().isShowMsg())
                                Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            else {

                                mArrylstMdlImages = new ArrayList<>();
                                mArrylstMdlImages.addAll(response.body().getArrylstMdlImagesListData());
                                mAdapter = new RcyclrImagesListAdapter(ActvtyImagesList.this, mArrylstMdlImages, mDrwblDefault);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        } else
                            Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "fetchImagesData() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlImagesListRes> call, Throwable t) {

                mSwiperfrshlyot.setRefreshing(false);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
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
