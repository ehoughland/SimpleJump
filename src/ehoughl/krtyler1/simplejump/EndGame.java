package ehoughl.krtyler1.simplejump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndGame extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        TextView tv = (TextView) this.findViewById(R.id.score);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        tv.setText(b.getString("gameScore"));
        
    }
    
    public void restartGame(View v)
    {
    	startActivity(new Intent(getApplicationContext(), GameActivity.class));
    	finish();
    }
    
    public void quitGame(View v)
    {
    	finish();
        System.exit(0);
    }
}
