package ehoughl.krtyler1.simplejump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.os.SystemClock;

public class HeroObject 
{
	private float xPosition = 0.0f;
	private float yPosition = 0.0f;
	private long timeOfLastJump = 0;
	private float yPositionOfLastJump = 0.0f;
	private boolean isFalling = false;
	private float initialJumpVelocity = 3.5f;
	private float maxYPosition = 0f;//1.2249975f; // hero starting at 0 + jump length
	
	public float getInitialJumpVelocity()
	{
		return initialJumpVelocity;
	}
	
	public void setIsFalling(boolean isFalling)
	{
		this.isFalling = isFalling;
	}
	
	public boolean getIsFalling()
	{
		return this.isFalling;
	}
	
	public float getYPositionOfLastJump()
	{
		return this.yPositionOfLastJump;
	}
	
	public void setYPositionOfLastJump(float yPositionOfLastJump)
	{
		this.yPositionOfLastJump = yPositionOfLastJump;
	}
	
	public float getXPosition()
	{
		return -xPosition;
	}
	
	public void setXPosition(float xPosition)
	{
		this.xPosition = xPosition;
	}
	
	public float getYPosition()
	{
		return this.yPosition;
	}
	
	public void setYPosition(float yPosition)
	{
		this.yPosition = yPosition;
	}
	
	public float getMaxYPosition()
	{
		return this.maxYPosition;
	}
	
	public void setMaxYPosition(float maxYPosition)
	{
		this.maxYPosition = maxYPosition;
	}
	
    private float vertices[] = 
    {
	    -0.125f, 0.0f, 0,
	    0.125f, 0.0f, 0,
	    -0.125f,  0.25f, 0,
	    0.125f,  0.25f, 0
    };
 
    public long getTimeSinceLastJump()
    {
    	if(timeOfLastJump == 0)
    	{
    		jump(0.0f); //we're starting the game so make him jump.
    		return 0;
    	}
    	else
    	{
    		return SystemClock.uptimeMillis() - timeOfLastJump;
    	}
    }
    
    public void jump(float jumpPosition)
    {
    	timeOfLastJump = SystemClock.uptimeMillis();
    	yPositionOfLastJump = jumpPosition;
    }
    
    public HeroObject(Bitmap heroBmp) 
    {
    	loadBitmap(heroBmp);
    	
    	// Mapping coordinates for the vertices
		float textureCoordinates[] = 
		{ 
				0.0f, 1.0f, //
				1.0f, 1.0f, //
				0.0f, 0.0f, //
				1.0f, 0.0f, //
		};

		short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };

		setIndices(indices);
		setVertices(vertices);
		setTextureCoordinates(textureCoordinates);
    }
 
	// Our vertex buffer.
	private FloatBuffer mVerticesBuffer = null;

	// Our index buffer.
	private ShortBuffer mIndicesBuffer = null;

	// Our UV texture buffer.
	private FloatBuffer mTextureBuffer; // New variable.

	// Our texture id.
	private int mTextureId = -1; // New variable.

	// The bitmap we want to load as a texture.
	private Bitmap mBitmap; // New variable.

	// Indicates if we need to load the texture.
	private boolean mShouldLoadTexture = false; // New variable.

	// The number of indices.
	private int mNumOfIndices = -1;

	// Flat Color
	private final float[] mRGBA = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };

	// Smooth Colors
	private FloatBuffer mColorBuffer = null;

	/**
	 * Render the mesh.
	 * 
	 * @param gl
	 *            the OpenGL context to render to.
	 */
	public void draw(GL10 gl) {
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW);
		
		gl.glEnable (GL10.GL_BLEND);
		gl.glBlendFunc (GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		// Set flat color
		gl.glColor4f(mRGBA[0], mRGBA[1], mRGBA[2], mRGBA[3]);
		// Smooth color
		
		if (mColorBuffer != null) 
		{
			// Enable the color array buffer to be used during rendering.
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		}

		// New part...
		if (mShouldLoadTexture) 
		{
			loadGLTexture(gl);
			mShouldLoadTexture = false;
		}
		
		if (mTextureId != -1 && mTextureBuffer != null) 
		{
			gl.glEnable(GL10.GL_TEXTURE_2D);
			// Enable the texture state
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

			// Point to our buffers
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		}
		// ... end new part.

		// Point out the where the color buffer is.
		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,
				GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);

		// Disable the vertices buffer.
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	/**
	 * Set the vertices.
	 * 
	 * @param vertices
	 */
	protected void setVertices(float[] vertices) {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVerticesBuffer = vbb.asFloatBuffer();
		mVerticesBuffer.put(vertices);
		mVerticesBuffer.position(0);
	}

	/**
	 * Set the indices.
	 * 
	 * @param indices
	 */
	protected void setIndices(short[] indices) {
		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndicesBuffer = ibb.asShortBuffer();
		mIndicesBuffer.put(indices);
		mIndicesBuffer.position(0);
		mNumOfIndices = indices.length;
	}

	/**
	 * Set the texture coordinates.
	 * 
	 * @param textureCoords
	 */
	protected void setTextureCoordinates(float[] textureCoords) { // New
																	// function.
		// float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer byteBuf = ByteBuffer
				.allocateDirect(textureCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mTextureBuffer = byteBuf.asFloatBuffer();
		mTextureBuffer.put(textureCoords);
		mTextureBuffer.position(0);
	}

	/**
	 * Set one flat color on the mesh.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	protected void setColor(float red, float green, float blue, float alpha) {
		mRGBA[0] = red;
		mRGBA[1] = green;
		mRGBA[2] = blue;
		mRGBA[3] = alpha;
	}

	/**
	 * Set the colors
	 * 
	 * @param colors
	 */
	protected void setColors(float[] colors) {
		// float has 4 bytes.
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		mColorBuffer = cbb.asFloatBuffer();
		mColorBuffer.put(colors);
		mColorBuffer.position(0);
	}

	/**
	 * Set the bitmap to load into a texture.
	 * 
	 * @param bitmap
	 */
	public void loadBitmap(Bitmap bitmap) 
	{ // New function.
		this.mBitmap = bitmap;
		mShouldLoadTexture = true;
	}

	/**
	 * Loads the texture.
	 * 
	 * @param gl
	 */
	private void loadGLTexture(GL10 gl) { // New function
		// Generate one texture pointer...
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		mTextureId = textures[0];

		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

		// Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		// Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);

		// Use the Android GLUtils to specify a two-dimensional texture image
		// from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
	}
}