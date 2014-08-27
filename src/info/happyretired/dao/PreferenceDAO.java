package info.happyretired.dao;

import info.happyretired.db.DBTable.PreferenceEntry;
import info.happyretired.db.MySQLiteHelper;
import info.happyretired.model.Preference;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PreferenceDAO {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
	public PreferenceDAO(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public void update(Preference obj){
		
		open();
		ContentValues values = new ContentValues();
		values.put(PreferenceEntry.COLUMN_ID, obj.getNofiticationID());
		values.put(PreferenceEntry.COLUMN_FONT_SIZE, obj.getFontSize());
		values.put(PreferenceEntry.COLUMN_FONT_LINE, obj.getFontLine());
	    long insertId = database.update(PreferenceEntry.TABLE_NAME, values, null, null);
	    close();
	}
	
	public Preference getPreference() {
		
		open();
		
		String[] projection = {
				PreferenceEntry.COLUMN_ID,
				PreferenceEntry.COLUMN_FONT_SIZE,
				PreferenceEntry.COLUMN_FONT_LINE
			    };
		
		Cursor cursor = database.query(
				PreferenceEntry.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    null,                                // The columns for the WHERE clause
			    null,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    null                                 // The sort order
			    );
		
		cursor.moveToFirst();
		Preference obj = new Preference();
		obj.setNofiticationID(cursor.getLong(cursor.getColumnIndexOrThrow(PreferenceEntry.COLUMN_ID)));
		obj.setFontSize(cursor.getInt(cursor.getColumnIndexOrThrow(PreferenceEntry.COLUMN_FONT_SIZE)));
		obj.setFontLine(cursor.getInt(cursor.getColumnIndexOrThrow(PreferenceEntry.COLUMN_FONT_LINE)));
		
		close();
		
	    return obj;
		
	}
}
