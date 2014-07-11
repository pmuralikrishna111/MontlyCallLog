package com.example.montlycalls;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

		private static final String TABLE_ALBUM = "table_album";
		private static final String COL_ID = "ID";
		private static final String COL_ARTIST = "ARTIST";
		private static final String COL_TITLE = "TITLE";
		private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ALBUM
		        + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		        + COL_ARTIST + " TEXT NOT NULL, " + COL_TITLE + " TEXT NOT NULL);";

		private static final String CREATE_TAB1 = "CREATE TABLE CSLOG" 
				+ " (COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ " MOBILENO TEXT, "
				+ " TS LONG,"
				+ "UNITS INTEGER"
				+ "TYPE TEXT)";
		//UNITS FOR CALL WILL IN SECS AND SMS WILL BE COUNT
		public DatabaseHelper(Context context, String name, CursorFactory factory,
		        int version) {
		    super(context, name, factory, version);
		    // TODO Auto-generated constructor stub
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
		    // TODO Auto-generated method stub
		    db.execSQL(CREATE_BDD);
		    db.execSQL(CREATE_TAB1);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    // TODO Auto-generated method stub
		    db.execSQL("DROP TABLE " + TABLE_ALBUM + ";");
		    onCreate(db);
		}
}
