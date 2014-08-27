package info.happyretired.db;

import android.provider.BaseColumns;

public class DBTable {


	    // To prevent someone from accidentally instantiating the contract class,
	    // give it an PreferenceTable constructor.
	    public DBTable() {}

	    /* Inner class that defines the table contents */
	    public static abstract class PreferenceEntry implements BaseColumns {
	    	public static final String TABLE_NAME  = "preference";
	    	public static final String COLUMN_ID = "notification_id";
	    	public static final String COLUMN_FONT_SIZE = "font_size";
	    	public static final String COLUMN_FONT_LINE = "font_line";
	    }
	}


