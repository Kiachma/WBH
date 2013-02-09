package tb14.walkbasehackathon.DAOs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tb14.walkbasehackathon.MySQLiteHelper;
import tb14.walkbasehackathon.DTO.Location;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LocationDAO {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_ACCURACY, MySQLiteHelper.COLUMN_LATITUDE,
			MySQLiteHelper.COLUMN_LONGITUDE, MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_TIMESTAMP };

	public LocationDAO(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Location createLocation(Location location) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_ACCURACY, location.getAccuracy());
		values.put(MySQLiteHelper.COLUMN_LATITUDE, location.getLatitude());
		values.put(MySQLiteHelper.COLUMN_LONGITUDE, location.getLongitude());
		values.put(MySQLiteHelper.COLUMN_NAME, location.getName());
		values.put(MySQLiteHelper.COLUMN_TIMESTAMP,location.getTimestamp().getTime());
		long insertId = database.insert(MySQLiteHelper.TABLE_LOCATIONS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOCATIONS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Location newLocation = cursorToLocation(cursor);
		cursor.close();
		return newLocation;
	}

	public void deleteLocation(Location location) {
		long id = location.getId();
		System.out.println("Location deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_LOCATIONS,
				MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Location> getAllLocations() {
		List<Location> locations = new ArrayList<Location>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOCATIONS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Location location = cursorToLocation(cursor);
			locations.add(location);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return locations;
	}

	private Location cursorToLocation(Cursor cursor) {
		Location locations = new Location();
		locations.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ID)));
		locations.setAccuracy(cursor.getDouble(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ACCURACY)));
		locations.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_LATITUDE)));
		locations.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_LONGITUDE)));
		locations.setName(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_NAME)));
		long millis = cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TIMESTAMP));
		Date timestamp = new Date(millis);
		locations.setTimestamp(timestamp);
		return locations;
	}

}
