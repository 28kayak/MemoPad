package sample.application.memopad;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
//additional imports 
import android.content.SharedPreferences;
import android.text.Editable;
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
//import to show menu
import android.view.MenuInflater;
//automatic saving system
import android.text.TextWatcher;



public class MemopadActivity extends Activity 
{
	

    private static final EditText EditText = null;
    //automatic saving system
    boolean memoChanged = false;//store boolean if memo is modified or not
    String fn;
    String encode = "SHIFT-JIS";   		
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText et = (EditText)findVieById(R.id.editText1);
        SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);
        memoChanged = pref.getBoolean("memoChanged", false);
        et.setText(pref.getString("memo", ""));
        et.setSelection(pref.getInt("cursor", 0));
        
        fn = pref.getString("fn", "");
        encode = pref.getString("encode", "SHIFT-JIS");
        
        Intent i = getIntent();
        Uri uri =i.getData();
        String tempFn;
        if(uri != null)
        {
        	tempFn = i.getData().getPath();
        }
        if(tempFn.length() > 0)
        {
        	if(memoChanged)
        	{
        		saveMemo();
        	}
        	fn = tempFn;
        	et.setText(readFile());
        	memoChanged = false;
        }
        TextWatcher tw = new TextWatcher(){
        	@Override
        	public void afterTextChanged(Editable arg0){}
        	@Override
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        	@Override
        	public void onTextChanged(CharSequence s, int start, int before,int count)
        	{
        		memoChanged  = true;
        	}
        };
        et.addTextChangedListener(tw);
    }
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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
		editor.putBoolean("memoChanged", memoChanged);
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			EditText et = (EditText) findViewById(R.id.editText1);
			switch(requestCode)
			{
				case 0:
					et.setText(data.getStringExtra("text"));
					memoChanged = false;
					break;
			}
		}
		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		EditText et = (EditText) findVieById(R.id.editText1);
		switch (item.getItemId())
		{
			case R.id.menu_save:
				saveMemo();
				break;
			case R.id.menu_open:
				if(memoChanged)
				{
					saveMemo();
				}
				Intent i = new Intent(this, MemoList.class);
				startActivityForResult(i,0);//activate MemoList by using Intent
				break;
			case R.id.menu_new: 
				if(memoChanged)
				{
					saveMemo();
				}
				et.setText("");
				break;
		}//switch
		return super.onOptionsItemSelected(item);
	}
	
	
    
}
