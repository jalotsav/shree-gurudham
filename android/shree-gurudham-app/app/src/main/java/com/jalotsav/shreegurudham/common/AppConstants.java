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

package com.jalotsav.shreegurudham.common;

/**
 * Created by Jalotsav on 7/6/2018.
 */
public interface AppConstants {

    // Build Type
    String BUILD_TYPE_DEBUG = "debug";

    // Web-Service Keys
    String KEY_STATUS_SML = "status";
    String KEY_SHOW_MSG_SML = "show_msg";
    String KEY_MESSAGE_SML = "message";
    String KEY_DATA_SML = "data";

    String KEY_SELECTED_LANGUAGE = "selectedLanguage";

    // Language short code
    String LANGUAGE_SHORT_ENGLISH = "en";
    String LANGUAGE_SHORT_GUJARATI = "gu";

    // PutExtra Keys
    String PUT_EXTRA_COME_FROM = "comeFrom";
    String PUT_EXTRA_NVGTNVW_POSTN = "nvgtnvwPosition";

    // NavigationView Drawer MenuItem position check for direct open that fragment
    int NVGTNVW_HOME = 21;
    int NVGTNVW_ABOUTUS = 22;
    int NVGTNVW_NEWS = 23;
    int NVGTNVW_CONTACTUS = 24;
    int NVGTNVW_IMAGES = 25;
    int NVGTNVW_VIDEOS = 26;
    int NVGTNVW_AUDIOS = 27;
}
