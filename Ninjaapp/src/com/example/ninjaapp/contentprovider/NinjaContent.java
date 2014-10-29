package com.example.ninjaapp.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import com.example.ninjaapp.database.DatabaseHelper;
import com.example.ninjaapp.database.Inventory;
import com.example.ninjaapp.database.ShoppingList;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class NinjaContent extends ContentProvider {

	private DatabaseHelper database;
	
	private static final int LIST = 10;
	private static final int LIST_ID = 20;
	private static final int INVEN = 30;
	private static final int INVEN_ID = 40;
	
	private static final String AUTHORITY = "com.example.ninjaapp.contentprovider";
	
	private static final String BASE_PATH = "list";
	private static final String BASE_PATH2 = "inventory";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
		      + "/" + BASE_PATH);
		
		public static final Uri CONTENT_URI2 = Uri.parse("content://" + AUTHORITY
			      + "/" + BASE_PATH2);
		
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, LIST);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "#", LIST_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH2, INVEN);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH2 + "#", INVEN_ID);
	}
	
	
	@Override
	public boolean onCreate() {
		database = new DatabaseHelper(getContext());
		return false;
	}
	

	@Override
	public Cursor query (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortorder) {
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		
		case LIST:
			checkListColumns(projection);
			queryBuilder.setTables(ShoppingList.TABLE_NAME);
			break;
		case LIST_ID:
			checkListColumns(projection);
			queryBuilder.setTables(ShoppingList.TABLE_NAME);
			queryBuilder.appendWhere(ShoppingList.COLUMN_ID + "="
			          + uri.getLastPathSegment());
			break;
		case INVEN:
			checkInvenColumns(projection);
			queryBuilder.setTables(Inventory.TABLE_NAME);
			break;
		case INVEN_ID:
			checkInvenColumns(projection);
			queryBuilder.setTables(Inventory.TABLE_NAME);
			queryBuilder.appendWhere(Inventory.COLUMN_ID + "="
			          + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortorder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    Uri returnUri;
	    long id = 0;
	    switch (uriType) {
	    case LIST:
	    	id = sqlDB.insert(ShoppingList.TABLE_NAME, null, values);
	    	returnUri = Uri.parse(BASE_PATH + "/" + id);
	    	break;
	    case INVEN:
	    	id = sqlDB.insert(Inventory.TABLE_NAME, null, values);
	    	returnUri = Uri.parse(BASE_PATH2 + "/" + id);
	    	break;
	    default:
	    	throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return returnUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsDeleted = 0;
	    String id;
	    switch (uriType) {
	    case LIST:
		      rowsDeleted = sqlDB.delete(ShoppingList.TABLE_NAME, selection,
		          selectionArgs);
		      break;
	    case LIST_ID:
			  id = uri.getLastPathSegment();
			  if (TextUtils.isEmpty(selection)) {
			    rowsDeleted = sqlDB.delete(ShoppingList.TABLE_NAME,
			        ShoppingList.COLUMN_ID + "=" + id, 
			        null);
			  } else {
			    rowsDeleted = sqlDB.delete(ShoppingList.TABLE_NAME,
			        ShoppingList.COLUMN_ID + "=" + id 
			        + " and " + selection,
			        selectionArgs);
			  }
			  break;
	    case INVEN:
		      rowsDeleted = sqlDB.delete(Inventory.TABLE_NAME, selection,
		          selectionArgs);
		      break;
	    case INVEN_ID:
			  id = uri.getLastPathSegment();
			  if (TextUtils.isEmpty(selection)) {
			    rowsDeleted = sqlDB.delete(Inventory.TABLE_NAME,
			        Inventory.COLUMN_ID + "=" + id, 
			        null);
			  } else {
			    rowsDeleted = sqlDB.delete(Inventory.TABLE_NAME,
			        Inventory.COLUMN_ID + "=" + id 
			        + " and " + selection,
			        selectionArgs);
			  }
			  break;  
	    default:
	    	throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsUpdated = 0;
	    String id;
	    switch (uriType) {
	    case LIST:
		      rowsUpdated = sqlDB.update(ShoppingList.TABLE_NAME, 
		          values, 
		          selection,
		          selectionArgs);
		      break;
	    case LIST_ID:
		      id = uri.getLastPathSegment();
		      if (TextUtils.isEmpty(selection)) {
		        rowsUpdated = sqlDB.update(ShoppingList.TABLE_NAME, 
		            values,
		            ShoppingList.COLUMN_ID + "=" + id, 
		            null);
		      } else {
		        rowsUpdated = sqlDB.update(ShoppingList.TABLE_NAME, 
		            values,
		            ShoppingList.COLUMN_ID + "=" + id 
		            + " and " 
		            + selection,
		            selectionArgs);
		      }
		      break;
	    case INVEN:
		      rowsUpdated = sqlDB.update(Inventory.TABLE_NAME, 
		          values, 
		          selection,
		          selectionArgs);
		      break;
	    case INVEN_ID:
		      id = uri.getLastPathSegment();
		      if (TextUtils.isEmpty(selection)) {
		        rowsUpdated = sqlDB.update(Inventory.TABLE_NAME, 
		            values,
		            Inventory.COLUMN_ID + "=" + id, 
		            null);
		      } else {
		        rowsUpdated = sqlDB.update(Inventory.TABLE_NAME, 
		            values,
		            Inventory.COLUMN_ID + "=" + id 
		            + " and " 
		            + selection,
		            selectionArgs);
		      }
		      break;
	    default:
		      throw new IllegalArgumentException("Unknown URI: " + uri);
		    }
		    getContext().getContentResolver().notifyChange(uri, null);
		    return rowsUpdated;
	}

	private void checkListColumns(String[] projection) {
	    String[] available = { ShoppingList.COLUMN_ITEMNAME, ShoppingList.COLUMN_ITEMID, 
		        ShoppingList.COLUMN_ITEMQUAN, ShoppingList.COLUMN_ID };
		    if (projection != null) {
		      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
		      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
		      // check if all columns which are requested are available
		      if (!availableColumns.containsAll(requestedColumns)) {
		        throw new IllegalArgumentException("Unknown columns in projection");
		      }
		    }
		  }
	
	private void checkInvenColumns(String[] projection) {
	    String[] available = { Inventory.COLUMN_CONSUMP, Inventory.COLUMN_ENTRYDATE,
	    		Inventory.COLUMN_EXPR, Inventory.COLUMN_ID, Inventory.COLUMN_ITEMID,
	    		Inventory.COLUMN_ITEMNAME, Inventory.COLUMN_ITEMQUAN, Inventory.COLUMN_PRIORITY };
		    if (projection != null) {
		      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
		      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
		      // check if all columns which are requested are available
		      if (!availableColumns.containsAll(requestedColumns)) {
		        throw new IllegalArgumentException("Unknown columns in projection");
		      }
		    }
		  }
	
}
