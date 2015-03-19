package com.saymtf.hungrybunnies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Thane on 3/18/2015.
 */
public class CarrotBar {

    /* Rectangle Verticies + Color */
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;


    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "attribute vec2 a_TexCoordinate;" +
            "varying vec2 v_TexCoordinate;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "  v_TexCoordinate = a_TexCoordinate;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "varying vec2 v_TexCoordinate;" +
            "uniform vec4 vColor;" +
            "uniform sampler2D u_Texture;" +
            "void main() {" +
            "  gl_FragColor = texture2D(u_Texture, v_TexCoordinate);" +
            "}";


    //numbert of coor per vertix in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -1.0f,  -0.65f, 0.0f,   // top left
            -1.0f, -1.0f, 0.0f,   // bottom left
            1.0f, -1.0f, 0.0f,   // bottom right
            1.0f,  -0.65f, 0.0f }; // top right

    private short drawOrder[] = {0,1,2,0,2,3}; // order to draw vertices
    float color[] = { 0.63671875f, 0.0f, 0.22265625f, 1.0f};

    private final int mProgram;

    private int mPositionHandle;
    private int mColorHandle;
    private int mDirtTextureHandle;
    private int mTextureUniformHandle;
    private int mTextureCoordinateHandle;
    private final int mTextureCoordinateDataSize = 2;

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;


    /*Texture*/
    private FloatBuffer textureBuffer; // buffer holding the texture coord.
    private float texture[] = {
            // Mapping coordinates for the vertices
            0.0f, 0.0f,     // top left     (V2)
            0.0f, 1.0f,     // bottom left  (V1)
            1.0f, 0.75f,     // top right    (V4)
            0.0f, 0.0f      // bottom right (V3)
    };

    private int[] textures = new int[1];

    public CarrotBar() {

        //init vertex byte buffer for shape coord.
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coord values * 4 bytes per float)
                squareCoords.length * 4);

        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // init byte buffer for the draw lisst
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                //# of coord values * 2 byte per short
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // Texture Buffer
        bb = ByteBuffer.allocateDirect(texture.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);


        //Apply shaders to program
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);
    }


    public void loadGLTexture(Context context) {

        // Load the Texture
        mDirtTextureHandle = TextureHelper.loadTexture(context, R.drawable.dirt_layer_image);
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

    }

    public void draw() {
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(mPositionHandle);

        // APPLY TEXTURE

        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mDirtTextureHandle);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, textureBuffer);

        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

    }
}
