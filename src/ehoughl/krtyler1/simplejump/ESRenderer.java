package ehoughl.krtyler1.simplejump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
	
public class ESRenderer implements GLSurfaceView.Renderer 
{
	private FloatBuffer triangle;
	private int angle;
	float yPosition = 0.0f;
	float ySpeed = 0.07f;
	float timeElapsed = 0.0f;
	float gravity = 0.0003f;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		// Set the background frame color to black
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// initialize the triangle vertex array
		initShapes();
		// Enable use of vertex arrays
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		SystemClock.sleep ( 20 ); //delay for 1 second
		angle += 3; //increment angle by 6 degrees
		//rotate triangle about y-axis
		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
		
		timeElapsed += 0.002;
		
		ySpeed = (float) (ySpeed - gravity/Math.sqrt(timeElapsed));
		
		yPosition = yPosition + ySpeed;
		
		gl.glTranslatef(0, yPosition, 0);
		
		if(yPosition < -1) 
		{
			ySpeed = 0.07f;
			timeElapsed = 0.0f;
		}
		
		// Draw the triangle using blue color
		gl.glColor4f(0.0f, 0.0f, 1.0f, 0.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangle);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) 
	{
		gl.glViewport(0, 0, width, height);
	}
	
	private void initShapes()
	{
		float vertices[] = 
		{
			// (x, y, z) of triangle
			-0.1f, -1.0f, 0,
			0.1f, -1.0f, 0,
			0.0f, -0.9f, 0
		};
		
		// initialize vertex Buffer for triangle
		// argument=(# of coordinate values * 4 bytes per float)
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		// use the device hardwareÕs native byte order
		vbb.order(ByteOrder.nativeOrder());
		// create a floating point buffer from the ByteBuffer
		triangle = vbb.asFloatBuffer();
		// add the coordinates to the FloatBuffer
		triangle.put(vertices);
		// set the buffer to read the first vertex coordinates
		triangle.position(0);
	}
}