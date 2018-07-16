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

/**
 * Created by Jalotsav on 7/10/2018.
 */
public class MdlAlbumsImagesResData implements AppConstants {

    @SerializedName(KEY_ALBUM_ID_CAPS)
    private int albumID;
    @SerializedName(KEY_ALBUM_TITLE_CAPS)
    private String albumTitle;
    @SerializedName(KEY_IMG_PATH_CAPS)
    private String imgPath;

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
