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
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.adapter.VwpgrHomeSliderAdapter;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.pagecontent.MdlPageCntntHomeSliderImages;
import com.jalotsav.shreegurudham.models.pagecontent.MdlPageContentHomeGetRes;
import com.jalotsav.shreegurudham.retrofitapi.APIPageContent;
import com.jalotsav.shreegurudham.retrofitapi.APIRetroBuilder;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 7/6/2018.
 */
public class FrgmntHome extends Fragment implements AppConstants {

    private static final String TAG = "FrgmntHome";

    @BindView(R.id.cordntrlyot_frgmnt_home) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.vwpgr_frgmnt_home) ViewPager mViewPager;
    @BindView(R.id.vwpgr_frgmnt_home_slidrindictr) CircleIndicator mSliderIndictr;
    @BindView(R.id.tv_frgmnt_home_title) TextView mTvTitle;
    @BindView(R.id.tv_frgmnt_home_descrptn) TextView mTvDescrptn;
    @BindView(R.id.prgrsbr_frgmnt_home) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    @BindDrawable(R.drawable.img_home_slider_default) Drawable mDrwblDefault;

    UserSessionManager session;
    VwpgrHomeSliderAdapter mSliderAdapter;
    ArrayList<MdlPageCntntHomeSliderImages> mArrylstMdlPageCntntSliderImages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_home, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

        mArrylstMdlPageCntntSliderImages = new ArrayList<>();
        mArrylstMdlPageCntntSliderImages.add(new MdlPageCntntHomeSliderImages(null));
        mSliderAdapter = new VwpgrHomeSliderAdapter(getActivity(), mArrylstMdlPageCntntSliderImages, mDrwblDefault);
        mViewPager.setAdapter(mSliderAdapter);
        mSliderIndictr.setViewPager(mViewPager);

        mTvDescrptn.setMovementMethod(new ScrollingMovementMethod());

        if (GeneralFunctions.isNetConnected(getActivity()))
            fetchHomeData();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();

        return rootView;
    }

    // call API for Get Home data
    private void fetchHomeData() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        APIPageContent apiPageContent = APIRetroBuilder.getRetroBuilder(false).create(APIPageContent.class);
        Call<MdlPageContentHomeGetRes> callMdlPageCntntHome = apiPageContent.callGetPageContentHome(session.getSelectedLanguage());
        callMdlPageCntntHome.enqueue(new Callback<MdlPageContentHomeGetRes>() {
            @Override
            public void onResponse(Call<MdlPageContentHomeGetRes> call, Response<MdlPageContentHomeGetRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus()) {

                            if(response.body().isShowMsg())
                                Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            else {

                                if(isAdded()) { // to check fragment is attached

                                    mArrylstMdlPageCntntSliderImages = new ArrayList<>();
                                    mArrylstMdlPageCntntSliderImages.addAll(response.body().getArrylstMdlPageCntntSliderImages());
                                    mSliderAdapter = new VwpgrHomeSliderAdapter(getActivity(), mArrylstMdlPageCntntSliderImages, mDrwblDefault);
                                    mViewPager.setAdapter(mSliderAdapter);
                                    mSliderIndictr.setViewPager(mViewPager);
                                    mTvTitle.setText(response.body().getPageTitle());
                                    mTvDescrptn.setText(response.body().getMetterDescription());
                                }
                            }
                        } else
                            Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "fetchHomeData() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlPageContentHomeGetRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_refresh_overflow, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:

                if (GeneralFunctions.isNetConnected(getActivity()))
                    fetchHomeData();
                else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
