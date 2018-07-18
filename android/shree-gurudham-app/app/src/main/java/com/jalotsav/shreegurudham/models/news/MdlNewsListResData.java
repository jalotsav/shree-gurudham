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

package com.jalotsav.shreegurudham.models.news;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.shreegurudham.common.AppConstants;

/**
 * Created by Jalotsav on 7/17/2018.
 */
public class MdlNewsListResData implements AppConstants {

    @SerializedName(KEY_NEWS_ID_CAPS)
    private int newsID;
    @SerializedName(KEY_NEWS_TITLE_CAPS)
    private String newsTitle;
    @SerializedName(KEY_NEWS_DESC_CAPS)
    private String newsDesc;

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }
}
