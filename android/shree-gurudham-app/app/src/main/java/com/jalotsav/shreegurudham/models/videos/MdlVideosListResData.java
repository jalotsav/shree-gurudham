package com.jalotsav.shreegurudham.models.videos;

/**
 * Created by Jalotsav on 7/12/2018.
 */
public class MdlVideosListResData {

    private String title;
    private String youtubeVideoID;

    public MdlVideosListResData(String title, String youtubeVideoID) {
        this.title = title;
        this.youtubeVideoID = youtubeVideoID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYoutubeVideoID() {
        return youtubeVideoID;
    }

    public void setYoutubeVideoID(String youtubeVideoID) {
        this.youtubeVideoID = youtubeVideoID;
    }
}
