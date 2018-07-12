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

package com.jalotsav.shreegurudham.retrofitapi;

import com.jalotsav.shreegurudham.common.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jalotsav on 7/12/2018.
 */
public class APIRetroBuilder {

    private static Retrofit OBJ_RETROFIT = null;

    public static Retrofit getRetroBuilder(boolean enableConnectTimeout) {

        if (OBJ_RETROFIT == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(loggingInterceptor); // print OkHTTP log
            if (enableConnectTimeout) {
                clientBuilder.connectTimeout(5, TimeUnit.MINUTES);
                clientBuilder.readTimeout(5, TimeUnit.MINUTES);
            }

            OBJ_RETROFIT = new Retrofit.Builder()
                    .baseUrl(AppConstants.ROOT_URL_API)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return OBJ_RETROFIT;
    }
}
