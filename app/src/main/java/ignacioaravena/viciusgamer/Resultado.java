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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedMap;

import cz.msebera.android.httpclient.Header;

public class Resultado extends AppCompatActivity {


    Bundle bundle;
    String busqueda;
    TextView txt_busqueda;
    ListView listado;
//    ListViewAdapter adapter;


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

//        adapter = new ListViewAdapter(this, id);
//        lista.setAdapter(adapter);
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();
                String idp = item.split("-")[0];
//                Toast.makeText(getBaseContext(), idp, Toast.LENGTH_LONG).show();
                Intent i = new Intent(Resultado.this, Detalle.class);
                i.putExtra("idp",idp);
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
                    cargaLista(obDatosJSON(new String(responseBody)));

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    public void cargaLista(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        listado.setAdapter(adapter);
    }


    public ArrayList<String> obDatosJSON(String response){
        ArrayList<String> listado = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String data;
            String detail;
            for (int i=0; i<jsonArray.length(); i++){
                data = jsonArray.getJSONObject(i).getString("id")+ " - ";
                data = data + jsonArray.getJSONObject(i).getString("platform")+ " - ";
                detail = jsonArray.getJSONObject(i).getString("detail");  // usar en dealers
                JSONArray ddata = new JSONArray("["+detail+"]");
                String detailname = ddata.getJSONObject(0).getString("name");
                data = data + detailname;
                listado.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  listado;
    }



}

