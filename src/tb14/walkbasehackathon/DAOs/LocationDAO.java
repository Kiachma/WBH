package tb14.walkbasehackathon.DAOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tb14.walkbasehackathon.MySQLiteHelper;
import tb14.walkbasehackathon.DTO.Location;
import tb14.walkbasehackathon.DTO.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LocationDAO {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allLocationColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_ACCURACY, MySQLiteHelper.COLUMN_LATITUDE,
			MySQLiteHelper.COLUMN_LONGITUDE, MySQLiteHelper.COLUMN_NAME,
			MySQLiteHelper.COLUMN_TIMESTAMP };

	private String[] allTaskColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_LOCATION, MySQLiteHelper.COLUMN_TASK ,MySQLiteHelper.COLUMN_TYPE};

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
		values.put(MySQLiteHelper.COLUMN_TIMESTAMP, location.getTimestamp()
				.getTime());
		long insertId = database.insert(MySQLiteHelper.TABLE_LOCATIONS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOCATIONS,
				allLocationColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
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
	
	public void deleteTask(Task task) {
		long id = task.getId();
		System.out.println("Task deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_TASK,
				MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Location> getAllLocations() {
		List<Location> locations = new ArrayList<Location>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOCATIONS,
				allLocationColumns, null, null, null, null, null);

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
	public List<Task> getAllTask() {
		List<Task> tasks = new ArrayList<Task>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_TASK,
				allTaskColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Task task = cursorToTask(cursor);
			tasks.add(task);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return tasks;
	}

	public Task createTask(Task task) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_LOCATION, task.getLocation().getId());
		values.put(MySQLiteHelper.COLUMN_TASK, task.getTask());
		values.put(MySQLiteHelper.COLUMN_TYPE, task.getType());
		long insertId = database
				.insert(MySQLiteHelper.TABLE_TASK, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_TASK,
				allTaskColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		Task newTask = cursorToTask(cursor);
		cursor.close();
		return newTask;
	}

	private Task cursorToTask(Cursor cursor) {
		Task task = new Task();
		task.setId(cursor.getLong(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ID)));
		Location location = getLocation(cursor.getLong(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_LOCATION)));
		task.setLocation(location);
		task.setTask(cursor.getString(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TASK)));
		task.setType(cursor.getInt(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TYPE)));
		return task;
	}

	public Location getLocation(long locationId) {
		Location location = new Location();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOCATIONS,
				allLocationColumns, MySQLiteHelper.COLUMN_ID + "="
						+ locationId, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			location = cursorToLocation(cursor);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return location;
	}

	private Location cursorToLocation(Cursor cursor) {
		Location locations = new Location();
		locations.setId(cursor.getLong(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ID)));
		locations.setAccuracy(cursor.getDouble(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ACCURACY)));
		locations.setLatitude(cursor.getDouble(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_LATITUDE)));
		locations.setLongitude(cursor.getDouble(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_LONGITUDE)));
		locations.setName(cursor.getString(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_NAME)));
		long millis = cursor.getLong(cursor
				.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TIMESTAMP));
		Date timestamp = new Date(millis);
		locations.setTimestamp(timestamp);
		return locations;
	}

}
