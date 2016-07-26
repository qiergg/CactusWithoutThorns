package com.weifeng_cactus.cactuswithoutthorns.Model;

import android.content.Context;
import android.widget.ImageView;

import com.weifeng_cactus.cactuswithoutthorns.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maiya on 16/7/26.
 */
public class SplashData {

    List<ImageView> mData = new ArrayList();
    int[] data ={R.mipmap.qiche1,R.mipmap.qiche2,R.mipmap.qiche3,R.mipmap.qiche4};
    private final Context mContext;

   public SplashData(Context context){
        mContext = context;
    }

    public  List<ImageView> getSplashPic() {
        ImageView imgs = new ImageView(mContext);
        imgs.setScaleType(ImageView.ScaleType.FIT_XY);
        for(int i=0;i<data.length;i++){
            imgs.setBackgroundResource(data[i]);
            mData.add(imgs);

        }

        return mData;
    }
}
