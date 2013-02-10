package tb14.walkbasehackathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tb14.walkbasehackathon.Adapter.LocationSpinnerAdapter;
import tb14.walkbasehackathon.Adapter.MusicSpinnerAdapter;
import tb14.walkbasehackathon.Adapter.TaskAdapter;
import tb14.walkbasehackathon.DAOs.LocationDAO;
import tb14.walkbasehackathon.DTO.Location;
import tb14.walkbasehackathon.DTO.Task;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class Tab_Magic extends Fragment {
	private LocationDAO locationDAO;
	private ListView list;
	private Spinner locationSpinner;
	private Spinner taskSpinner;
	List<Location> locations;
	List<PackageInfo> packages;
	private final String TAG = "MAGIX";
	List<Task> tasks;
	private Context ctx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	final View view = inflater.inflate(R.layout.tab_magic, container, false);
    	ctx = view.getContext();
    	
    	locationDAO = new LocationDAO(view.getContext());
		locationDAO.open();
		tasks = locationDAO.getAllTask();	
		locations = locationDAO.getAllLocations();
		
		final ArrayAdapter<Task> adapter = new TaskAdapter(view.getContext(),
				R.layout.taskrow, tasks);
		initializeLocationSpinner(view);
		initializeTaskTypeSpinner(view);
		//initializeTaskSpinner(view);
		initializeListView(view,adapter);

		final EditText editMessage = (EditText) view.findViewById(R.id.phone_message_text);
		final EditText editNumber = (EditText) view.findViewById(R.id.phone_number_text);

		Button addTaskButton = (Button)view.findViewById(R.id.add_task);
		
		addTaskButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner locationSpinner = (Spinner) view
						.findViewById(R.id.locationSpinner);
				Spinner taskSpinner = (Spinner) view
						.findViewById(R.id.taskSpinner);
				Spinner typeSpinner = (Spinner) view.findViewById(R.id.type);
				
				Task task = new Task();
				task.setType((int)typeSpinner.getSelectedItemId());
				task.setLocation((Location)locationSpinner.getSelectedItem());
				switch((int)typeSpinner.getSelectedItemId()){
					case 0:task.setTask(((PackageInfo)taskSpinner.getSelectedItem()).packageName);break;
					case 1:	task.setTask(uri);break;
					case 2:task.setTask(editNumber.getText().toString() + "/" + editMessage.getText().toString());break;

				}
				task = locationDAO.createTask(task);
				adapter.add(task);
				adapter.notifyDataSetChanged();
			}
		});
		
        // Inflate the layout for this fragment
        return view;
    }
    
    private String uri;
    @Override
	public void onActivityResult(int aRequestCode, int aResultCode, Intent aData) {
    	if (aData!=null) {
    		uri = aData.getDataString();
    	}
        super.onActivityResult(aRequestCode, aResultCode, aData);
    }

	private void initializeTaskTypeSpinner(final View view) {
		Spinner spinner = (Spinner) view.findViewById(R.id.type);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{    
		 @Override
		 public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

		 switch(i){
				case 0:
					PackageManager pm = view.getContext().getPackageManager();
					List<PackageInfo> packages = pm
							.getInstalledPackages(PackageManager.GET_META_DATA);
					initializeTaskSpinner(view, packages);

					taskSpinner.setVisibility(View.VISIBLE);

					view.findViewById(R.id.phone_message_text).setVisibility(8);
					view.findViewById(R.id.phone_number_text).setVisibility(8);
					view.findViewById(R.id.taskSpinner).setVisibility(0);

					break;
					
				case 1:
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("audio/*");
					Intent c = Intent.createChooser(intent, "Select soundfile");
					startActivityForResult(c,1);
					taskSpinner.setVisibility(View.GONE);
					view.findViewById(R.id.phone_message_text).setVisibility(View.GONE);
					view.findViewById(R.id.phone_number_text).setVisibility(View.GONE);
					break;
					
				case 2:
					view.findViewById(R.id.phone_message_text).setVisibility(0);
					view.findViewById(R.id.phone_number_text).setVisibility(0);
					view.findViewById(R.id.taskSpinner).setVisibility(8);

					break;

		 }
		} 
		  @Override     
		  public void onNothingSelected(AdapterView<?> parentView) 
		{         

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

	protected void initializeMusicSpinner(View view, ArrayList<HashMap<String, String>> songList) {
		taskSpinner = (Spinner)view.findViewById(R.id.taskSpinner); 
		taskSpinner.setAdapter(new MusicSpinnerAdapter(view.getContext(), R.layout.locationspinner, songList));
		
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
				builder.setMessage("Confirm deletion of\n"+adapter.getItem(position).getTask()).setTitle("Delete?");
				builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					    Task task = (Task) adapter.getItem(position);
					    locationDAO.deleteTask(task);
					    adapter.remove(task);
					}
				});
				builder.setNegativeButton("Cancel", null);

				
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});

		list.setAdapter(adapter);

	}

 
}


