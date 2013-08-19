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
//imports to save memo-data on data base
import java.text.DateFormat;
import java.util.Date;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;




public class MemopadActivity extends Activity 
{
	

    private static final EditText EditText = null;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText et = (EditText)findVieById(R.id.editText1);
        SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);
        et.setText(pref.getString("memo", ""));
        et.setSelection(pref.getInt("cursor", 0));
        
        
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
	
	void saveMemo()
	{
		EditText et = (EditText)this.findViewById(R.id.editText1);
		String title;
		String memo = et.getText().toString();//store memo_data as a string,memo.
		
		if(memo.trim().length() > 0)
		{//get length of memo. save data if length of memo is more then 0
			if(memo.indexOf("\n") == -1)
			{
				title = memo.substring(0, Math.min(memo.length(), 20));
				//set title using memo-data upto 20 words. 
				//to avoid error when memo-data is less than 20 letters,
				//compare memo.length() and 20 and use smaller value. 
			}
			else
			{
				title = memo.substring(0, Math.min(memo.indexOf("\n"), 20));
				//use memo-data as a title before "\n(next line)" if next line comes before 20 letters
			}
			String ts = DateFormat.getDateTimeInstance().format(new Date());
			//get data and store as a string
			MemoDBHelper memos = new MemoDBHelper(this);
			//create an instance of MemoDBHelper
			SQLiteDatabase db = memos.getWritableDatabase();
			//open database with writable condition
			ContentValues values = new ContentValues();
			values.put("title", title + "\n" + ts); 
			//put title on database
			values.put("memo", memo);
			//put memo-data on database
			db.insertOrThrow("memoDB", null, values);
			memos.close();//exit from MemoDBHelper
		}
	}
	
	
    
}
