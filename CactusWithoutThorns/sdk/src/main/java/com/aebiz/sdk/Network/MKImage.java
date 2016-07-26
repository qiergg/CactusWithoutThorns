package com.aebiz.sdk.Network;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.aebiz.sdk.Network.volley.VolleyError;
import com.aebiz.sdk.Network.volley.toolbox.BitmapLruCache;
import com.aebiz.sdk.Network.volley.toolbox.ImageLoader;

/**
 * Created by duanyytop on 15/4/7.
 */
public class MKImage {

    private static MKImage mInstance = null;
    private ImageLoader mImageLoader;
    private BitmapLruCache imageCache;
    private Bitmap bitmap;

    public interface ImageResultListener{
        public void getBitmap(Bitmap bitmap);
    }

    public static MKImage getInstance() {
        if (mInstance == null) {
            mInstance = new MKImage();
        }
        return mInstance;
    }

    public void init(Context context) {
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 4;
        imageCache = new BitmapLruCache(cacheSize);
        mImageLoader = new ImageLoader(MKNetwork.getInstance().getRequestQueue(), imageCache);
    }

    public interface onErrorListener {
        public void onError(VolleyError error);
    }

    public void getImage(final String requestUrl, final ImageResultListener imageResultListener ){
        mImageLoader.get(requestUrl, new ImageLoader.ImageListener() {
            public void onErrorResponse(VolleyError error) {
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    if (requestUrl != null && !requestUrl.equals(response.getRequestUrl())) {
                        return;
                    }
                    bitmap = response.getBitmap();
                    imageResultListener.getBitmap(bitmap);
                }
            }
        }, 0, 0);
    }

    public void getImage(String requestUrl, final ImageView imageView) {
        this.getImage(requestUrl, imageView, null);
    }

    public void getImage(String requestUrl, final ImageView imageView, final onErrorListener errorLis) {
        imageView.setTag(requestUrl);
        mImageLoader.get(requestUrl, new ImageLoader.ImageListener() {
            public void onErrorResponse(VolleyError error) {
                if (error != null && errorLis != null)
                    errorLis.onError(error);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    String url = (String) imageView.getTag();
                    if (url != null && !url.equals(response.getRequestUrl())) {
                        return;
                    }
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        }, 0, 0);
    }


}
