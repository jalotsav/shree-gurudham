package com.jalotsav.shreegurudham.nvgtnvwmain;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalotsav.shreegurudham.ActvtyMain;
import com.jalotsav.shreegurudham.R;
import com.jalotsav.shreegurudham.adapter.RcyclrNewsAdapter;
import com.jalotsav.shreegurudham.common.GeneralFunctions;
import com.jalotsav.shreegurudham.common.RecyclerViewEmptySupport;
import com.jalotsav.shreegurudham.common.UserSessionManager;
import com.jalotsav.shreegurudham.models.news.MdlNewsListRes;
import com.jalotsav.shreegurudham.models.news.MdlNewsListResData;
import com.jalotsav.shreegurudham.retrofitapi.APINews;
import com.jalotsav.shreegurudham.retrofitapi.APIRetroBuilder;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jalotsav on 7/18/2018.
 */
public class FrgmntNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FrgmntNews";

    @BindView(R.id.cordntrlyot_frgmnt_news_list)CoordinatorLayout mCrdntrlyot;
    @BindView(R.id.swiperfrshlyot_frgmnt_news_list) SwipeRefreshLayout mSwiperfrshlyot;
    @BindView(R.id.lnrlyot_recyclremptyvw_appearhere) LinearLayout mLnrlyotAppearHere;
    @BindView(R.id.tv_recyclremptyvw_appearhere) TextView mTvAppearHere;
    @BindView(R.id.rcyclrvw_frgmnt_news_list) RecyclerViewEmptySupport mRecyclerView;

    @BindString(R.string.news_appear_here) String mVideosAppearHere;
    @BindString(R.string.no_intrnt_cnctn) String mNoInternetConnMsg;
    @BindString(R.string.server_problem_sml) String mServerPrblmMsg;
    @BindString(R.string.internal_problem_sml) String mInternalPrblmMsg;

    UserSessionManager session;
    RecyclerView.LayoutManager mLayoutManager;
    RcyclrNewsAdapter mAdapter;
    ArrayList<MdlNewsListResData> mArrylstMdlNews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lo_frgmnt_news_list, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        session = new UserSessionManager(getActivity());

        initSwipeRefreshLayout();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mLnrlyotAppearHere);

        mTvAppearHere.setText(mVideosAppearHere);

        mArrylstMdlNews = new ArrayList<>();
        mAdapter = new RcyclrNewsAdapter(getActivity(), mArrylstMdlNews);
        mRecyclerView.setAdapter(mAdapter);

        callOnRefresh();

        return rootView;
    }

    // Initialization of SwipeRefreshLayout
    private void initSwipeRefreshLayout() {

        mSwiperfrshlyot.setOnRefreshListener(this);
        mSwiperfrshlyot.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryGreen),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryRed),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryBlue),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryOrange));
    }

    // call onRefresh method without swipe gesture
    private void callOnRefresh() {

        mSwiperfrshlyot.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {

        if(GeneralFunctions.isNetConnected(getActivity()))
            fetchNewsData();
        else {
            mSwiperfrshlyot.setRefreshing(false);
            Snackbar.make(mCrdntrlyot, mNoInternetConnMsg, Snackbar.LENGTH_LONG).show();
        }
    }

    // call API for Get News List
    private void fetchNewsData() {

        APINews apiGetNews = APIRetroBuilder.getRetroBuilder(true).create(APINews.class);
        Call<MdlNewsListRes> callMdlNewsList = apiGetNews.callGetNews(session.getSelectedLanguage());
        callMdlNewsList.enqueue(new Callback<MdlNewsListRes>() {
            @Override
            public void onResponse(Call<MdlNewsListRes> call, Response<MdlNewsListRes> response) {

                mSwiperfrshlyot.setRefreshing(false);

                if(response.isSuccessful()) {

                    try {
                        if(response.body().isStatus()) {

                            if(response.body().isShowMsg())
                                Snackbar.make(mCrdntrlyot, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            else {

                                if(isAdded()) { // to check fragment is attached

                                    mArrylstMdlNews = new ArrayList<>();
                                    mArrylstMdlNews.addAll(response.body().getArrylstMdlNewsListData());
                                    mAdapter = new RcyclrNewsAdapter(getActivity(), mArrylstMdlNews);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        } else
                            Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e(TAG, "fetchNewsData() - onResponse: " + e.getMessage());
                        Snackbar.make(mCrdntrlyot, mInternalPrblmMsg, Snackbar.LENGTH_LONG).show();
                    }
                } else
                    Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MdlNewsListRes> call, Throwable t) {

                mSwiperfrshlyot.setRefreshing(false);
                Snackbar.make(mCrdntrlyot, mServerPrblmMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        try {

            UserSessionManager session = new UserSessionManager(getActivity());
            if (session.isLanguageChanged()) {

                session.setLanguageChanged(false);
                ((ActvtyMain) getActivity()).setLanguageToAppLocale();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
