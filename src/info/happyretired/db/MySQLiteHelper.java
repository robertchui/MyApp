package info.happyretired.db;

import info.happyretired.db.DBTable.PreferenceEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper {

	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";

	  private static final String DATABASE_NAME = "happyretired.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  
	  private static final String DATABASE_CREATE =
			    "CREATE TABLE " + PreferenceEntry.TABLE_NAME + " (" +
			    PreferenceEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
			    PreferenceEntry.COLUMN_FONT_SIZE + INTEGER_TYPE + COMMA_SEP +
			    PreferenceEntry.COLUMN_FONT_LINE + INTEGER_TYPE +
			    " )";
	 

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	    
	    int id = 0;
	    int font_size = 20;
	    int font_line = 10;
	    
	    ContentValues values = new ContentValues();
	    values.put(PreferenceEntry.COLUMN_ID, id);
	    values.put(PreferenceEntry.COLUMN_FONT_SIZE, font_size);
	    values.put(PreferenceEntry.COLUMN_FONT_LINE, font_line);
	    
	 // Insert the new row, returning the primary key value of the new row
	    long newRowId;
	    newRowId = database.insert(PreferenceEntry.TABLE_NAME, null, values);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    
	    db.execSQL("DROP TABLE IF EXISTS " + PreferenceEntry.TABLE_NAME);
	    onCreate(db);
	  }

	} 