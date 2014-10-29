package com.example.ninjaapp.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ShoppingList {
	
	// Database table
		  public static final String TABLE_NAME = "shoppinglist";
		  public static final String COLUMN_ID = "_id";
		  public static final String COLUMN_ITEMNAME = "item_name";
		  public static final String COLUMN_ITEMID = "item_id";
		  public static final String COLUMN_ITEMQUAN = "quantity";

		  // Database creation SQL statement
		  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_NAME
		      + "(" 
		      + COLUMN_ID + " integer primary key autoincrement, "
		      + COLUMN_ITEMNAME + " text not null, "
		      + COLUMN_ITEMID + " text, "
		      + COLUMN_ITEMQUAN + " integer not null"
		      + ");";

		  public static void onCreate(SQLiteDatabase database) {
		    database.execSQL(DATABASE_CREATE);
		  }

		  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
		      int newVersion) {
		    Log.w(ShoppingList.class.getName(), "Upgrading database from version "
		        + oldVersion + " to " + newVersion
		        + ", which will destroy all old data");
		    database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		    onCreate(database);
		  }

}
