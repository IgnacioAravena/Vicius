package ignacioaravena.viciusgamer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class Detalle extends AppCompatActivity {

    TextView txt_nombre, txt_plataforma, txt_desarrollador, txt_clasificacion;
    ImageView imageview_juego;
    ListView tiendas;
    Bundle bundle;
    ArrayList<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        bundle= getIntent().getExtras();
        String idp=bundle.getString("idp");
        doProducto(idp);
        txt_nombre = (TextView)findViewById(R.id.txt_nombre);
        txt_plataforma = (TextView)findViewById(R.id.txt_plataforma);
        txt_desarrollador = (TextView)findViewById(R.id.txt_desarrollador);
        txt_clasificacion = (TextView)findViewById(R.id.txt_clasificacion);
        imageview_juego = (ImageView)findViewById(R.id.imageview_juego);
        tiendas = (ListView) findViewById(R.id.tiendas);
        tiendas.setFocusable(false);


        tiendas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TableLayout tr = (TableLayout) view;
                TextView t = (TextView) tr.findViewById(R.id.textViewUrl);
//                Toast.makeText(getBaseContext(), t.getText(), Toast.LENGTH_LONG).show();
                String url = (String) t.getText();
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(url));
                startActivity(myWebLink);

            }
        });
    }

    public void doProducto(String id){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://www.viciusgamer.cl/product/api/"+id;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if  (statusCode == 200) {
                    //datos del juego
                    ArrayList<String> dataProducto = obDatosJSONProducto(new String(responseBody));
                    txt_nombre.setText(dataProducto.get(2));
                    txt_plataforma.setText(dataProducto.get(4));
                    txt_desarrollador.setText(dataProducto.get(5));
                    txt_clasificacion.setText(dataProducto.get(3));
                    // imagen
                    Picasso.with(getApplicationContext()).load(dataProducto.get(1)).resize(200, 280).into(imageview_juego);
                    // listado de tiendas
                    ArrayList<String> dataTiendas = obDatosJSONTiendas(new String(responseBody));
                    cargaLista(dataTiendas);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }


    public ArrayList<String> obDatosJSONProducto(String response){
        ArrayList<String> producto = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray("["+response+"]");
            String data;
            String detail;
            for (int i=0; i<jsonArray.length(); i++){
                data = jsonArray.getJSONObject(i).getString("id");//0
                producto.add(data);
                data = jsonArray.getJSONObject(i).getString("image_s3");//1
                producto.add(data);
                detail = jsonArray.getJSONObject(i).getString("detail");
                JSONArray ddata = new JSONArray("["+detail+"]"); // Detail es un objeto hacia adentro
                data = ddata.getJSONObject(0).getString("name");//2
                producto.add(data);
                data = ddata.getJSONObject(0).getString("esrb");//3
                producto.add(data);
                data = jsonArray.getJSONObject(i).getString("platform");//4
                producto.add(data);
                data = jsonArray.getJSONObject(i).getString("developer");//5
                producto.add(data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return producto;
    }


    public ArrayList<String> obDatosJSONTiendas(String response){
        ArrayList<String> dealers = new ArrayList<>();
        String dealer;
        try{
            JSONArray jsonArray = new JSONArray("["+response+"]");
            String data;
            for (int i=0; i < jsonArray.length(); i++){
                data = jsonArray.getJSONObject(i).getString("dealers");
                JSONArray ddata = new JSONArray(data);
                for (int j=0; j < ddata.length(); j++){
                    // $$$$,TIENDA,URL
                    if(!Objects.equals(ddata.getJSONObject(j).getString("name"), "Amazon")){
                        dealer = "$" + ddata.getJSONObject(j).getString("price")+ ",";
                        dealer = dealer + ddata.getJSONObject(j).getString("name")+",";
                        dealer = dealer + ddata.getJSONObject(j).getString("url");
                        dealers.add(dealer);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  dealers;
    }

    public void cargaLista(ArrayList<String> datos){
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
//        tiendas.setAdapter(adapter);

        ArrayList<String> Arr_precio = new ArrayList<String>();
        ArrayList<String> Arr_tienda = new ArrayList<String>();
        ArrayList<String> Arr_url = new ArrayList<String>();

        for(int i=0; i < datos.size(); i++){
            String[] j = datos.get(i).split(",");
            Arr_precio.add(j[0]);
            Arr_tienda.add(j[1]);
            Arr_url.add(j[2]);
        }

        String[] precio = new String[Arr_precio.size()];
        precio = Arr_precio.toArray(precio);

        String[] tienda = new String[Arr_tienda.size()];
        tienda = Arr_tienda.toArray(tienda);

        String[] url = new String[Arr_url.size()];
        url = Arr_url.toArray(url);

        CustomListTiendas adapter = new CustomListTiendas(Detalle.this, precio, tienda, url );
        tiendas.setAdapter(adapter);

    }

}

///////////////////////////////////////////////////////////////////////
//{
//        "id": 1016,
//        "slug": "ps4-battlefield-4",
//        "image_s3": "http://viciusgamer.s3.amazonaws.com/media/products/ps4-battlefield-4.jpg",
//          "detail": {
//              "name": "Battlefield 4",
//              "esrb": "M"
//        },
//        "platform": "PS4",
//        "developer": "EA DICE",
//        "publisher": "Electronic Arts",
//        "dealers": [
//          {
//              "name": "Linio",
//              "price": 13990,
//              "url": "https://www.linio.cl/p/battlefield-4-xbox-one-vdf6nq",
//              "updated_at": "2016-12-02T16:30:01.722579Z"
//          },
//          {
//              "name": "Weplay",
//              "price": 14990,
//              "url": "http://www.weplay.cl/store/1400-battlefield_4_ps4",
//              "updated_at": "2016-12-02T13:33:39.195750Z"
//          },
//        ]
//        }
///////////////////////////////////////////
