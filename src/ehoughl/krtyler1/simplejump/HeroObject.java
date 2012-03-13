package ehoughl.krtyler1.simplejump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.os.SystemClock;

public class HeroObject 
{
	private FloatBuffer vertexBuffer;   // buffer holding the vertices
 
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
	    -0.1f, 0.0f, 0,
	    0.1f, 0.0f, 0,
	    0.0f,  0.2f, 0
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
    
    public HeroObject() 
    {
        // a float has 4 bytes so we allocate for each coordinate 4 bytes
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
 
        // allocates the memory from the byte buffer
        vertexBuffer = vertexByteBuffer.asFloatBuffer();
 
        // fill the vertexBuffer with the vertices
        vertexBuffer.put(vertices);
 
        // set the cursor position to the beginning of the buffer
        vertexBuffer.position(0);
    }
 
    /** The draw method for the square with the GL context */
    public void draw(GL10 gl) 
    {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        // set the colour for the hero
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
 
        // Point to our vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
 
        // Draw the vertices as triangle strip
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length / 3);
 
        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}