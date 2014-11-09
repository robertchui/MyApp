package info.happyretired.db;

import java.util.HashMap;

import info.happyretired.db.DBTable.PreferenceEntry;
import info.happyretired.model.Preference;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper {

	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";

	  private static final String DATABASE_NAME = "happyretired.db";
	  private static final int DATABASE_VERSION = 3;
	  
	// Login Table Columns names
	  private static final String TABLE_LOGIN = "login";
	    private static final String KEY_ID = "id";
	    
	    private static final String KEY_USERNAME = "username";
	    private static final String KEY_NAME = "name";
	    private static final String KEY_EMAIL = "email";
	    private static final String KEY_UID = "uid";
	    private static final String KEY_CREATED_AT = "created_at";

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
	    
	    String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
	    database.execSQL(CREATE_LOGIN_TABLE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    
	    db.execSQL("DROP TABLE IF EXISTS " + PreferenceEntry.TABLE_NAME);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
	    onCreate(db);
	  }
	  
	  /**
	     * Storing user details in database
	     * */
	    public void addUser(String username, String name, String email, String uid, String created_at) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(KEY_USERNAME, username); // Name
	        values.put(KEY_NAME, name); // Name
	        values.put(KEY_EMAIL, email); // Email
	        values.put(KEY_UID, uid); // Email
	        values.put(KEY_CREATED_AT, created_at); // Created At
	 
	        // Inserting Row
	        db.insert(TABLE_LOGIN, null, values);
	        db.close(); // Closing database connection
	    }
	    
	    /**
	     * Getting user data from database
	     * */
	    public HashMap<String, String> getUserDetails(){
	        HashMap<String,String> user = new HashMap<String,String>();
	        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
	          
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        // Move to first row
	        cursor.moveToFirst();
	        if(cursor.getCount() > 0){
	        	user.put("username", cursor.getString(1));
	            user.put("name", cursor.getString(2));
	            user.put("email", cursor.getString(3));
	            user.put("uid", cursor.getString(4));
	            user.put("created_at", cursor.getString(5));
	        }
	        cursor.close();
	        db.close();
	        // return user
	        return user;
	    }
	    
	    /**
	     * Getting user login status
	     * return true if rows are there in table
	     * */
	    public int getRowCount() {
	        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        int rowCount = cursor.getCount();
	        db.close();
	        cursor.close();
	         
	        // return row count
	        return rowCount;
	    }
	     
	    /**
	     * Re crate database
	     * Delete all tables and create them again
	     * */
	    public void resetTables(){
	        SQLiteDatabase db = this.getWritableDatabase();
	        // Delete All Rows
	        db.delete(TABLE_LOGIN, null, null);
	        db.close();
	    }

	} 