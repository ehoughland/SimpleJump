package ehoughl.krtyler1.simplejump;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.WindowManager;

public class GameActivity extends Activity 
{
	private GLSurfaceView mGLView;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
		mGLView = new ESSurfaceView(this, GameActivity.this);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
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
	
	public void endGame()
	{
		this.finish();
	}
	
	class ESSurfaceView extends GLSurfaceView implements SensorEventListener
	{
		private ESRenderer renderer;
		
		public ESSurfaceView(Context context, GameActivity game)
		{
			super(context);
			// Set the Renderer for drawing on the GLSurfaceView
			
			Bitmap platformBmp = BitmapFactory.decodeResource(getResources(), R.drawable.platform);
			Bitmap heroBmp = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
			
			renderer = new ESRenderer(platformBmp, heroBmp, game);
			setRenderer(renderer);
			
			SensorManager mSensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			HandlerThread mHandlerThread = new HandlerThread("sensorThread");
			mHandlerThread.start();
			Handler handler = new Handler(mHandlerThread.getLooper());
			mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
			                    SensorManager.SENSOR_DELAY_GAME, handler);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
			// TODO Auto-generated method stub
		}

		float smoothedValue = 0.0f;
		static final float SMOOTHING_FACTOR = 0.15f;
		
		@Override
		public void onSensorChanged(SensorEvent event) 
		{
	        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
	        {
	        	smoothedValue = smoothedValue + SMOOTHING_FACTOR * (event.values[0] - smoothedValue);
	        	
	        	queueEvent(new Runnable()
	            {
	                public void run()
	                {
	                    renderer.setTilt(smoothedValue/100f);
	                }
	            });
	        }
		}
	}
}