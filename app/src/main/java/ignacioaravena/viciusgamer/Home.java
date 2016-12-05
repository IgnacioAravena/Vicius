package ignacioaravena.viciusgamer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    Button btn_buscar;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        search = (EditText) findViewById(R.id.search);
        btn_buscar = (Button) findViewById(R.id.btn_buscar);
    }

    public void buscar(View view) {

        if (!search.getText().toString().equals("")) {
            String busqueda;
            busqueda = search.getText().toString();
            Intent i = new Intent(this, Resultado.class);
            i.putExtra("busqueda", busqueda);
            startActivity(i);
       }else {
            String msg = "Eehh!!.... quieres buscar nada?";
            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        }

    }
}



