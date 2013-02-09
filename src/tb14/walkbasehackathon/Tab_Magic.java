package tb14.walkbasehackathon;

import java.util.Date;
import java.util.List;

import tb14.walkbasehackathon.Adapter.LocationAdapter;
import tb14.walkbasehackathon.Adapter.LocationSpinnerAdapter;
import tb14.walkbasehackathon.Adapter.TaskAdapter;
import tb14.walkbasehackathon.DAOs.LocationDAO;
import tb14.walkbasehackathon.DTO.Location;
import tb14.walkbasehackathon.DTO.Task;
import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_Magic extends Fragment {
	private LocationDAO locationDAO;
	private ListView list;
	private Spinner locationSpinner;
	private Spinner taskSpinner;
	private final String TAG = "MAGIX";
	List<Task> tasks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	final View view = inflater.inflate(R.layout.tab_magic, container, false);

    	
    	locationDAO = new LocationDAO(view.getContext());
		locationDAO.open();
		tasks = locationDAO.getAllTask();
		List<Location> locations = locationDAO.getAllLocations();
		PackageManager pm=view.getContext().getPackageManager();
		List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);
		
		initializeLocationSpinner(view,locations);
		initializeTaskSpinner(view,packages);
		initializeListView(view,tasks);
		Button addTaskButton = (Button)view.findViewById(R.id.add_task);
		addTaskButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner locationSpinner = (Spinner) view
						.findViewById(R.id.locationSpinner);
				Spinner taskSpinner = (Spinner) view
						.findViewById(R.id.taskSpinner);
				Task task = new Task();
				task.setLocation((Location)locationSpinner.getSelectedItem());
				task.setTask(((PackageInfo)taskSpinner.getSelectedItem()).packageName);
				ArrayAdapter<Task> adapter = new TaskAdapter(view.getContext(),
						R.layout.locationrow, tasks);
				task = locationDAO.createTask(task);
				adapter.add(task);
			}
		});
		
        // Inflate the layout for this fragment
        return view;
    }

	private void initializeTaskSpinner(View view, List<PackageInfo> packages) {
		taskSpinner = (Spinner)view.findViewById(R.id.taskSpinner);
		ArrayAdapter<PackageInfo> adapter = new ArrayAdapter<PackageInfo>(view.getContext(), android.R.layout.simple_spinner_item, packages);
		taskSpinner.setAdapter(adapter);
	}

	private void initializeLocationSpinner(View view, List<Location> locations) {
		locationSpinner = (Spinner)view.findViewById(R.id.locationSpinner);
		locationSpinner.setAdapter(new LocationSpinnerAdapter(view.getContext(), R.layout.locationspinner, locations));
		
	}

	private void initializeListView(View view, List<Task> values) {
		list = (ListView) view.findViewById(R.id.list);

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Log.v(TAG, "item " + position + " clicked");
			}
		});

		ArrayAdapter<Task> adapter = new TaskAdapter(view.getContext(),
				R.layout.taskrow, values);
		list.setAdapter(adapter);

	}
 
}