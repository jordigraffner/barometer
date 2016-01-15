package graffner.jordi.barometer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.R;

/**
 * Created by wesley tjin on 29-12-15.
 */
public class InvoerAdapter extends ArrayAdapter<CourseModel> {



public InvoerAdapter(Context context, int resource, List<CourseModel> objects){
        super(context, resource, objects);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null ) {
        vh = new ViewHolder();
        LayoutInflater li = LayoutInflater.from(getContext());
        convertView = li.inflate(R.layout.view_content_row, parent, false);
        vh.name = (TextView) convertView.findViewById(R.id.subject_name);
        convertView.setTag(vh);
        } else {
        vh = (ViewHolder) convertView.getTag();
        }
        CourseModel cm = getItem(position);
        vh.name.setText(cm.name);
        return convertView;
        }

private static class ViewHolder {
    TextView name;


}
}
