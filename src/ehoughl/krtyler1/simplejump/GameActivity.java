package ehoughl.krtyler1.simplejump;

import ehoughl.krtyler1.simplejump.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class GameActivity extends Activity 
{
	private GLSurfaceView mGLView;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
		mGLView = new ESSurfaceView(this);
		setContentView(mGLView);
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		// The following call pauses the rendering thread.
		mGLView.onPause();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		// The following call resumes a paused rendering thread.
		mGLView.onResume();
	}
	
	class ESSurfaceView extends GLSurfaceView implements SensorEventListener
	{
		private ESRenderer renderer;
		
		public ESSurfaceView(Context context)
		{
			super(context);
			// Set the Renderer for drawing on the GLSurfaceView
			renderer = new ESRenderer();
			setRenderer(renderer);
			
			// Render the view only when there is a change
			//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}
		
		@Override
		public void onSensorChanged(SensorEvent e) 
		{
//			if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
//	        {
//				Float xSensorValue = e.values[0];
//				
//				if(xSensorValue != null) 
//				{
//					//renderer.angle += 3;
//					//requestRender();
//				}
//			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
			// TODO Auto-generated method stub	
		}
	}
}