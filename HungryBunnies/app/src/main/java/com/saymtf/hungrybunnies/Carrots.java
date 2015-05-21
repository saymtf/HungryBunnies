package com.saymtf.hungrybunnies;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by saymtfmtfmtf on 3/20/15.
 */
public class Carrots {

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawBuffer;
    private int mPositionHandler;
    private int mColorHandler;
    private int mCarrotHandler;
    private int mTextureCoordinateHandle;
    private int mTextureCoordinateDataSize = 2;
    private int mTextureUniformHandle;

    ///private final int mProgram;

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "attribute vec2 a_TexCoordinate;" +
            "varying vec2 v_TexCoordinate;" +
            "void main() {" +
            "   gl_Position = vPosition;" +
            "  v_TexCoordinate = a_TexCoordinate;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "varying vec2 v_TexCoordinate;" +
            "uniform vec4 vColor;" +
            "uniform sampler2D u_Texture;" +
            "void main() {" +
            /*"   gl_FragColor = vColor;" +*/
            "   gl_FragColor = texture2D(u_Texture, v_TexCoordinate);" +
     "}";

static float carrotCoords[] = {
        -0.725f, 0.956f, 0.0f,
        0.8f, 1.0f, 0.0f,
        0.875f, 0.956f, 0.0f,
        0.0f, 0.53f, 0.0f
         };

private short drawOrder[] = {0,1,2,0,2,3};
private float color[] = { 0.0f, 0.0f, 0.0f, 0.0f };

private final int COORDS_PER_VERTEX = 3;
private final int vertexStride = COORDS_PER_VERTEX * 4;
private final int vertexCount = carrotCoords.length / COORDS_PER_VERTEX;

/* TEXTURE */
    private FloatBuffer textureBuffer;
    private float texture[] =
            {
                    0.0f, 0.0f,     // top left     (V2)
                    0.0f, 1.0f,     // bottom left  (V1)
                    1.0f, 1.0f,     // top right    (V4)
                    1.0f, 0.0f      // bottom right (V3)
            };

    public Carrots(int mProgram) {
        ByteBuffer bb = ByteBuffer.allocateDirect(
                carrotCoords.length * 4
        );

        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(carrotCoords);
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        bb = ByteBuffer.allocateDirect( texture.length * 4 );
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);


        //mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);

    }


    public void loadGLTexture(Context context) {
        mCarrotHandler = TextureHelper.loadTexture(context, R.mipmap.carrot_01);
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
    }


    public void draw(int mProgram) {
        GLES20.glUseProgram(mProgram);

        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandler);

        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mColorHandler = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandler, 1, color, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandler);

        // Texture

        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mCarrotHandler);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, textureBuffer);

        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);



    }


}
