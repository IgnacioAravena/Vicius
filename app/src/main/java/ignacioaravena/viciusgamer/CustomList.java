package ignacioaravena.viciusgamer;

import android.app.Activity;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] imageUrl;
    private final String[] textId;


    public CustomList(Activity context, String[] imageUrl, String[] textId) {
        super(context, R.layout.list_row, imageUrl);
        this.context = context;
        this.imageUrl = imageUrl;
        this.textId = textId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtId = (TextView) rowView.findViewById(R.id.textId);
        Picasso.with(context).load(imageUrl[position]).into(imageView);
        txtId.setText(textId[position]);
        return rowView;
    }
}