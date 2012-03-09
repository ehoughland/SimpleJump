package ehoughl.krtyler1.simplejump;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
	
public class ESRenderer implements GLSurfaceView.Renderer 
{
	private float vertices[] = 
	    {
	            -0.2f,  -0.1f,  0.0f,        // V1 - bottom left
	            -0.2f,  0.1f,  0.0f,        // V2 - top left
	             0.2f,  -0.1f,  0.0f,        // V3 - bottom right
	             0.2f,  0.1f,  0.0f         // V4 - top right
	    };
	private float verticesTwo[] = 
	    {
	            -0.2f,  .6f,  0.0f,        // V1 - bottom left
	            -0.2f,  .8f,  0.0f,        // V2 - top left
	             0.2f,  .6f,  0.0f,        // V3 - bottom right
	             0.2f,  .8f,  0.0f         // V4 - top right
	    };
	int angle = 0;
	float gravity = 0.005f;
	float testYPosition = (verticesTwo[1] + verticesTwo[4]) / 2;
	PlatformObject platform = new PlatformObject(vertices);
	PlatformObject platformTwo = new PlatformObject(verticesTwo);
	HeroObject hero = new HeroObject();
	int drawCount = 0;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
        // Set the background frame color
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        
        // Enable use of vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }
    
    public void onDrawFrame(GL10 gl) 
    {
    	float heroNewlyCalculatedyPosition = 0f;
    	
    	// Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f); 
        
        platform.draw(gl);
        platformTwo.draw(gl);
        
        heroNewlyCalculatedyPosition = ((hero.initialJumpVelocity * hero.timeSinceLastJump()) - 
        		((0.5f * (gravity) * (hero.timeSinceLastJump() * hero.timeSinceLastJump()))))/1000;
        
        heroNewlyCalculatedyPosition += hero.getYPositionOfLastJump(); //the position of the hero is dependent on the location of his last jump
        
        //if his new position is less than his old position, we know he is falling
        if(hero.getYPosition() > heroNewlyCalculatedyPosition)
        {
        	hero.setIsFalling(true);
        }
        else
        {
        	hero.setIsFalling(false);
        }
        
        //at this point we know where he should be but have not done any collision calculations or drawn anything to the screen.
        //we'll now do some calculation and determine if we should draw him where he should be according to the physics or
        //make him jump off a platform.
        
        //check for collision
        if(hero.getIsFalling() && heroNewlyCalculatedyPosition <= testYPosition) //he has collided with the platform
        {
        	hero.setYPositionOfLastJump(testYPosition);
        	hero.setYPosition(testYPosition);
        	hero.jump();
        	gl.glTranslatef(0.0f, testYPosition, 0.0f);
        }
        else
        {
        	gl.glTranslatef(0.0f, heroNewlyCalculatedyPosition, 0.0f);
        	hero.setYPosition(heroNewlyCalculatedyPosition);
        }
        
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