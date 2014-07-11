package com.example.montlycalls;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Album_db {
	private static String DATABASE_NAME = "playlist.db";
	private static final int VERSION_BDD = 1;
	private static final String TABLE_ALBUM = "table_album";
	private static final String COL_ID = "ID";
	private static final String COL_ARTIST = "ARTIST";
	private static final String COL_TITLE = "TITLE";    
	private SQLiteDatabase db;
	private DatabaseHelper oDB;

	public Album_db(Context context) {

	    oDB = new DatabaseHelper(context, DATABASE_NAME, null, VERSION_BDD);
	}

	public void open() {
	    db = oDB.getWritableDatabase();
	}

	public void close() {
	    oDB.close();
	}

	public SQLiteDatabase getBd() {
	    return db;
	}

	

	public int removeAlbum(int id) {
	    // Suppression BDD via id
	    return db.delete(TABLE_ALBUM, COL_ID + " = " + id, null);
	} 
}

