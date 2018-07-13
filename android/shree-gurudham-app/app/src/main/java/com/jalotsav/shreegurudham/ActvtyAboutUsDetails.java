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

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.pagecontent.MdlPageContentGetRes;
import com.jalotsav.shreegurudham.models.pagecontent.MdlPageContentGetResData;
import com.jalotsav.shreegurudham.retrofitapi.APIPageContent;
import com.jalotsav.shreegurudham.retrofitapi.APIRetroBuilder;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 7/13/2018.
 */
public class ActvtyAboutUsDetails extends AppCompatActivity implements AppConstants, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = ActvtyAboutUsDetails.class.getSimpleName();

    UserSessionManager session;

    @BindView(R.id.cordntrlyot_actvty_aboutus_details) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.toolbar_actvty_aboutus_details) Toolbar mToolbar;
    @BindView(R.id.imgvw_actvty_aboutus_details_headerimage) ImageView mImgvwHeaderImage;
    @BindView(R.id.tv_actvty_aboutus_details_headertitle) TextView mTvHeaderTitle;
    @BindView(R.id.tv_actvty_aboutus_details_content) TextView mTvContent;
    @BindView(R.id.prgrsbr_actvty_aboutus_details) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    String mPageKey;
    int mSelectedPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_aboutus_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(true);

        session = new UserSessionManager(this);

        mPageKey = getIntent().getStringExtra(PUT_EXTRA_PAGE_KEY);
        mSelectedPosition = getIntent().getIntExtra(PUT_EXTRA_POSITION, 0);

        // To prevent null value in initCollapsingToolbar()
        mTvHeaderTitle.setText(getString(R.string.app_name));

        initCollapsingToolbar();
        setHeaderDetailsUI();

        if (GeneralFunctions.isNetConnected(this))
            getAboutUsDetails();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
    }

    // Initial CollapsingToolbar
    private void initCollapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.colpstoolbar_actvty_aboutus_details);
        collapsingToolbar.setTitle(" ");
//        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        AppBarLayout appBarLayout = findViewById(R.id.appbarlyot_actvty_aboutus_details);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(mTvHeaderTitle.getText().toString());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    // Set selected About details on UI from Intent extra
    private void setHeaderDetailsUI() {

        switch (mSelectedPosition) {
            case 0:

                mImgvwHeaderImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_home_slider_default));
                mTvHeaderTitle.setText(getString(R.string.aboutus_title_1));
                mTvContent.setText(getString(R.string.aboutus_title_1));
                break;
            case 1:

                mImgvwHeaderImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_home_slider_default));
                mTvHeaderTitle.setText(getString(R.string.aboutus_title_2));
                mTvContent.setText(getString(R.string.aboutus_title_2));
                break;
            case 2:

                mImgvwHeaderImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_home_slider_default));
                mTvHeaderTitle.setText(getString(R.string.aboutus_title_3));
                mTvContent.setText(getString(R.string.aboutus_title_3));
                break;
            case 3:

                mImgvwHeaderImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_home_slider_default));
                mTvHeaderTitle.setText(getString(R.string.aboutus_title_4));
                mTvContent.setText(getString(R.string.aboutus_title_4));
                break;
        }
    }

    // Call Retrofit API
    private void getAboutUsDetails() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);
        APIPageContent apiPageContent = APIRetroBuilder.getRetroBuilder(false).create(APIPageContent.class);
        Call<MdlPageContentGetRes> callMdlPageContent = apiPageContent.callGetPageContent(session.getSelectedLanguage(), mPageKey);
        callMdlPageContent.enqueue(new Callback<MdlPageContentGetRes>() {
            @Override
            public void onResponse(Call<MdlPageContentGetRes> call, Response<MdlPageContentGetRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus()) {

                            if(response.body().isShowMsg())
                                Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            else {

                                ArrayList<MdlPageContentGetResData> arrylstMdlBranchListData = response.body().getArrylstMdlPageCntntGetData();
                                mTvContent.setText(arrylstMdlBranchListData.get(0).getMetterDescription());
                            }
                        } else
                            Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "getAboutUsDetails() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlPageContentGetRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    // Create LANGUAGES popup menu
    private void createLanguagesPopupMenu(MenuItem item) {

        View menuItemView = findViewById(item.getItemId());
        PopupMenu popup = new PopupMenu(this, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.menu_languages_item, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_languages_item_english:

                session.setSelectedLanguage(LANGUAGE_SHORT_ENGLISH);
                setLanguageToAppLocale();
                return true;
            case R.id.action_languages_item_gujarati:

                session.setSelectedLanguage(LANGUAGE_SHORT_GUJARATI);
                setLanguageToAppLocale();
                return true;
        }
        return false;
    }

    // Set selected language to application LOCALE
    private void setLanguageToAppLocale() {

        session.setLanguageChanged(true);
        Locale locale = new Locale(session.getSelectedLanguage().toLowerCase());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        finish();
        startActivity(new Intent(this, ActvtyAboutUsDetails.class)
                .putExtra(PUT_EXTRA_PAGE_KEY, mPageKey)
                .putExtra(PUT_EXTRA_POSITION, mSelectedPosition));

        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_nvgtnvw_main_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                break;
            case R.id.action_language:

                createLanguagesPopupMenu(item);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
