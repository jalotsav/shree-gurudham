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

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/17/2018.
 */
public class MdlNewsListRes implements AppConstants {

    @SerializedName(KEY_STATUS_SML)
    private boolean status;
    @SerializedName(KEY_SHOW_MSG_SML)
    private boolean showMsg;
    @SerializedName(KEY_MESSAGE_SML)
    private String message;
    @SerializedName(KEY_DATA_SML)
    private ArrayList<MdlNewsListResData> arrylstMdlNewsListData;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isShowMsg() {
        return showMsg;
    }

    public void setShowMsg(boolean showMsg) {
        this.showMsg = showMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<MdlNewsListResData> getArrylstMdlNewsListData() {
        return arrylstMdlNewsListData;
    }

    public void setArrylstMdlNewsListData(ArrayList<MdlNewsListResData> arrylstMdlNewsListData) {
        this.arrylstMdlNewsListData = arrylstMdlNewsListData;
    }
}
