package tb14.walkbasehackathon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	public static final String COLUMN_ID = "_id";

	public static final String TABLE_LOCATIONS = "locations";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_ACCURACY = "accuracy";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_NAME = "name";
	
	
	public static final String TABLE_TASK = "task";
	public static final String COLUMN_TASK = "task";
	public static final String COLUMN_LOCATION = "location";
	
	
	

	private static final String DATABASE_NAME = "hackathon.db";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_LOCATIONS=" create table "
			+ TABLE_LOCATIONS + " ( " + 
			COLUMN_ID+ " integer primary key autoincrement , " + 
			COLUMN_ACCURACY+ " DOUBLE PRECISION not null , " + 
			COLUMN_LATITUDE + " DOUBLE PRECISION not null , "+
			COLUMN_NAME + " Text not null , "+ 
			COLUMN_TIMESTAMP + " datetime not null , "+ 
			COLUMN_LONGITUDE + " DOUBLE PRECISION not null) ; ";
	
	private static final String CREATE_TASK=" create table "
			+ TABLE_TASK + " ( " + 
			COLUMN_ID+ " integer primary key autoincrement , " + 
			COLUMN_LOCATION+ " integer not null , " + 
			COLUMN_TASK + " Text not null); ";
	
	

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_LOCATIONS);
		database.execSQL(CREATE_TASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
		onCreate(db);
	}

}
