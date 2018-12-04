package br.com.wagner.bollyfilmes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.List;

public class MainFragment extends Fragment {

    private int posicaoItem = ListView.INVALID_POSITION;

    private static final String KEY_POSITION = "SELECIONADO";

    private ListView list ;

    private FilmesAdapter adapter;

    private boolean useFilmeDestaque = false;

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

        list = view.findViewById(R.id.list_filmes);

        final ArrayList<ItemFilme> arrayList = new ArrayList<>();
        arrayList.add(new ItemFilme("Homem Aranha","Herói com poderes de aranha","10/10/2016",3.5f));
        arrayList.add(new ItemFilme("Batman","Herói bilionário que usa tecnologia para combater o crime","10/10/2016",4));
        arrayList.add(new ItemFilme("Homem de Ferro","Herói com armadura de ferro","10/10/2016",3.8f));
        arrayList.add(new ItemFilme("Capitão América","Soldado com superforça","10/10/2016",5));
        arrayList.add(new ItemFilme("Hulk","Cientista que fica forte ao sentir raiva","10/10/2016",3.5f));
        arrayList.add(new ItemFilme("Pantera Negra","Principe de tribo africana que possui reservas gigantescas de mineral raro","10/10/2016",4.8f));

        adapter = new FilmesAdapter(getContext(),arrayList);
        adapter.setUseFilmeDestaque(useFilmeDestaque);

        list.setAdapter(adapter );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemFilme itemFilme = arrayList.get(position);
                Callback callback = (Callback) getActivity();
                callback.onItemSelected(itemFilme);
                posicaoItem = position;

            }
    });

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_POSITION)){
            posicaoItem = savedInstanceState.getInt(KEY_POSITION);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(posicaoItem != ListView.INVALID_POSITION){
            outState.putInt(KEY_POSITION, posicaoItem);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(posicaoItem != ListView.INVALID_POSITION && list != null ){
            list.smoothScrollToPosition(posicaoItem);
        }
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

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;

        if(adapter != null){
            adapter.setUseFilmeDestaque(useFilmeDestaque);
        }
    }

    public interface  Callback {
        void onItemSelected(ItemFilme itemFilme);
    }

}
