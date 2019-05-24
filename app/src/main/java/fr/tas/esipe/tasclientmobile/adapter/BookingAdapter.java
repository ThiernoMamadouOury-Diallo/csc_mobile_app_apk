package fr.tas.esipe.tasclientmobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.tas.esipe.tasclientmobile.R;

public class BookingAdapter extends ArrayAdapter<Integer> {

    private Context context;
    private int resource;

    public BookingAdapter(@NonNull Context context, int resource, ArrayList<Integer> ids) {
        super(context, resource, ids);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resource, null);
        }

        Integer id = getItem(position);

        TextView tvName = (TextView) v.findViewById(R.id.id_booking);
        tvName.setText(String.valueOf(id));

        return v;
    }
}
