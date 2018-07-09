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
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.nvgtnvwmain.FrgmntHome;

import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 7/6/2018.
 */
public class ActvtyMain extends AppCompatActivity implements AppConstants, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_drwrlyot_appbar_main) Toolbar mToolbar;
    @BindView(R.id.drwrlyot_nvgtndrwr_main) DrawerLayout mDrwrlyot;
    @BindView(R.id.navgtnvw_nvgtndrwr_main) NavigationView mNavgtnVw;

    MenuItem mMenuItemHome, mMenuItemAboutUs, mMenuItemImages, mMenuItemVideos, mMenuItemAudios, mMenuItemNews, mMenuItemContactUs;
    UserSessionManager session;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        session = new UserSessionManager(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrwrlyot, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrwrlyot.addDrawerListener(toggle);
        toggle.syncState();

        mNavgtnVw.setNavigationItemSelectedListener(this);

        // load Projects fragment by default
        int navgtnPosition = getIntent().getIntExtra(PUT_EXTRA_NVGTNVW_POSTN, NVGTNVW_HOME);
        onNavigationItemSelected(getNavgtnvwPositionMenuItem(navgtnPosition));
        setToolbarTitle(getToolbarTitle(navgtnPosition));
    }

    // Get MenuItem from BottomNavigationView Position, which is get from getIntent()
    private MenuItem getNavgtnvwPositionMenuItem(int navgtnPosition) {

        switch (navgtnPosition) {
            case NVGTNVW_HOME:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_home);
            case NVGTNVW_NEWS:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_news);
            case NVGTNVW_CONTACTUS:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_contactus);
            default:

                return mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_home);
        }
    }

    // Get MenuItem from BottomNavigationView Position, which is get from getIntent()
    private String getToolbarTitle(int navgtnPosition) {

        switch (navgtnPosition) {
            case NVGTNVW_HOME:

                return getString(R.string.home_sml);
            case NVGTNVW_NEWS:

                return getString(R.string.news_sml);
            case NVGTNVW_CONTACTUS:

                return getString(R.string.contactus_sml);
            default:

                return getString(R.string.home_sml);
        }
    }

    // Set Toolbar title
    private void setToolbarTitle(String toolbarTitle) {

        try {
            getSupportActionBar().setTitle(toolbarTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment;
        if(mBundle == null) {

            mBundle = new Bundle();
            mBundle.putInt(PUT_EXTRA_COME_FROM, 0);
        }

        switch (item.getItemId()) {
            case R.id.action_nvgtndrwr_main_home:

                fragment = new FrgmntHome();
                mToolbar.setTitle(getString(R.string.home_sml));
                loadFragment(fragment, item);
                return true;
            case R.id.action_nvgtndrwr_main_aboutus:

                fragment = new FrgmntHome();
                mToolbar.setTitle(getString(R.string.aboutus_sml));
                loadFragment(fragment, item);
                return true;
            case R.id.action_nvgtndrwr_main_images:

                fragment = new FrgmntHome();
                mToolbar.setTitle(getString(R.string.images_sml));
                loadFragment(fragment, item);
                return true;
            case R.id.action_nvgtndrwr_main_videos:

                fragment = new FrgmntHome();
                mToolbar.setTitle(getString(R.string.videos_sml));
                loadFragment(fragment, item);
                return true;
            case R.id.action_nvgtndrwr_main_audios:

                fragment = new FrgmntHome();
                mToolbar.setTitle(getString(R.string.audios_sml));
                loadFragment(fragment, item);
                return true;
            case R.id.action_nvgtndrwr_main_news:

                fragment = new FrgmntHome();
                mToolbar.setTitle(getString(R.string.news_sml));
                loadFragment(fragment, item);
                return true;
            case R.id.action_nvgtndrwr_main_contactus:

                fragment = new FrgmntHome();
                mToolbar.setTitle(getString(R.string.contactus_sml));
                loadFragment(fragment, item);
                return true;
        }
        return false;
    }

    // Load Fragment of selected MenuItem
    private void loadFragment(Fragment fragment, MenuItem item) {

        if(fragment != null) {

            mDrwrlyot.closeDrawer(GravityCompat.START);

            getCurrentCheckedMenuItem().setChecked(false);
            item.setChecked(true);

            fragment.setArguments(mBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.framlyot_drwrlyot_contnt_container, fragment)
                    .commit();
        }
    }

    // Get Current checked MenuItem of NavigationView
    public MenuItem getCurrentCheckedMenuItem() {

        mMenuItemHome = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_home);
        mMenuItemAboutUs = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_aboutus);
        mMenuItemImages = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_images);
        mMenuItemVideos = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_videos);
        mMenuItemAudios = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_audios);
        mMenuItemNews = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_news);
        mMenuItemContactUs = mNavgtnVw.getMenu().findItem(R.id.action_nvgtndrwr_main_contactus);

        MenuItem currntSelctdMenuItem;
        if (mMenuItemHome.isChecked())
            currntSelctdMenuItem = mMenuItemHome;
        else if (mMenuItemAboutUs.isChecked())
            currntSelctdMenuItem = mMenuItemAboutUs;
        else if (mMenuItemImages.isChecked())
            currntSelctdMenuItem = mMenuItemImages;
        else if (mMenuItemVideos.isChecked())
            currntSelctdMenuItem = mMenuItemVideos;
        else if (mMenuItemAudios.isChecked())
            currntSelctdMenuItem = mMenuItemAudios;
        else if (mMenuItemNews.isChecked())
            currntSelctdMenuItem = mMenuItemNews;
        else if (mMenuItemContactUs.isChecked())
            currntSelctdMenuItem = mMenuItemContactUs;
        else
            currntSelctdMenuItem = mMenuItemHome;

        return currntSelctdMenuItem;
    }

    @Override
    public void onBackPressed() {

        if (mDrwrlyot.isDrawerOpen(GravityCompat.START))
            mDrwrlyot.closeDrawer(GravityCompat.START);
        else if (getCurrentCheckedMenuItem() != mMenuItemHome)
            onNavigationItemSelected(mNavgtnVw.getMenu().getItem(0));
        else
            super.onBackPressed();
    }
}
