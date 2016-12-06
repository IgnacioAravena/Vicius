package ignacioaravena.viciusgamer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.entity.StrictContentLengthStrategy;

public class Resultado extends AppCompatActivity {


    Bundle bundle;
    String busqueda;
    TextView txt_busqueda;
    ListView listado;
    ArrayList<String> datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        listado = (ListView) findViewById(R.id.listado);
        txt_busqueda=(TextView)findViewById(R.id.txt_busqueda);
        bundle= getIntent().getExtras();
        busqueda=bundle.getString("busqueda");
        txt_busqueda.setText(busqueda);
        doSearch(busqueda); //genera una nueva busqueda

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout ll = (LinearLayout) view;
                TextView t = (TextView) ll.findViewById(R.id.textId);
                Intent i = new Intent(Resultado.this, Detalle.class);
                i.putExtra("idp",t.getText());
                startActivity(i);
            }
        });
    }

    public void doSearch(String busqueda){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://www.viciusgamer.cl/product/api/search";

        RequestParams parametros = new RequestParams();
        parametros.put("q",busqueda);

        client.get(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if  (statusCode == 200) {
                    datos = obDatosJSON(new String(responseBody));
                    cargaLista(datos);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    public void cargaLista(ArrayList<String> datos){
        ArrayList<String> imageUrl = new ArrayList<String>();
        ArrayList<String> id = new ArrayList<String>();

        for(int i=0; i < datos.size(); i++){
            String[] j = datos.get(i).split(",");
            id.add(j[0]);
            imageUrl.add(j[1]);
        }

        String[] url = new String[imageUrl.size()];
        url = imageUrl.toArray(url);

        String[] idp = new String[id.size()];
        idp = id.toArray(idp);

        CustomList adapter = new CustomList(Resultado.this, url, idp );
        listado.setAdapter(adapter);
    }


    public ArrayList<String> obDatosJSON(String response){
        ArrayList<String> listado = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String data;
            for (int i=0; i<jsonArray.length(); i++){
                data = jsonArray.getJSONObject(i).getString("id")+ ",";
                data = data + jsonArray.getJSONObject(i).getString("image_s3");  // usar en dealers
                listado.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  listado;
    }



}

