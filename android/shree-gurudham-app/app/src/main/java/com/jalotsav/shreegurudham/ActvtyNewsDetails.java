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
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
public class ActvtyNewsDetails extends AppCompatActivity implements AppConstants, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = ActvtyNewsDetails.class.getSimpleName();

    UserSessionManager session;

    @BindView(R.id.cordntrlyot_actvty_news_details) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.tv_actvty_news_details_headertitle) TextView mTvHeaderTitle;
    @BindView(R.id.tv_actvty_news_details_content) TextView mTvContent;
    @BindView(R.id.prgrsbr_actvty_news_details) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    int mNewsID;
    String mNewsTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_news_details);
        ButterKnife.bind(this);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("");
        } catch (Exception e) { e.printStackTrace(); }

        session = new UserSessionManager(this);

        mNewsID = getIntent().getIntExtra(PUT_EXTRA_NEWS_ID, 0);
        mNewsTitle = getIntent().getStringExtra(PUT_EXTRA_NEWS_TITLE);

        mTvHeaderTitle.setMovementMethod(new ScrollingMovementMethod());
        mTvHeaderTitle.setText(mNewsTitle);
        mTvContent.setText(mNewsTitle);

        /*if (GeneralFunctions.isNetConnected(this))
            fetchNewsDetails();
        else Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();*/
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
        startActivity(new Intent(this, ActvtyNewsDetails.class)
                .putExtra(PUT_EXTRA_NEWS_ID, mNewsID)
                .putExtra(PUT_EXTRA_NEWS_TITLE, mNewsTitle));

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
