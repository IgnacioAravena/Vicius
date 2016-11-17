package ignacioaravena.viciusgamer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Home extends AppCompatActivity {

    Button btn_buscar;
    EditText search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        search=(EditText)findViewById(R.id.search);
        btn_buscar=(Button)findViewById(R.id.btn_buscar);
    }

    public void buscar(View view){

        System.out.println("click boton buscar");
        String busqueda;
        //TODO: validar variable vacia
        busqueda = search.getText().toString();
        System.out.println("busqueda:"+ busqueda);

        Intent i = new Intent(this,Resultado.class);
        i.putExtra("busqueda",busqueda);
        startActivity(i);


    }

    public void hola (View view){
        System.out.println("jxfkjsabjkfbasjkbfkjasbfka");
    }
}
