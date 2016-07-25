package com.gentech.mobile.fun4u.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.gentech.mobile.fun4u.R;
import com.gentech.mobile.fun4u.models.Content;
import com.gentech.mobile.fun4u.presenters.AnalyticsPresenter;
import com.gentech.mobile.fun4u.utils.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 16.07.16.
 */
public class AdFragment extends Fragment {
    public static final String TAG = AdFragment.class.getSimpleName();

    NativeAd nativeAd;

    @Bind(R.id.ad_unit)
    LinearLayout adView;

    @Bind(R.id.native_ad_icon)
    ImageView nativeAdIcon;

    @Bind(R.id.native_ad_title)
    TextView nativeAdTitle;

    @Bind(R.id.native_ad_body)
    TextView nativeAdBody;

    @Bind(R.id.native_ad_media)
    MediaView nativeAdMedia;

    @Bind(R.id.native_ad_social_context)
    TextView nativeAdSocialContext;

    @Bind(R.id.native_ad_call_to_action)
    Button nativeAdCallToAction;

    AdChoicesView adChoicesView;

    private String fbAdId = "";
    private boolean isAdLoaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        fbAdId = Config.FB_PLACEMENT_ID;

        showNativeAd();
    }

    public static Fragment newInstance() {
        AdFragment fragment = new AdFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAdLoaded", false);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ad, container, false);
        ButterKnife.bind(this, rootView);

        nativeAd.loadAd();

        return rootView;
    }

    private void showNativeAd() {
        Log.d(TAG, "showNativeAd fbAdId " + fbAdId);
        nativeAd = new NativeAd(getContext(), fbAdId);
        nativeAd.setAdListener(new AdListener() {

            @Override
            public void onError(Ad ad, AdError error) {
                Log.e(TAG, "onError " + error.getErrorCode());
            }

            @Override
            public void onAdLoaded(Ad ad) {

                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdBody.setText(nativeAd.getAdBody());

                NativeAd.Image adIcon = nativeAd.getAdIcon();
                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                nativeAdMedia.setNativeAd(nativeAd);

                if (adChoicesView == null) {
                    adChoicesView = new AdChoicesView(getContext(), nativeAd, true);
                    adView.addView(adChoicesView, 0);
                }

                nativeAd.registerViewForInteraction(adView);

                Log.d(TAG, "ccf putBoolean true");
                getArguments().putBoolean("isAdLoaded", true);

//                isAdLoaded = true;
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG, "onAdClicked");
                AnalyticsPresenter.getInstance().sendAnalyticsEvent(TAG,
                        AnalyticsPresenter.CATEGORY_ADVERTISEMENT, AnalyticsPresenter.ACTION_FB_NATIVE_CLICKED);

            }
        });

        AdSettings.addTestDevice(Config.FB_AD_HASHED_ID);
    }

    public boolean isAdLoaded() {
      return  getArguments().getBoolean("isAdLoaded", false);
//        return isAdLoaded;

//        return  nativeAd!=null;
    }


}
