package tb14.walkbasehackathon;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Tab_Info extends Fragment {
	private final String TAG = "Saved locations";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	final View view = inflater.inflate(R.layout.tab_info,	container, false);
    	
    	//Create buttons
    	Button setAlarmButton = (Button) view.findViewById(R.id.set_alarm_button);
    	Button removeAlarmButton = (Button) view.findViewById(R.id.remove_alarm_button);
    	
    	//Code to execute when "set alarm" is clicked
    	setAlarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				LocationUpdater locationUpdater = new LocationUpdater();
				locationUpdater.setAlarm(arg0.getContext());
				
			}
		});
    	//Code to execute when "remove alarm" is clicked
    	removeAlarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				LocationUpdater locationUpdater = new LocationUpdater();
				locationUpdater.cancelAlarm(view.getContext());
			}
		});
    	
    	return view;
    }
    
    
}