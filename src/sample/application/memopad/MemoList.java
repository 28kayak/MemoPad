package sample.application.memopad;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
//added imports 
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;

public class MemoList extends ListActivity
{
	static final String[] cols= {"title", "memo",android.provider.BaseColumns._ID};
	//cols are data of filed to be gotten by searching database
	MemoDBHelper memos;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memolist);
		showMemos(getMemos());
	}

}
