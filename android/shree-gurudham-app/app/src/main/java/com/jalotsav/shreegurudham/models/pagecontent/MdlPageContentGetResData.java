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

package com.jalotsav.shreegurudham.models.pagecontent;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.shreegurudham.common.AppConstants;

/**
 * Created by Jalotsav on 7/12/2018.
 */
public class MdlPageContentGetResData implements AppConstants {

    @SerializedName(KEY_PAGE_TITLE_CAPS)
    private String pageTitle;
    @SerializedName(KEY_MATTER_DESC_CAPS)
    private String metterDescription;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getMetterDescription() {
        return metterDescription;
    }

    public void setMetterDescription(String metterDescription) {
        this.metterDescription = metterDescription;
    }
}
