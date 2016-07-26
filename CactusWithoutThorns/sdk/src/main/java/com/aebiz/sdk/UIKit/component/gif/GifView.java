package com.aebiz.sdk.UIKit.component.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.InputStream;

public class GifView extends View
        implements GifAction {
    private GifDecoder gifDecoder = null;

    private Bitmap currentImage = null;

    private boolean isRun = true;

    private boolean pause = false;

    private int showWidth = -1;
    private int showHeight = -1;
    private Rect rect = null;

    private DrawThread drawThread = null;

    private GifImageType animationType = GifImageType.SYNC_DECODER;

    private Handler redrawHandler = new Handler() {
        public void handleMessage(Message msg) {
            GifView.this.invalidate();
        }
    };

    public GifView(Context context) {
        super(context);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setGifDecoderImage(byte[] gif) {
        if (this.gifDecoder != null) {
            this.gifDecoder.free();
            this.gifDecoder = null;
        }
        this.gifDecoder = new GifDecoder(gif, this);
        this.gifDecoder.start();
    }

    private void setGifDecoderImage(InputStream is) {
        if (this.gifDecoder != null) {
            this.gifDecoder.free();
            this.gifDecoder = null;
        }
        this.gifDecoder = new GifDecoder(is, this);
        this.gifDecoder.start();
    }

    public void setGifImage(byte[] gif) {
        setGifDecoderImage(gif);
    }

    public void setGifImage(InputStream is) {
        setGifDecoderImage(is);
    }

    public void setGifImage(int resId) {
        Resources r = getResources();
        InputStream is = r.openRawResource(resId);
        setGifDecoderImage(is);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.gifDecoder == null)
            return;
        if (this.currentImage == null) {
            this.currentImage = this.gifDecoder.getImage();
        }
        if (this.currentImage == null) {
            return;
        }
        int saveCount = canvas.getSaveCount();
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        if (this.showWidth == -1)
            canvas.drawBitmap(this.currentImage, 0.0F, 0.0F, null);
        else {
            canvas.drawBitmap(this.currentImage, null, this.rect, null);
        }
        canvas.restoreToCount(saveCount);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int pleft = getPaddingLeft();
        int pright = getPaddingRight();
        int ptop = getPaddingTop();
        int pbottom = getPaddingBottom();
        int h;
        int w;
        if (this.gifDecoder == null) {
            w = 1;
            h = 1;
        } else {
            w = this.gifDecoder.width;
            h = this.gifDecoder.height;
        }

        w += pleft + pright;
        h += ptop + pbottom;

        w = Math.max(w, getSuggestedMinimumWidth());
        h = Math.max(h, getSuggestedMinimumHeight());

        int widthSize = resolveSize(w, widthMeasureSpec);
        int heightSize = resolveSize(h, heightMeasureSpec);
        Log.e("zcftest", "onMeasure : " + widthSize + " , " + heightSize);
        setShowDimension(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize);
    }

    public void showCover() {
        if (this.gifDecoder == null)
            return;
        this.pause = true;
        this.currentImage = this.gifDecoder.getImage();
        invalidate();
    }

    public void showAnimation() {
        if (this.pause)
            this.pause = false;
    }

    public void setGifImageType(GifImageType type) {
        if (this.gifDecoder == null)
            this.animationType = type;
    }

    public void setShowDimension(int width, int height) {
        if ((width > 0) && (height > 0)) {
            this.showWidth = width;
            this.showHeight = height;
            this.rect = new Rect();
            this.rect.left = 0;
            this.rect.top = 0;
            this.rect.right = width;
            this.rect.bottom = height;
        }
    }

    public void parseOk(boolean parseStatus, int frameIndex) {
        if (parseStatus) {
            if (this.gifDecoder != null) ;
            if (animationType == GifImageType.WAIT_FINISH) {
                if (frameIndex != -1) return;
                if (this.gifDecoder.getFrameCount() > 1) {
                    DrawThread dt = new DrawThread();
                    dt.start();
                    return;
                }
                reDraw();
            } else if (animationType == GifImageType.SYNC_DECODER) {
                if (frameIndex == 1) {
                    this.currentImage = this.gifDecoder.getImage();
                    reDraw();
                    return;
                }
                if (frameIndex == -1) {
                    reDraw();
                    return;
                }
                if (this.drawThread != null) return;
                this.drawThread = new DrawThread();
                this.drawThread.start();

            } else if (animationType == GifImageType.COVER) {
                if (frameIndex == 1) {
                    this.currentImage = this.gifDecoder.getImage();
                    reDraw();
                    return;
                }
                if (frameIndex != -1) return;
                if (this.gifDecoder.getFrameCount() > 1) {
                    if (this.drawThread != null) return;
                    this.drawThread = new DrawThread();
                    this.drawThread.start();
                    return;
                }

                reDraw();

            }
           /* switch ($SWITCH_TABLE$com$ant$liao$GifView$GifImageType()[this.animationType.ordinal()]) {
                case 1:
                    if (frameIndex != -1) return;
                    if (this.gifDecoder.getFrameCount() > 1) {
                        DrawThread dt = new DrawThread(null);
                        dt.start();
                        return;
                    }
                    reDraw();

                    break;
                case 3:
                    if (frameIndex == 1) {
                        this.currentImage = this.gifDecoder.getImage();
                        reDraw();
                        return;
                    }
                    if (frameIndex != -1) return;
                    if (this.gifDecoder.getFrameCount() > 1) {
                        if (this.drawThread != null) return;
                        this.drawThread = new DrawThread(null);
                        this.drawThread.start();
                        return;
                    }

                    reDraw();

                    break;
                case 2:
                    if (frameIndex == 1) {
                        this.currentImage = this.gifDecoder.getImage();
                        reDraw();
                        return;
                    }
                    if (frameIndex == -1) {
                        reDraw();
                        return;
                    }
                    if (this.drawThread != null) return;
                    this.drawThread = new DrawThread(null);
                    this.drawThread.start();
                default:
                    return;

                Log.e("gif", "parse error");
            }*/
        }
    }

    private void reDraw() {
        if (this.redrawHandler != null) {
            Message msg = this.redrawHandler.obtainMessage();
            this.redrawHandler.sendMessage(msg);
        }
    }

    private class DrawThread extends Thread {
        private DrawThread() {
        }

        public void run() {
            if (GifView.this.gifDecoder == null) {
                return;
            }
            do
                if (!GifView.this.pause) {
                    GifFrame frame = GifView.this.gifDecoder.next();
                    GifView.this.currentImage = frame.image;
                    long sp = frame.delay;
                    if (GifView.this.redrawHandler == null) return;
                    Message msg = GifView.this.redrawHandler.obtainMessage();
                    GifView.this.redrawHandler.sendMessage(msg);
                    SystemClock.sleep(sp);
                } else {
                    SystemClock.sleep(10L);
                }
            while (GifView.this.isRun);
        }
    }

    public static enum GifImageType {
        WAIT_FINISH(0),

        SYNC_DECODER(1),

        COVER(2);

        final int nativeInt;

        private GifImageType(int i) {
            this.nativeInt = i;
        }

    }
}