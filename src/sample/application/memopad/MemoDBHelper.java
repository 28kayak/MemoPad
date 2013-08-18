package sample.application.memopad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper
{
	public MemoDBHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}
	public MemoDBHelper(Context context)
	{
		super(context,name,factory,version);
	}

}
