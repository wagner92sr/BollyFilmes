package br.com.wagner.bollyfilmes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.list_filmes);

        ArrayList<ItemFilme> arrayList = new ArrayList<>();
        arrayList.add(new ItemFilme("Homem Aranha","Herói com poderes de aranha","10/10/2016",3.5f));
        arrayList.add(new ItemFilme("Batman","Herói bilionário que usa tecnologia para combater o crime","10/10/2016",4));
        arrayList.add(new ItemFilme("Homem de Ferro","Herói com armadura de ferro","10/10/2016",3.8f));
        arrayList.add(new ItemFilme("Capitão América","Soldado com superforça","10/10/2016",5));
        arrayList.add(new ItemFilme("Hulk","Cientista que fica forte ao sentir raiva","10/10/2016",3.5f));
        arrayList.add(new ItemFilme("Pantera Negra","Principe de tribo africana que possui reservas gigantescas de mineral raro","10/10/2016",4.8f));

        FilmesAdapter adapter = new FilmesAdapter(this,arrayList);

        list.setAdapter(adapter );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_atualizar:
                Toast.makeText(this,"Atualizando a lista de filmes....",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
