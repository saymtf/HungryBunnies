package com.saymtf.hungrybunnies;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by saymtfmtfmtf on 3/17/15.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private float[] mMVPMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];



    public void onSurfaceCreated(GL10 unused, EGLConfig config) {


    }

    public void onDrawFrame(GL10 unused) {
        //GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

}
