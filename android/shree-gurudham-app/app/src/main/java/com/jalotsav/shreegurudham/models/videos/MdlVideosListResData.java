package com.jalotsav.shreegurudham.models.videos;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.shreegurudham.common.AppConstants;

/**
 * Created by Jalotsav on 7/12/2018.
 */
public class MdlVideosListResData implements AppConstants {

    @SerializedName(KEY_VIDEO_TITLE_CAPS)
    private String videoTitle;
    @SerializedName(KEY_VIDEO_PATH_CAPS)
    private String videoPath;
    @SerializedName(KEY_YOUTUBE_ID_CAPS)
    private String youtubeID;

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getYoutubeID() {
        return youtubeID;
    }

    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }
}
