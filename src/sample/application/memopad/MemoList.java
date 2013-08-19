package sample.application.memopad;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
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
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		memos = new MemoDBHelper(this);
		SQLiteDatabase db = memos.getReadableDatabase();
		Cursor cursor = db.query("memoDB", cols, "_ID="+String.valueOf(id), null, null, null, null);
		startManagingCursor(cursor);
		int idx = cursor.getColumnIndex("memo");
		cursor.moveToFirst();
		Intent intent = new Intent();
		intent.putExtra("text", cursor.getString(idx));
		setResult(RESULT_OK,intent);
		memos.close();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memolist);
		showMemos(getMemos());
	}
	private Cursor getMemos()
	{
		memos = new MemoDBHelper(this);
		SQLiteDatabase db = memos.getReadableDatabase();
		Cursor cursor = db.query("memoDB", cols, null, null, null, null, null);
		startManagingCursor(cursor);
		return cursor;
	}
	private void showMemos(Cursor cursor)
	{
		if(cursor != null)
		{
			String [] from = {"title"};
			int[] to = {android.R.id.text1};
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
					android.R.layout.simple_list_item_1, cursor, from, to);
			setListAdapter(adapter);
		}
		memos.close();
	}
}
