package ignacioaravena.viciusgamer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONObject;
import org.w3c.dom.Text;

public class Resultado extends AppCompatActivity {


    Bundle bundle;
    String busqueda;
    TextView txt_busqueda;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        txt_busqueda=(TextView)findViewById(R.id.txt_busqueda);
        System.out.println(txt_busqueda.getText());
        bundle= getIntent().getExtras();
        busqueda=bundle.getString("busqueda");
        System.out.println(busqueda);
        txt_busqueda.setText(busqueda);
    }



}
