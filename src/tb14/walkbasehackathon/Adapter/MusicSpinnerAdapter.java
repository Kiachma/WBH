package tb14.walkbasehackathon.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import tb14.walkbasehackathon.R;
import tb14.walkbasehackathon.DTO.Location;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MusicSpinnerAdapter extends ArrayAdapter<HashMap<String, String>> {

	Context context;
	int textViewResourceId;
	ArrayList<HashMap<String, String>> songList ;

	public MusicSpinnerAdapter(Context context, int textViewResourceId,
			ArrayList<HashMap<String, String>> songList) {
		super(context, textViewResourceId, songList);	
		this.textViewResourceId = textViewResourceId;
        this.context = context;
        this.songList = songList;

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
		label.setText(songList.get(position).get("songPath"));
		return row;
	}

}
