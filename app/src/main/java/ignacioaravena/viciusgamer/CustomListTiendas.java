package ignacioaravena.viciusgamer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListTiendas extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] precio;
    private final String[] tienda;
    private final String[] url;



    public CustomListTiendas(Activity context, String[] precio, String[] tienda, String[] url) {
        super(context, R.layout.list_row, precio);
        this.context = context;
        this.precio = precio;
        this.tienda = tienda;
        this.url = url;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row_tiendas, null, true);

        TextView txtPrecio = (TextView) rowView.findViewById(R.id.textViewPrecio);
        TextView txtTienda = (TextView) rowView.findViewById(R.id.textViewTienda);
        TextView txtUrl = (TextView) rowView.findViewById(R.id.textViewUrl);

        txtPrecio.setText(precio[position]);
        txtTienda.setText(tienda[position]);
        txtUrl.setText(url[position]);

        return rowView;
    }
}