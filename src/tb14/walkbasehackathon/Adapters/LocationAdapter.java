package tb14.walkbasehackathon.Adapters;


import java.util.List;

import tb14.walkbasehackathon.R;
import tb14.walkbasehackathon.DTO.Location;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationAdapter extends ArrayAdapter<Location> {
	
	Context context;
    int layoutResourceId;   
    List<Location> data = null;
   
    public LocationAdapter(Context context, int layoutResourceId,List<Location> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LocationHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new LocationHolder();
            holder.deleteButton = (Button)row.findViewById(R.id.delete);
            holder.locationName = (TextView)row.findViewById(R.id.name);
           
            row.setTag(holder);
        }
        else
        {
            holder = (LocationHolder)row.getTag();
        }
       
        Location location = data.get(position);
        holder.deleteButton.setText(R.string.delete_location);
        holder.locationName.setText(location.getName());
        return row;
    }
    
    
   
    static class LocationHolder
    {
        Button deleteButton;
        TextView locationName;
    }

}
