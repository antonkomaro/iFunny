package com.gentech.anton.ifunny.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.models.ContentModel;
import com.gentech.anton.ifunny.ui.activities.MainActivity;
import com.gentech.anton.ifunny.ui.fragments.VideoFragment;
import com.gentech.anton.ifunny.utils.Constants;
import com.gentech.anton.ifunny.utils.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 12.07.16.
 */
public class ContentAdapter extends PagerAdapter {
    public static final String TAG = ContentAdapter.class.getSimpleName();

    private Context context;
    private View rootView;

    private List<ContentModel> data = new ArrayList<>();

    @Bind(R.id.iv_content)
    SimpleDraweeView ivContent;

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.fl_video_container)
    FrameLayout flVideoContainer;

    public ContentAdapter(Context context, List<ContentModel> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.item_content, container, false);
        ButterKnife.bind(this, rootView);

//        ivContent = (SimpleDraweeView) rootView.findViewById(R.id.iv_content);
//        tvContent = (TextView) rootView.findViewById(R.id.tv_content);
//        youTubePlayerFragment = (YouTubePlayerFragment) ((MainActivity)context).getFragmentManager()
//                .findFragmentById(R.id.video_fragment);

        loadContent(position, container, rootView);

        this.rootView = rootView;
        return rootView;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    private void loadContent(int position, ViewGroup container, View rootView) {
        ContentModel content = data.get(position);
        if (content == null) {
            Log.e(TAG, context.getString(R.string.no_content_found));
            return;
        }

        String url = content.getUrl();
        switch (content.getContentType()) {
            case IMAGE:
                loadImage(url);
                break;
            case GIF:
                loadGif(url);
                break;
            case VIDEO:
                loadVideo(url);
                break;
            default:
                Log.e(TAG, context
                        .getString(R.string.unknown_content_type));
                return;
        }

        tvContent.setText(data.get(position).getTitle());
        container.addView(rootView);
    }

    private void loadVideo(String url) {
//        String videoId = Utils.getYoutubeVideoIdFromUrl(url);

        VideoFragment videoFragment = VideoFragment.newInstance("ztRydkK--l8");
        videoFragment.init();
        ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_video_container, videoFragment).commit();


//        createYoutubeFragment(url, context);
    }

//    private void createYoutubeFragment(String url, Context context) {
//        YouTubePlayerSupportFragment youTubePlayerFragment = new YouTubePlayerSupportFragment();
//        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY,  new YouTubePlayer.OnInitializedListener(){
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                String videoId = Utils.getYoutubeVideoIdFromUrl(url);
//                youTubePlayer.loadVideo(videoId);
//                youTubePlayer.play();
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                if (youTubeInitializationResult.toString().equals(context.getString(R.string.service_missing))) {
//                    Utils.showSnackMessage(rootView, context.getString(R.string.player_error));
//                }
//
//                Log.e(TAG, context.getString(R.string.player_error));
//            }
//        });
//
//        ((MainActivity) context).getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fl_video_container, youTubePlayerFragment)
//                .commit();
//    }

    private void loadGif(String url) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .build();
        ivContent.setController(controller);
    }

    private void loadImage(String url) {
        ivContent.setImageURI(Uri.parse(url));
    }

}
