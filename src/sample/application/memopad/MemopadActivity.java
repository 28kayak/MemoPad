package sample.application.memopad;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//additional imports 
import android.content.SharedPreferences;
//class to save user data 
import android.text.Selection;
//Utility class for manipulating cursors and selections in CharSequences.
//a cursor is a selection where the start and end are at the same offset. 
import android.widget.EditText;
//EditText is a thin veneer over TextView that configures itself to be editable.




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
    }//onCreateOptionsMenu()


	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop();
		EditText et = (EditText) findViewById(R.id.editText1);
		SharedPreferences pref = getSharedPreferences("MemoPrefs",MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("memo", et.getText().toString());
		editor.putInt("cursor",Selection.getSelectionStart(et.getText()));//store current place of cursor.
		editor.commit();	
	}//onStop()
	
	
    
}
