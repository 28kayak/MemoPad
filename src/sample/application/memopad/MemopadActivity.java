package sample.application.memopad;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;




public class MemopadActivity extends Activity 
{

    private static final EditText EditText = null;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop();
		
	}
    
}
