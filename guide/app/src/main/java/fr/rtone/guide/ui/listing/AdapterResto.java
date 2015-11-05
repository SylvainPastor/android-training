package fr.rtone.guide.ui.listing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.rtone.guide.R;
import fr.rtone.guide.models.Restaurant;

/**
 * Created by sylvain on 04/11/15.
 */
public class AdapterResto extends ArrayAdapter<Restaurant>{

    LayoutInflater layoutInflater; // Objet pour remplacer un layout à partir d'un fichier xml
    int resId;

    public AdapterResto(Context context, int resource, List<Restaurant> objects) {
        super(context, resource, objects);

        layoutInflater = LayoutInflater.from(context);
        resId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView==null) {
            convertView = layoutInflater.inflate(resId, null/**/);

            holder = new ViewHolder();
            holder.textViewTitle = (TextView)convertView.findViewById(R.id.textViewTitle);
            holder.textViewCategory = (TextView)convertView.findViewById(R.id.textViewCategory);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Restaurant item = getItem(position);
        holder.textViewTitle.setText(item.name);
        holder.textViewCategory.setText(item.category);

        //return super.getView(position, convertView, parent);
        return convertView;
    }

    class ViewHolder {
        TextView textViewTitle;
        TextView textViewCategory;
    }
}

