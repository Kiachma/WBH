package tb14.walkbasehackathon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.PowerManager;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver  {

	private int timesRecieved = 0;
	private String msgStr = "";
	final public static String ONE_TIME = "onetime";
	
	@Override
	public void onReceive(Context context, Intent arg1) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		//Acquire the lock
		wl.acquire();
		//You can do the processing here.
		StringBuilder msgStr = new StringBuilder();
		msgStr.append("One time Timer : ");
		msgStr.append(timesRecieved);
		timesRecieved++;
		Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
		 SharedPreferences save= (SharedPreferences) context.getSharedPreferences("WBHPrefs", Context.MODE_PRIVATE);
		 Editor editor = save.edit();
		 
		 
		//Release the lock
		wl.release();	
	}
	
	public void setAlarm(Context context){
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, Alarm.class);
		intent.putExtra(ONE_TIME, Boolean.FALSE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		//After after 5 seconds
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); 
		
	}
	
}
