package ehoughl.krtyler1.simplejump;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;

public class GameLoopThread extends Thread implements SensorEventListener 
{
	private GameView view;
	private boolean running = false;
	static final int FPS = 30;
	private volatile ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private Bitmap heroBmp;
	HeroGameObject heroObj;
	
	public GameLoopThread(GameView view, Context c)
	{
		this.view = view;
		heroBmp = BitmapFactory.decodeResource(view.getResources(), R.drawable.compass_small);
		
		SensorManager mSensorMgr = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		HandlerThread mHandlerThread = new HandlerThread("sensorThread");
		mHandlerThread.start();
		Handler handler = new Handler(mHandlerThread.getLooper());
		mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		                    SensorManager.SENSOR_DELAY_FASTEST, handler);
	}
	
	public void setRunning(boolean run)
	{
		running = run;
	}
	
	@Override
	public void run()
	{
		addHero();
		
		while(running)
		{
			Canvas c = null;
			
			try
			{
				c = view.getHolder().lockCanvas(null);
                synchronized (view.getHolder()) 
                {
                    draw(c);
                }
			}
			finally
			{
				if (c != null)
				{
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
		}
	}
	
	private void draw(Canvas canvas) 
	{
        // Draw the background color. Operations on the Canvas accumulate
        // so this is like clearing the screen. In a real game you can put in a background image of course
        canvas.drawColor(Color.BLACK);
        
        synchronized (gameObjects) 
        {
            for (GameObject object : gameObjects) 
            {
            	if(object.getType() == GameObjectType.Hero)
            	{
            		//temporary code to keep him from dying!
            		int startY = (view.getHeight() - heroBmp.getHeight()) - 50;
            		
            		if(object.getY() > startY)
            		{
            			object.setY(startY);
            			((HeroGameObject)object).jump();
            		}
            		////////////////////////////////////////////////////////////
            		
            		((HeroGameObject)object).move();
            	}
            	
                object.draw(canvas, object.getX(), object.getY());
            }
        }
        
        canvas.restore();
    }
	
	public void addHero()
	{
		heroObj = new HeroGameObject(heroBmp);
		
		int startX = (view.getWidth() - heroBmp.getWidth())/2;
		int startY = (view.getHeight() - heroBmp.getHeight()) - 50;
		
		heroObj.setX(startX);
		heroObj.setY(startY);
		
		synchronized(this)
		{
			gameObjects.add(heroObj);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{	
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) 
	{
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
        {
			Integer xSensorValue = (int)sensorEvent.values[0];
			
//			if(xSensorValue > 10)
//			{
//				xSensorValue = 10;
//			}
//			else if(xSensorValue < 10)
//			{
//				xSensorValue = -10;
//			}
			
			if(xSensorValue != null && heroObj != null) 
			{
				heroObj.setX(-xSensorValue*2); 
			}
        }
	}
}
