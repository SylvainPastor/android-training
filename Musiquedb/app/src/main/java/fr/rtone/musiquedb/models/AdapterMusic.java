package fr.rtone.musiquedb.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.rtone.musiquedb.R;

/**
 * Created by sylvain on 05/11/15.
 */
public class AdapterMusic extends ArrayAdapter<Music> {

    private int resId = -1;
    LayoutInflater inflater;

    class ViewHolder {
        public TextView textViewMusic;
        public TextView textViewAuthor;
    };
    ViewHolder holder;

    public AdapterMusic(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        resId = resource;
        inflater = LayoutInflater.from(context);
        // viewHolder
        holder = new ViewHolder();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resId, null);
            holder.textViewMusic  = (TextView)convertView.findViewById(R.id.textViewMusic);
            holder.textViewAuthor = (TextView)convertView.findViewById(R.id.textViewAuthor);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        // mise a jour des champs
        holder.textViewMusic.setText(getItem(position).title);
        holder.textViewAuthor.setText(getItem(position).author);

        return convertView;
    }
}