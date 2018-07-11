package com.jalotsav.shreegurudham.models.videos;

/**
 * Created by Jalotsav on 7/12/2018.
 */
public class MdlVideosListResData {

    private String title;
    private String thumbnailImageURL;
    private String videoURL;

    public MdlVideosListResData(String title, String thumbnailImageURL, String videoURL) {
        this.title = title;
        this.thumbnailImageURL = thumbnailImageURL;
        this.videoURL = videoURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailImageURL() {
        return thumbnailImageURL;
    }

    public void setThumbnailImageURL(String thumbnailImageURL) {
        this.thumbnailImageURL = thumbnailImageURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
