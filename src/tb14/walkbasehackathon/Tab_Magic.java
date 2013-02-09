package tb14.walkbasehackathon;

import java.util.Date;
import java.util.List;

import tb14.walkbasehackathon.Adapter.LocationAdapter;
import tb14.walkbasehackathon.Adapter.LocationSpinnerAdapter;
import tb14.walkbasehackathon.Adapter.TaskAdapter;
import tb14.walkbasehackathon.DAOs.LocationDAO;
import tb14.walkbasehackathon.DTO.Location;
import tb14.walkbasehackathon.DTO.Task;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
	List<Location> locations;
	List<PackageInfo> packages;
	private final String TAG = "MAGIX";
	List<Task> tasks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	final View view = inflater.inflate(R.layout.tab_magic, container, false);

    	
    	locationDAO = new LocationDAO(view.getContext());
		locationDAO.open();
		tasks = locationDAO.getAllTask();
		
		PackageManager pm=view.getContext().getPackageManager();
		locations = locationDAO.getAllLocations();
		packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);
		final ArrayAdapter<Task> adapter = new TaskAdapter(view.getContext(),
				R.layout.taskrow, tasks);
		initializeLocationSpinner(view);
		initializeTaskTypeSpinner(view);
		//initializeTaskSpinner(view);
		initializeListView(view,adapter);
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
				task = locationDAO.createTask(task);
				adapter.add(task);
				adapter.notifyDataSetChanged();
			}
		});
		
        // Inflate the layout for this fragment
        return view;
    }

	private void initializeTaskTypeSpinner(View view) {
		Spinner spinner = (Spinner) view.findViewById(R.id.type);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				switch(position){
					case 0 : initializeTaskSpinner(view, packages);break;
					case 1 : break;
					case 2 : break;
					case 3 : break;					
				}
				
				
			}
		});
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
		        R.array.type_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
	}

	private void initializeTaskSpinner(View view, List<PackageInfo> packages) {
		taskSpinner = (Spinner)view.findViewById(R.id.taskSpinner);
		ArrayAdapter<PackageInfo> adapter = new ArrayAdapter<PackageInfo>(view.getContext(), android.R.layout.simple_spinner_item, packages);
		taskSpinner.setAdapter(adapter);
	}

	private void initializeLocationSpinner(View view) {
		locationSpinner = (Spinner)view.findViewById(R.id.locationSpinner);
		locationSpinner.setAdapter(new LocationSpinnerAdapter(view.getContext(), R.layout.locationspinner, locations));
		
	}

	private void initializeListView(View view, final ArrayAdapter<Task> adapter) {
		list = (ListView) view.findViewById(R.id.list);

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// When clicked, show a toast with the TextView text
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setMessage("WAAAAAH").setTitle("Weh?");
				builder.setPositiveButton("HOKAY", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.v(TAG, "DELETE EVERYTHING!");
					    Task task = (Task) adapter.getItem(position);
					    locationDAO.deleteTask(task);
					    adapter.remove(task);
					}
				});
				builder.setNegativeButton("NO, I CLEAN!", null);

				
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});

		list.setAdapter(adapter);

	}
 
}