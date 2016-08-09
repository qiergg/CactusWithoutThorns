package com.weifeng_cactus.cactuswithoutthorns.fragment;


import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.weifeng_cactus.cactuswithoutthorns.R;

/**
 * Created by maiya on 16/8/8.
 */
public class CrimeCameraFragment extends Fragment {

    private SurfaceView mSf;
    private Camera mCamera;

    @TargetApi(9)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_camera, container, false);
       Button takePic= (Button) v.findViewById(R.id.crime_camera_takePictureButton);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mSf = (SurfaceView) v.findViewById(R.id.crime_camera_surfaceView);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
            mCamera = Camera.open(0);
        }else {
           mCamera =Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCamera!=null){
            mCamera.release();
            mCamera=null;
        }
    }
}
