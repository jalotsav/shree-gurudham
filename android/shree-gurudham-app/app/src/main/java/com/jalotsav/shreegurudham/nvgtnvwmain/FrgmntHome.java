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

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jalotsav.shreegurudham.ActvtyMain;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.adapter.VwpgrHomeSliderAdapter;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.home.MdlHomeResData;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Jalotsav on 7/6/2018.
 */
public class FrgmntHome extends Fragment implements AppConstants, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.cordntrlyot_frgmnt_home) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_frgmnt_home) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.vwpgr_frgmnt_home) ViewPager mViewPager;
    @BindView(R.id.vwpgr_frgmnt_home_slidrindictr) CircleIndicator mSliderIndictr;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    UserSessionManager session;
    VwpgrHomeSliderAdapter mSliderAdapter;
    ArrayList<MdlHomeResData> mArrylstMdlDashbrdResData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_home, container, false);
        ButterKnife.bind(this, rootView);

        initSwipeRefreshLayout();

        session = new UserSessionManager(getActivity());

        mArrylstMdlDashbrdResData = new ArrayList<>();
        mArrylstMdlDashbrdResData.add(new MdlHomeResData(null));

        mSliderAdapter = new VwpgrHomeSliderAdapter(getActivity(), mArrylstMdlDashbrdResData, mDrwblDefault);
        mViewPager.setAdapter(mSliderAdapter);
        mSliderIndictr.setViewPager(mViewPager);

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
            fetchHomeData();
        else {
            mSwiperfrshlyot.setRefreshing(false);
            Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    // call API for Get Home data
    private void fetchHomeData() {

        mSwiperfrshlyot.setRefreshing(false);

        mArrylstMdlDashbrdResData = new ArrayList<>();
        mArrylstMdlDashbrdResData.add(new MdlHomeResData("https://img.youtube.com/vi/J4c92aL6VlQ/0.jpg"));
        mArrylstMdlDashbrdResData.add(new MdlHomeResData("https://img.youtube.com/vi/8G0FH3SCg4g/0.jpg"));
        mArrylstMdlDashbrdResData.add(new MdlHomeResData("https://img.youtube.com/vi/jMLoiSeJzcI/0.jpg"));

        mSliderAdapter = new VwpgrHomeSliderAdapter(getActivity(), mArrylstMdlDashbrdResData, mDrwblDefault);
        mViewPager.setAdapter(mSliderAdapter);
        mSliderIndictr.setViewPager(mViewPager);
    }
}
