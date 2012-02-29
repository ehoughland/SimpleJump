package ehoughl.krtyler1.simplejump;

import ehoughl.krtyler1.simplejump.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SimpleJumpActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void startGame(View view)
    {
    	startActivity(new Intent(getApplicationContext(), GameActivity.class));
    }
}