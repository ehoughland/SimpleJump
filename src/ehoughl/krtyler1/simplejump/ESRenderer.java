package ehoughl.krtyler1.simplejump;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;
	
public class ESRenderer implements GLSurfaceView.Renderer 
{
	int angle = 0;
	float yPosition = 0f;
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
        
        if(hero.timeOfLastJump == 0)
        {
        	hero.timeOfLastJump = SystemClock.uptimeMillis();
        }
        
        float yPositionChange = ((hero.initialJumpVelocity * hero.timeSinceLastJump()) - ((0.5f * (gravity) * (hero.timeSinceLastJump() * hero.timeSinceLastJump()))));
        yPosition = yPositionChange/1000f + hero.yPositionBottom;
        
        if(yPosition < 0f)
        {
        	hero.resetTimeSinceLastJump();
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
}