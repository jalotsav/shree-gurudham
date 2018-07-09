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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jalotsav.shreegurudham.ActvtyMain;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.UserSessionManager;

import java.util.Locale;

/**
 * Created by Jalotsav on 7/6/2018.
 */
public class FrgmntHome extends Fragment implements AppConstants, PopupMenu.OnMenuItemClickListener {

    UserSessionManager session;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Set selected language to application LOCALE
    private void setLanguageToAppLocale(String currentLanguage) {

        Locale locale = new Locale(currentLanguage.toLowerCase());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        getActivity().finish();
        startActivity(new Intent(getActivity(), ActvtyMain.class)
                .putExtra(PUT_EXTRA_NVGTNVW_POSTN, NVGTNVW_HOME));
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_language, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_language:

                createLanguagesPopupMenu(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Create LANGUAGES popup menu
    private void createLanguagesPopupMenu(MenuItem item) {

        View menuItemView = getActivity().findViewById(item.getItemId());
        PopupMenu popup = new PopupMenu(getActivity(), menuItemView);
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
                setLanguageToAppLocale(session.getSelectedLanguage());
                return true;
            case R.id.action_languages_item_gujarati:

                session.setSelectedLanguage(LANGUAGE_SHORT_GUJARATI);
                setLanguageToAppLocale(session.getSelectedLanguage());
                return true;
        }
        return false;
    }
}
