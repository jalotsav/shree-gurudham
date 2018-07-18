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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.UserSessionManager;

import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 7/18/2018.
 */
public class ActvtyNewsDetails extends AppCompatActivity implements AppConstants {

    UserSessionManager session;

    @BindView(R.id.toolbar_actvty_news_details_) Toolbar mToolbar;
    @BindView(R.id.tv_actvty_news_details_headertitle) TextView mTvHeaderTitle;
    @BindView(R.id.tv_actvty_news_details_content) TextView mTvContent;
    @BindView(R.id.lnrlyot_actvty_news_details_content) LinearLayout mLnrlyotContent;

    String mNewsTitle, mNewsDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_news_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(true);

        session = new UserSessionManager(this);

        mNewsTitle = getIntent().getStringExtra(PUT_EXTRA_NEWS_TITLE);
        mNewsDesc = getIntent().getStringExtra(PUT_EXTRA_NEWS_DESC);

        // To prevent null value in initCollapsingToolbar()
        mTvHeaderTitle.setText(getString(R.string.app_name));

        initCollapsingToolbar();
        setNewsDetailsUI();
    }

    // Initial CollapsingToolbar
    private void initCollapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.colpstoolbar_actvty_news_details_);
        collapsingToolbar.setTitle(" ");
//        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        AppBarLayout appBarLayout = findViewById(R.id.appbarlyot_actvty_news_details);
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

    // Set selected News details on UI from Intent extra
    private void setNewsDetailsUI() {

        mTvHeaderTitle.setText(mNewsTitle);
        if(TextUtils.isEmpty(mNewsDesc))
            mLnrlyotContent.setVisibility(View.INVISIBLE);
        else {
            mLnrlyotContent.setVisibility(View.VISIBLE);
            mTvContent.setText(mNewsDesc);
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
