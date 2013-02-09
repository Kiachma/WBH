package tb14.walkbasehackathon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.PowerManager;
import android.util.Log;

import com.walkbase.location.WBLocation;
import com.walkbase.location.WBLocationManager;
import com.walkbase.location.listeners.WBLocationListener;

public class LocationUpdater extends BroadcastReceiver implements WBLocationListener  {

	final public static String TAG = "WBH BG Update";
	final static long INTERVAL = 10000;

	private WBLocationManager locationmanager;
	private SharedPreferences prefs;
	private Editor editor;
	private PowerManager.WakeLock wl;
	@Override
	public void onReceive(Context context, Intent arg1) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		
		locationmanager = WBLocationManager.getWBLocationManager();
		locationmanager.setApiKey("9ew2ucuohe67381nbwbfbw9sbb9");
		locationmanager.setWBLocationListener(this);
		
		 prefs = (SharedPreferences) context.getSharedPreferences("WBHPrefs", Context.MODE_PRIVATE);
		 editor = prefs.edit();
		
		//Acquire the lock
		wl.acquire();
		locationmanager.fetchLastKnownUserLocation(context);		
	}
	
	public void setAlarm(Context context){
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, LocationUpdater.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL , pi);
	}
	
	public void cancelAlarm(Context context) {
	        Intent intent = new Intent(context, LocationUpdater.class);
	        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
	        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	        alarmManager.cancel(sender);
	    }

	
	@Override
	public void lastKnownLocationWasRetrieved(WBLocation arg0) {
		if (prefs.getLong("timestamp", 1) != arg0.getTimestamp()) {
			editor.putLong("latitude", (long) arg0.getLatitude());
			editor.putLong("longitude", (long) arg0.getLongitude());
			editor.putLong("accuracy", (long) arg0.getAccuracy());
			editor.putLong("timestamp", (long) arg0.getTimestamp());
			editor.commit();
		}
		wl.release();	
	}
	
	private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		double pk = (180 / 3.14169);

		double a1 = lat_a / pk;
		double a2 = lng_a / pk;
		double b1 = lat_b / pk;
		double b2 = lng_b / pk;
		double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
		double t3 = Math.sin(a1) * Math.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);

		return 6366000 * tt;
	}

	
	
	
	@Override
	public void errorFetchingLastKnownLocation(String arg0, int arg1) {
		// TODO Auto-generated method stub
		wl.release();	
	}
	
	@Override
	public void successfullyStartedTheLiveLocationFeed() {
		// NOT FUNCTIONING = NOT USING
	}

	@Override
	public void liveLocationFeedWasClosed() {
		// NOT FUNCTIONING = NOT USING
	}

	@Override
	public void liveLocationWasUpdated(WBLocation arg0) {
		// NOT FUNCTIONING = NOT USING
	}

	@Override
	public void errorFetchingLiveLocationFeed(String arg0, int arg1) {
		// NOT FUNCTIONING = NOT USING
	}
	
}
