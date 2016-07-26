package com.aebiz.sdk.UIKit.component.gif;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2015/2/3 0003.
 */
public class GifFrame {
    public Bitmap image;
    public int delay;
    public GifFrame nextFrame = null;

    public GifFrame(Bitmap im, int del) {
        this.image = im;
        this.delay = del;
    }
}
