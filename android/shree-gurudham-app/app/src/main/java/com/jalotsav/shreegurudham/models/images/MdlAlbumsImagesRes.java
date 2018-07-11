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

package com.jalotsav.shreegurudham.models.images;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.shreegurudham.common.AppConstants;

import java.util.ArrayList;

/**
 * Created by Jalotsav on 7/11/2018.
 */
public class MdlAlbumsImagesRes implements AppConstants {

    @SerializedName(KEY_STATUS_SML)
    private boolean success;
    @SerializedName(KEY_SHOW_MSG_SML)
    private boolean showMsg;
    @SerializedName(KEY_MESSAGE_SML)
    private String message;
    @SerializedName(KEY_DATA_SML)
    private ArrayList<MdlAlbumsImagesResData> arrylstMdlAlbumsImagesData;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public ArrayList<MdlAlbumsImagesResData> getArrylstMdlAlbumsImagesData() {
        return arrylstMdlAlbumsImagesData;
    }

    public void setArrylstMdlAlbumsImagesData(ArrayList<MdlAlbumsImagesResData> arrylstMdlAlbumsImagesData) {
        this.arrylstMdlAlbumsImagesData = arrylstMdlAlbumsImagesData;
    }
}
