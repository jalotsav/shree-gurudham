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

    // URLs - Root, API
    String ROOT_URL_API = "http://www.shreegurudham.com/apis/ApiService.svc/";
    String ROOT_URL_YOUTUBE = "https://www.youtube.com/watch?v=";
    String ROOT_URL_YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/";

    // Web-Service Keys
    String KEY_STATUS_SML = "status";
    String KEY_SHOW_MSG_SML = "show_msg";
    String KEY_MESSAGE_SML = "message";
    String KEY_DATA_SML = "data";
    String KEY_PAGE_TITLE_CAPS = "PAGETITLE";
    String KEY_MATTER_DESC_CAPS = "MATTERDESC";
    String KEY_ALBUM_ID_CAPS = "ALBUMID";
    String KEY_ALBUM_TITLE_CAPS = "ALBUMTITLE";
    String KEY_IMG_PATH_CAPS = "IMGPATH";
    String KEY_IMAGE_TITLE_CAPS = "IMAGETITLE";
    String KEY_IMAGE_PATH_CAPS = "IMAGEPATH";
    String KEY_VIDEO_PATH_CAPS = "VIDEOPATH";
    String KEY_VIDEO_TITLE_CAPS = "VIDEOTITLE";
    String KEY_YOUTUBE_ID_CAPS = "YOUTUBEID";
    String KEY_AUDIO_PATH_CAPS = "AUDIO_PATH";
    String KEY_AUDIO_TITLE_CAPS = "AUDIO_TITLE";
    String KEY_NEWS_ID_CAPS = "NEWSID";
    String KEY_NEWS_TITLE_CAPS = "NEWS_TITLE";
    String KEY_NEWS_DESC_CAPS = "NEWSDESC";
    String KEY_PAGE_TITLE_SML = "page_title";
    String KEY_MATTER_DESC_SML = "matter_desc";
    String KEY_SLIDER_IMAGES_SML = "slider_images";

    // Web-Service Values
    String VALUE_ABOUT_DEKAWADA = "about_dekawada";
    String VALUE_ABOUT_SHREEGURUJI = "about_shreeguruji";
    String VALUE_ABOUT_SHREEBAPU = "about_shreebapu";
    String VALUE_ABOUT_SHIVPOOJA = "about_shivpooja";

    // User session manager Keys
    String KEY_SELECTED_LANGUAGE = "selectedLanguage";
    String KEY_LANGUAGE_CHANGED = "isLanguageChanged";

    // Language short code
    String LANGUAGE_SHORT_ENGLISH = "en";
    String LANGUAGE_SHORT_GUJARATI = "gu";

    // PutExtra Keys
    String PUT_EXTRA_COME_FROM = "comeFrom";
    String PUT_EXTRA_NVGTNVW_POSTN = "nvgtnvwPosition";
    String PUT_EXTRA_PAGE_KEY = "page_key";
    String PUT_EXTRA_POSITION = "position";
    String PUT_EXTRA_ALBUM_ID = "albumID";
    String PUT_EXTRA_ALBUM_NAME = "albumName";
    String PUT_EXTRA_IMAGE_URL = "imageUrl";
    String PUT_EXTRA_YOUTUBE_VIDEOID = "youtubeVideoID";
    String PUT_EXTRA_AUDIO_URL = "audioUrl";
    String PUT_EXTRA_NEWS_TITLE = "newsTitle";
    String PUT_EXTRA_NEWS_DESC = "newsDesc";

    // NavigationView Drawer MenuItem position check for direct open that fragment
    int NVGTNVW_HOME = 21;
    int NVGTNVW_ABOUTUS = 22;
    int NVGTNVW_NEWS = 23;
    int NVGTNVW_CONTACTUS = 24;
    int NVGTNVW_IMAGES = 25;
    int NVGTNVW_VIDEOS = 26;
    int NVGTNVW_AUDIOS = 27;

    // Request Keys
    int REQUEST_RECOVERY = 101;

    // API Key
    String API_KEY_YOUTUBE = "AIzaSyDsW7ppu24mRbYAzRdZpcWKVYJnqkols-g";

    // Miscellaneous
    String CONTENT_TYPE_JSON = "Content-Type: application/json";
    String YOUTUBE_THUMBNAIL_IMAGE_NAME = "/0.jpg";
}
