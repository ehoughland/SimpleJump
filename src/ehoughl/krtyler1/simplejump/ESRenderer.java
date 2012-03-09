package ehoughl.krtyler1.simplejump;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
	
public class ESRenderer implements GLSurfaceView.Renderer 
{
	int angle = 0;
	float yPosition = -0.1f;
	float gravity = 0.005f;
	PlatformObject platform = new PlatformObject();
	HeroObject hero = new HeroObject();
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
        // Set the background frame color
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        
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
        
        platform.draw(gl);
        
        yPosition = ((hero.initialJumpVelocity * hero.timeSinceLastJump()) - 
        		((0.5f * (gravity) * (hero.timeSinceLastJump() * hero.timeSinceLastJump()))))/1000;
        
        yPosition += hero.yPositionOfLastJump; //the position of the hero is dependent on the location of his last jump
        
        //if his new position is less than or equal to his old position, we know he is falling
        if(hero.yPositionBottom > yPosition)
        {
        	hero.isFalling = true;
        }
        
        //set the hero's yPosition attribute to the newly calculated position.
        hero.yPositionBottom = yPosition;
        
        //at this point we know where he should be but have not done any collision calculations or drawn anything to the screen.
        //we'll now do some calculation and determine if we should draw him where he should be according to the physics or
        //make him jump off a platform.
        
        //check for collision
        if(hero.isFalling)
        {
	        if(hero.yPositionBottom <= platform.yPositionTop) //he has collided with the platform
	        {
	        	//Log.d("hero.yPositionBottom", Float.toString(hero.yPositionBottom));
	        	//Log.d("platform.yPositionTop", Float.toString(platform.yPositionTop));
	            
	        	hero.jump();
	        	hero.yPositionOfLastJump = platform.yPositionTop;
	        	hero.yPositionBottom = platform.yPositionTop;
	        	yPosition = platform.yPositionTop;
	        }
        }
        
        //only translating and rotating the hero - since it hasn't been drawn yet
        gl.glTranslatef(0.0f, yPosition, 0.0f);
        
        //constant rotation for hero
        angle += 3;
        
        if(angle == 360)
        {
        	angle = 0;
        }
        
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        
        hero.draw(gl);
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