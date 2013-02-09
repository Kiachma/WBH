package tb14.walkbasehackathon.Adapter;

import java.util.List;

import tb14.walkbasehackathon.R;
import tb14.walkbasehackathon.DTO.Location;
import tb14.walkbasehackathon.DTO.Task;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task> {

	Context context;
    int layoutResourceId;   
    List<Task> data = null;
   
    public TaskAdapter(Context context, int layoutResourceId, List<Task> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new TaskHolder();
            holder.location = (TextView)row.findViewById(R.id.location);
            holder.task = (TextView)row.findViewById(R.id.task);
           
            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }
       
        Task task = data.get(position);
        holder.task.setText(task.getTask());
        holder.location.setText(task.getLocation().getName());
       
        return row;
    }
   
    static class TaskHolder
    {
        TextView location;
        TextView task;
    }
}
