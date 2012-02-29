package ehoughl.krtyler1.simplejump;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	private GameLoopThread gameLoopThread;
	
	public GameView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		gameLoopThread = new GameLoopThread(this, context);
		SurfaceHolder holder = getHolder();
        holder.addCallback(this);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		gameLoopThread.setRunning(true);
		gameLoopThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		// TODO Auto-generated method stub
	}
}
