package tb14.walkbasehackathon;

import java.util.List;

import tb14.walkbasehackathon.Adapter.TaskAdapter;
import tb14.walkbasehackathon.DAOs.LocationDAO;
import tb14.walkbasehackathon.DTO.Location;
import tb14.walkbasehackathon.DTO.Task;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Tab_Magic extends Fragment {
	private LocationDAO locationDAO;
	private ListView list;
	private final String TAG = "MAGIX";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	final View view = inflater.inflate(R.layout.tab_magic, container, false);

    	
    	locationDAO = new LocationDAO(view.getContext());
		locationDAO.open();
		List<Task> values = locationDAO.getAllTask();

		list = (ListView) view.findViewById(R.id.list);
		
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Log.v(TAG, "item "+position+" clicked");
			}
		});
		
		
		ArrayAdapter<Task> adapter = new TaskAdapter(view.getContext(),
				R.layout.taskrow, values);
		list.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
 
}