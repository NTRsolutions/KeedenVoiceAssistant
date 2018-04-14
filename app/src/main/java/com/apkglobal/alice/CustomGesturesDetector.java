package com.apkglobal.alice;

import android.nfc.Tag;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import static com.apkglobal.alice.R.id.listview;


/**
 * Created by Anshul Aggarwal on 23-01-2018.
 */

public class CustomGesturesDetector implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{
    String TAG="-------------------->";

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.e(TAG,"onSIngleTapConfiremd");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.e(TAG,"onDoubleTapConfiremd");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.e(TAG,"onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.e(TAG,"onDOwn");
        return true;}

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.e(TAG,"onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.e(TAG,"onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.e(TAG,"onFling");
        return true;
    }
}
