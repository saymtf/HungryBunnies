package com.saymtf.hungrybunnies;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class GameActivity extends Activity {

    private MyGLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    class MyGLSurfaceView extends GLSurfaceView {

        private final MyGLRenderer mRenderer;

        public MyGLSurfaceView(Context context) {
            super(context);

            setEGLContextClientVersion(2);
            setEGLConfigChooser(8, 8, 8, 8, 16, 0);

            //getHolder().setFormat(PixelFormat.TRANSLUCENT);
            setBackgroundResource(R.mipmap.game_background);
            //setZOrderOnTop(true);

            mRenderer = new MyGLRenderer();

            setRenderer(mRenderer);
            //setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
    }
}
