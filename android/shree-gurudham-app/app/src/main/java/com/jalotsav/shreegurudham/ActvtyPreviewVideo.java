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

package com.jalotsav.shreegurudham;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.jalotsav.shreegurudham.common.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jalotsav on 7/12/2018.
 */
public class ActvtyPreviewVideo extends YouTubeBaseActivity implements AppConstants, YouTubePlayer.OnInitializedListener {

    @BindView(R.id.youtubeplyrvw_actvty_prevwimage_player) YouTubePlayerView mYouTubePlayerView;

    String mYoutubeVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_preview_video);
        ButterKnife.bind(this);

        mYoutubeVideoId = getIntent().getStringExtra(PUT_EXTRA_YOUTUBE_VIDEOID);

        mYouTubePlayerView.initialize(API_KEY_YOUTUBE, this);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        if(!wasRestored)
            youTubePlayer.cueVideo(mYoutubeVideoId);
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError())
            youTubeInitializationResult.getErrorDialog(this, REQUEST_RECOVERY).show();
        else {
            String error = String.format(getString(R.string.youtube_player_error_sml), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AppConstants.REQUEST_RECOVERY) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(API_KEY_YOUTUBE, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return mYouTubePlayerView;
    }
}
