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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jalotsav.shreegurudham.ActvtyAboutUsDetails;
import com.jalotsav.shreegurudham.ActvtyMain;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.UserSessionManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jalotsav on 7/13/2018.
 */
public class FrgmntAboutUs extends Fragment implements AppConstants {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_aboutus, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick({R.id.rltvlyot_frgmnt_aboutus_title1, R.id.rltvlyot_frgmnt_aboutus_title2,
            R.id.rltvlyot_frgmnt_aboutus_title3, R.id.rltvlyot_frgmnt_aboutus_title4})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.rltvlyot_frgmnt_aboutus_title1:

                startActivityAboutUsDetails(VALUE_ABOUT_DEKAWADA, 0);
                break;
            case R.id.rltvlyot_frgmnt_aboutus_title2:

                startActivityAboutUsDetails(VALUE_ABOUT_SHREEGURUJI, 1);
                break;
            case R.id.rltvlyot_frgmnt_aboutus_title3:

                startActivityAboutUsDetails(VALUE_ABOUT_SHREEBAPU, 2);
                break;
            case R.id.rltvlyot_frgmnt_aboutus_title4:

                startActivityAboutUsDetails(VALUE_ABOUT_SHIVPOOJA, 3);
                break;
        }
    }

    // start AboutUs details activity by given PAGE KEY
    private void startActivityAboutUsDetails(String pageKey, int position) {

        startActivity(new Intent(getActivity(), ActvtyAboutUsDetails.class)
                .putExtra(PUT_EXTRA_PAGE_KEY, pageKey)
                .putExtra(PUT_EXTRA_POSITION, position));
    }

    @Override
    public void onResume() {
        super.onResume();

        try {

            UserSessionManager session = new UserSessionManager(getActivity());
            if (session.isLanguageChanged()) {

                session.setLanguageChanged(false);
                ((ActvtyMain) getActivity()).setLanguageToAppLocale();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
