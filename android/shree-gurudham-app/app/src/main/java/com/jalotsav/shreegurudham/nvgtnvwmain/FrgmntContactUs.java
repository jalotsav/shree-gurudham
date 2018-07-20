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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.common.AppConstants;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.models.inquiry.MdlInquiryRes;
import com.jalotsav.shreegurudham.retrofitapi.APIInquiry;
import com.jalotsav.shreegurudham.utils.ValidationUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jalotsav on 7/19/2018.
 */
public class FrgmntContactUs extends Fragment implements AppConstants {

    private static final String TAG = "FrgmntContactUs";

    @BindView(R.id.cordntrlyot_frgmnt_contactus) CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.mapvw_frgmnt_contactus_location) MapView mMapvwLocation;

    @BindView(R.id.txtinputlyot_frgmnt_contactus_fullname) TextInputLayout mTxtinptlyotFirstName;
    @BindView(R.id.txtinputlyot_frgmnt_contactus_email) TextInputLayout mTxtinptlyotEmail;
    @BindView(R.id.txtinputlyot_frgmnt_contactus_mobileno) TextInputLayout mTxtinptlyotMobile;

    @BindView(R.id.txtinptet_frgmnt_contactus_fullname) TextInputEditText mTxtinptEtFirstName;
    @BindView(R.id.txtinptet_frgmnt_contactus_email) TextInputEditText mTxtinptEtEmail;
    @BindView(R.id.txtinptet_frgmnt_contactus_mobileno) TextInputEditText mTxtinptEtMobile;
    @BindView(R.id.txtinptet_frgmnt_contactus_descrptn) TextInputEditText mTxtinptEtDescrptn;

    @BindView(R.id.prgrsbr_frgmnt_contactus) ProgressBar mPrgrsbrMain;

    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;
    @BindString(R.string.sevatrust_name_sml) String mMapMarkerTitle;
    @BindString(R.string.entr_fullname_sml) String mEntrFirstName;
    @BindString(R.string.entr_email_sml) String mEntrEmail;
    @BindString(R.string.entr_mobileno_sml) String mEntrMobile;

    String mFullnameVal = "", mEmailVal = "", mMobileVal = "", mDescrptnVal = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_contactus, container, false);
        ButterKnife.bind(this, rootView);

        try {
            // To avoid automatically appear android keyboard when activity start
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapvwLocation.onCreate(savedInstanceState);
        mMapvwLocation.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                Log.i(TAG, "onMapReady");
                if(!TextUtils.isEmpty(MAP_LATITUDE_CONTACTUS) && !TextUtils.isEmpty(MAP_LONGITUDE_CONTACTUS)) {

                    try {
                        LatLng mPosition = new LatLng(Double.parseDouble(MAP_LATITUDE_CONTACTUS), Double.parseDouble(MAP_LONGITUDE_CONTACTUS));
                        googleMap.addMarker(new MarkerOptions().position(mPosition).title(mMapMarkerTitle));
                        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngZoom(mPosition, 13);
                        googleMap.animateCamera(mCameraUpdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onMapReady: TryCatch" + e.getMessage());
                    }
                }
            }
        });

        return rootView;
    }

    @OnClick({R.id.appcmptbtn_frgmnt_contactus_send})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.appcmptbtn_frgmnt_contactus_send:

                if(mPrgrsbrMain.getVisibility() != View.VISIBLE) {
                    if (GeneralFunctions.isNetConnected(getActivity()))
                        checkAllValidation();
                    else
                        Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    // Check all validation of fields and call API
    private void checkAllValidation() {

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotFirstName, mTxtinptEtFirstName, mEntrFirstName)) // FirstName
            return;

        if (!ValidationUtils.validateEmpty(getActivity(), mTxtinptlyotEmail, mTxtinptEtEmail, mEntrEmail)) // Email
            return;

        if (!ValidationUtils.validateEmailFormat(getActivity(), mTxtinptlyotEmail, mTxtinptEtEmail)) // Email Format
            return;

        if (!ValidationUtils.validateMobile(getActivity(), mTxtinptlyotMobile, mTxtinptEtMobile)) // Mobile
            return;

        writeInquiry();
    }

    // call API for Send Inquiry Details
    private void writeInquiry() {

        mPrgrsbrMain.setVisibility(View.VISIBLE);

        mFullnameVal = mTxtinptEtFirstName.getText().toString().trim();
        mEmailVal = mTxtinptEtEmail.getText().toString().trim();
        mMobileVal = mTxtinptEtMobile.getText().toString().trim();
        mDescrptnVal = mTxtinptEtDescrptn.getText().toString().trim();

        APIInquiry apiInquiry = getRetroBuilder(true).create(APIInquiry.class);
        Call<MdlInquiryRes> callMdlInquiry = apiInquiry.callInquiry(VALUE_INQUIRY, mFullnameVal, mEmailVal, mMobileVal, mDescrptnVal);
        callMdlInquiry.enqueue(new Callback<MdlInquiryRes>() {
            @Override
            public void onResponse(Call<MdlInquiryRes> call, Response<MdlInquiryRes> response) {

                mPrgrsbrMain.setVisibility(View.GONE);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus())
                            clearInquiryUI();

                        Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "writeInquiry() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlInquiryRes> call, Throwable t) {

                mPrgrsbrMain.setVisibility(View.GONE);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    // Retrofit Builder with ROOT URL API and other configs
    public Retrofit getRetroBuilder(boolean enableConnectTimeout) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(loggingInterceptor); // print OkHTTP log
        if (enableConnectTimeout) {
            clientBuilder.connectTimeout(5, TimeUnit.MINUTES);
            clientBuilder.readTimeout(5, TimeUnit.MINUTES);
        }

        return new Retrofit.Builder()
                .baseUrl(AppConstants.ROOT_URL_API)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Clear/Blank EditText field of Inquiry form
    private void clearInquiryUI() {

        mTxtinptEtFirstName.setText("");
        mTxtinptEtEmail.setText("");
        mTxtinptEtMobile.setText("");
        mTxtinptEtDescrptn.setText("");
    }

    @Override
    public void onResume() {
        mMapvwLocation.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapvwLocation.onDestroy();
    }
}
