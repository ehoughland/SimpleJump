package ehoughl.krtyler1.simplejump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;
import android.util.Log;
	
public class ESRenderer implements GLSurfaceView.Renderer 
{
	private FloatBuffer triangle;
	int angle = 0;
	float yPosition = 0f;
	float initialJumpVelocity = 3.5f;
	float initialJumpPosition = 0f;
	float gravity = 0.005f;
	PlatformObject platform = new PlatformObject();
	HeroObject hero = new HeroObject();
	
	long timeOfLastJump = 0;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
        // Set the background frame color
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        
        // initialize the triangle vertex array
        //initShapes();
        
        // Enable use of vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }
    
    public void onDrawFrame(GL10 gl) 
    {
    	// Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f); 
        
        if(timeOfLastJump == 0)
        {
        	timeOfLastJump = SystemClock.uptimeMillis();
        }
        
        float yPositionPrevious = yPosition;
        float yPositionChange = ((initialJumpVelocity * timeSinceLastJump()) - ((0.5f * (gravity) * (timeSinceLastJump() * timeSinceLastJump()))));
        yPosition = yPositionChange/1000f + hero.yPositionBottom;
        
        if(yPosition < 0f)
        {
        	resetTimeSinceLastJump();
        }
        
        angle += 3;
        
        if(angle == 360)
        {
        	angle = 0;
        }
        
        platform.draw(gl);
        
        gl.glTranslatef(0.0f, yPosition, 0.0f);
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        
        // Draw the hero
        hero.draw(gl);
        
        
        
        //check for collision if yPositionChange is negative.  if it is, reset time
        if(yPosition < yPositionPrevious)
        {
        	Log.d("yPosition", Float.toString(yPosition));
            Log.d("hero.yPositionBottom", Float.toString(hero.yPositionBottom));
            Log.d("platform.yPositionTop", Float.toString(platform.yPositionTop));
            
        	if(yPosition + hero.yPositionBottom < platform.yPositionTop)
        	{
        		resetTimeSinceLastJump();
        		hero.yPositionBottom = platform.yPositionTop;
        	}
        }
        
        //gl.glColor4f(0.63671875f, 0.76953125f, 0.22265625f, 0.0f);
        //gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangle);
        //gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
    }
    
    private long timeSinceLastJump()
    {
    	return SystemClock.uptimeMillis() - timeOfLastJump;
    }
    
    private void resetTimeSinceLastJump()
    {
    	timeOfLastJump = 0;
    }
    
    public void onSurfaceChanged(GL10 gl, int width, int height) 
    {
        gl.glViewport(0, 0, width, height);
        
        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
    } 
	
	private void initShapes()
	{
	    float triangleCoords[] = 
	    {
            // X, Y, Z
            -0.1f, -0.1f, 0,
             0.1f, -0.1f, 0,
             0.0f,  0.1f, 0
        };
	    
        // initialize vertex Buffer for triangle  
        ByteBuffer vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                triangleCoords.length * 4); 
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        triangle = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
        triangle.put(triangleCoords);    // add the coordinates to the FloatBuffer
        triangle.position(0);            // set the buffer to read the first coordinate
    }
}