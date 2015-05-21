package com.saymtf.hungrybunnies;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by saymtfmtfmtf on 4/18/15.
 */
public class Bunny {
    private FloatBuffer vertexBuffer;

    private ShortBuffer drawListBuffer;



    private String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}";

    private String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            0.0f,  0.0f, 0.0f,   // top left
            0.0f, 0.0f, 0.0f,   // bottom left
            0.0f, 0.0f, 0.0f,   // bottom right
            0.0f,  0.0f, 0.0f }; // top right

    private short drawOrder[] = {0,1,2,0,2,3}; // order to draw vertices
    float color[] = {0.3f, 0.4f, 0.5f, 1.0f};

    private int mPositionHandler;
    private int mColorHandle;
    //private int mDirtTextureHandle;
    private int mTextureUniformHandle;
    private int mTextureCoordinateHandle;
    private final int mTextureCoordinateDataSize = 2;

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    /*Texture*/
    /*private FloatBuffer textureBuffer; // buffer holding the texture coord.
    private float texture[] = {
            // Mapping coordinates for the vertices
            0.0f, 0.0f,     // top left     (V2)
            0.0f, 1.0f,     // bottom left  (V1)
            1.0f, 1.0f,     // top right    (V4)
            1.0f, 0.0f      // bottom right (V3)
    };
    */
    public Bunny(int mProgram) {

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
        /*
        // Texture Buffer
        bb = ByteBuffer.allocateDirect(texture.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);
        */


        //Apply shaders to program
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);
    }


    public void loadGLTexture(Context context) {

        // Load the Texture
        //mDirtTextureHandle = TextureHelper.loadTexture(context, R.drawable.dirt_layer_image);
        //GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

    }

    public void draw(int mProgram) {
        GLES20.glUseProgram(mProgram);

        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandler);

        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0); // for texture use

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(mPositionHandler);

        // APPLY TEXTURE
        /*
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mDirtTextureHandle);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, textureBuffer);

        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
        */
    }
}
