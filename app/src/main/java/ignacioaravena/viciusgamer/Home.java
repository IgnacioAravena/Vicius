package ignacioaravena.viciusgamer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button btn_buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_buscar=(Button)findViewById(R.id.btn_buscar);
    }

    public void Buscar(View view){
        Intent i = new Intent(this,Resultado.class);
        startActivity(i);
    }

    public void hola (View view){
        System.out.println("jxfkjsabjkfbasjkbfkjasbfka");
    }
}
