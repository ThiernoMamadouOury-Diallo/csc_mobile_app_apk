package fr.tas.esipe.tasclientmobile.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import fr.tas.esipe.tasclientmobile.R;
import fr.tas.esipe.tasclientmobile.model.BillFileBean;

public class PdfFileAdapter extends ArrayAdapter<BillFileBean> {

    Context cxt;
    int res;
    ArrayList<BillFileBean> list;

    public PdfFileAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<BillFileBean> objects) {
        super(context, resource, objects);

        cxt = context;
        res = resource;
        list = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Initializing view which will point to layout file list_item
        View view = LayoutInflater.from(cxt).inflate(res, parent, false);

        //Text view showing pdf file name
        TextView txtView = (TextView)view.findViewById(R.id.billFileName);

        //setting the file name
        File pdf = new File(list.get(position).getBillName());

        txtView.setText(pdf.getName());
        return view;
    }
}
