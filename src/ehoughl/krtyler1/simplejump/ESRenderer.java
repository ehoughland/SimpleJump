package ehoughl.krtyler1.simplejump;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
	
public class ESRenderer implements GLSurfaceView.Renderer
{	
	Level level = new Level();
	HeroObject hero = new HeroObject();
	ArrayList<PlatformObject> platforms = level.getPlatforms();
	private float tilt = 0.0f;
	private float angle = 0.0f;
	
	public void setTilt(float tilt)
	{
		this.tilt += tilt;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.7f, 0.5f);  
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }
    
    public void onDrawFrame(GL10 gl) 
    {
    	// Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        
        //draw platforms
    	for(PlatformObject p : platforms)
    	{
    		p.draw(gl);
    	}
        
        float newHeroYPosition = ((hero.getInitialJumpVelocity() * hero.getTimeSinceLastJump()) 
        		- ((0.5f * (level.getGravity()) * (hero.getTimeSinceLastJump() * hero.getTimeSinceLastJump()))))/1000.0f 
        		+ hero.getYPositionOfLastJump();
       
        //if his new position is less than his old position, we know he is falling
        if(hero.getYPosition() > newHeroYPosition)
        {
        	hero.setIsFalling(true);
        }
        else
        {
        	hero.setIsFalling(false);
        }
        
        //if he goes over the left edge, put him on the right edge, and vice-versa.
        if(tilt > 1.1f)
        {
        	tilt = -1.0f;
        }
        else if(tilt < -1.1f)
        {
        	tilt = 1.0f;
        }
        
        hero.setXPosition(tilt);
        
        //at this point we know where he should be but have not done any collision calculations or drawn him to the screen.
        //we'll now do some calculation and determine if we should draw him where he should be according to the physics or
        //make him jump off a platform.
        
        boolean jumped = false;
        
        if(hero.getIsFalling())
        {
	        for(PlatformObject p : platforms)
	        {
	        	if(newHeroYPosition <= p.getYPosition() + 0.04f && newHeroYPosition >= p.getYPosition() - 0.04f)
	        	{
	        		if(hero.getXPosition() <= p.getXPosition() + 0.3f && hero.getXPosition() >= p.getXPosition() - 0.3f)
	        		{
	        			gl.glPushMatrix();
	        	        gl.glTranslatef(hero.getXPosition(), p.getYPosition(), 0.0f);
	        	        hero.jump(p.getYPosition());
	        	        angle = 0;
	        	        hero.setYPosition(p.getYPosition());
	        	        hero.draw(gl);
	        	        gl.glPopMatrix();
	        	        jumped = true;
	        		}
	        	}
	        }
        }
        
        if(!jumped)
        {
	        gl.glPushMatrix();
	        gl.glTranslatef(hero.getXPosition(), newHeroYPosition, 0.0f);
	        
	        if(hero.getTimeSinceLastJump() > 942.45f)
	        {
	        	angle += 3;
	        }
	        else
	        {
	        	angle += (float)Math.cos(hero.getTimeSinceLastJump()/600.0f) * 20.0f;
	        }
	        
	        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
	        hero.setYPosition(newHeroYPosition);
	        hero.draw(gl);
	        gl.glPopMatrix();
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) 
    {
    	// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
    }
}