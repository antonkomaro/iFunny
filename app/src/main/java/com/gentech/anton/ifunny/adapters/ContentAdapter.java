package com.gentech.anton.ifunny.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gentech.anton.ifunny.R;
import com.gentech.anton.ifunny.models.ContentModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anton on 12.07.16.
 */
public class ContentAdapter extends PagerAdapter {
    public static final String TAG = ContentAdapter.class.getSimpleName();

    Context context;
    private List<ContentModel> data = new ArrayList<>();

    @Bind(R.id.iv_content_item)
    SimpleDraweeView ivContent;

    @Bind(R.id.tv_content_item)
    TextView tvContent;

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

        loadContent(position, container, rootView);
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

    }

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
