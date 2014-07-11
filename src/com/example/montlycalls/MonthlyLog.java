package com.example.montlycalls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.UrlQuerySanitizer.ValueSanitizer;
import android.provider.SyncStateContract.Columns;

public class MonthlyLog {
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private static String DATABASE_NAME = "MonthlyLog.db";
	private static int DATABASE_VERSION = 1;
	private static final String TABLE_ALBUM = "table_album";
	private static final String COL_ID = "ID";
	private static final String COL_ARTIST = "ARTIST";
	private static final String COL_TITLE = "TITLE";
	private static final ContentValues value=new ContentValues(1);   
	
	
	public MonthlyLog(Context context)
	{
		DBHelper=new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void open()
	{
		db=DBHelper.getWritableDatabase();
	}
	
	public void close() {
	    db.close();
	}

	public long insert()
	{
		
	    // Suppression BDD via id
		value.put(COL_ARTIST, "123");
		value.put(COL_TITLE, "123title");
		//db.execSQL("insert into "+TABLE_ALBUM + " (ARTIST,TITLE) VALUES ('123','123TITLE')");
		
		//return 1; 
	    return db.insert(TABLE_ALBUM, null, value);
	}
	public Cursor select()
	{
		String[] Columns = {COL_ARTIST,COL_TITLE};
		return db.query(TABLE_ALBUM, Columns, null, null, null, null, null);
	}
	
	public SQLiteDatabase getBd() {
	    return db;
	}

}
