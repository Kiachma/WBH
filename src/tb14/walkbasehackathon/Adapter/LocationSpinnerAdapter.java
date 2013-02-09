package tb14.walkbasehackathon.Adapter;

import java.util.List;

import tb14.walkbasehackathon.R;
import tb14.walkbasehackathon.DTO.Location;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LocationSpinnerAdapter extends ArrayAdapter<Location> {
	Context context;
	int textViewResourceId;
	List<Location> locations ;

	public LocationSpinnerAdapter(Context context, int textViewResourceId,
			List<Location> locations) {
		super(context, textViewResourceId, locations);	
		this.textViewResourceId = textViewResourceId;
        this.context = context;
        this.locations = locations;

	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View row = inflater.inflate(R.layout.locationspinner, parent, false);
		TextView label = (TextView) row.findViewById(R.id.locationname);
		label.setText(locations.get(position).getName());
		return row;
	}

}
