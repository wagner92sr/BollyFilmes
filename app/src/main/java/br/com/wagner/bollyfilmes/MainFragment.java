package br.com.wagner.bollyfilmes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        ListView list = view.findViewById(R.id.list_filmes);

        final ArrayList<ItemFilme> arrayList = new ArrayList<>();
        arrayList.add(new ItemFilme("Homem Aranha","Herói com poderes de aranha","10/10/2016",3.5f));
        arrayList.add(new ItemFilme("Batman","Herói bilionário que usa tecnologia para combater o crime","10/10/2016",4));
        arrayList.add(new ItemFilme("Homem de Ferro","Herói com armadura de ferro","10/10/2016",3.8f));
        arrayList.add(new ItemFilme("Capitão América","Soldado com superforça","10/10/2016",5));
        arrayList.add(new ItemFilme("Hulk","Cientista que fica forte ao sentir raiva","10/10/2016",3.5f));
        arrayList.add(new ItemFilme("Pantera Negra","Principe de tribo africana que possui reservas gigantescas de mineral raro","10/10/2016",4.8f));

        FilmesAdapter adapter = new FilmesAdapter(getContext(),arrayList);

        list.setAdapter(adapter );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemFilme itemFilme = arrayList.get(position);

                Intent intent = new Intent(getContext(), FilmeDetalheActivity.class);
                intent.putExtra(MainActivity.KEY_FILME, itemFilme);
                startActivity(intent);


            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_atualizar:
                Toast.makeText(getContext(),"Atualizando a lista de filmes....",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
