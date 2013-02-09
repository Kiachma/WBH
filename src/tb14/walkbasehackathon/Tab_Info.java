package tb14.walkbasehackathon;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;



public class Tab_Info extends Fragment {
	private final String TAG = "Saved locations";
	private boolean alarmUp;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	final View view = inflater.inflate(R.layout.tab_info,	container, false);

    	//Create buttons
    	final Button setAlarmButton = (Button) view.findViewById(R.id.set_alarm_button);
    	final Button removeAlarmButton = (Button) view.findViewById(R.id.remove_alarm_button);

    	Intent intent = new Intent(view.findViewById(R.id.set_alarm_button).getContext(), LocationUpdater.class);
    	alarmUp = (PendingIntent.getBroadcast(view.getContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    	if(alarmUp){
    		setAlarmButton.setEnabled(false);
    		removeAlarmButton.setEnabled(true);
    	}    	

    	//Code to execute when "set alarm" is clicked
    	setAlarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				LocationUpdater locationUpdater = new LocationUpdater();
				locationUpdater.setAlarm(arg0.getContext());
				setAlarmButton.setEnabled(false);
				removeAlarmButton.setEnabled(true);
			}
		});
    	//Code to execute when "remove alarm" is clicked
    	removeAlarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				LocationUpdater locationUpdater = new LocationUpdater();
				locationUpdater.cancelAlarm(view.getContext());
				removeAlarmButton.setEnabled(false);
				setAlarmButton.setEnabled(true);
				alarmUp = false;
				Log.v(TAG, ""+(PendingIntent.getBroadcast(view.getContext(), 0, new Intent(view.findViewById(R.id.set_alarm_button).getContext(), LocationUpdater.class), PendingIntent.FLAG_NO_CREATE) != null));
			}
		});
    	
    	
    	return view;
    }
    
    
}