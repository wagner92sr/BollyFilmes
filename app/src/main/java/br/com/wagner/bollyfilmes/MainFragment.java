package br.com.wagner.bollyfilmes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private int posicaoItem = GridView.INVALID_POSITION;

    private static final String KEY_POSITION = "SELECIONADO";

    private GridView grid; //ListView grid

    private FilmesAdapter adapter;

    private boolean useFilmeDestaque = false;

    private boolean isConected = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        grid = view.findViewById(R.id.grid_filmes);//list_filmes

        final ArrayList<ItemFilme> arrayList = new ArrayList<>();

        adapter = new FilmesAdapter(getContext(), arrayList);
        adapter.setUseFilmeDestaque(useFilmeDestaque);

        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemFilme itemFilme = arrayList.get(position);
                Callback callback = (Callback) getActivity();
                callback.onItemSelected(itemFilme);
                posicaoItem = position;

            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_POSITION)) {
            posicaoItem = savedInstanceState.getInt(KEY_POSITION);
        }

        if (verificaConexao(getContext())) {
            new FilmesAsyncTask().execute();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (posicaoItem != GridView.INVALID_POSITION) {
            outState.putInt(KEY_POSITION, posicaoItem);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (posicaoItem != GridView.INVALID_POSITION && grid != null) {
            grid.smoothScrollToPosition(posicaoItem);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FilmesAsyncTask asyncTask = new FilmesAsyncTask();

        switch (item.getItemId()) {
            case R.id.menu_popularidade:
                if (verificaConexao(getContext())) {
                    asyncTask.setOrderBy("popular?");
                    Toast.makeText(getContext(), "Ordenando por Popularidade....", Toast.LENGTH_LONG).show();
                    asyncTask.execute();
                }
                return true;

            case R.id.menu_recentes:
                if (verificaConexao(getContext())) {
                    asyncTask.setOrderBy("top_rated?");
                    Toast.makeText(getContext(), "Ordenando por Melhores avaliações....", Toast.LENGTH_LONG).show();
                    asyncTask.execute();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;

        if (adapter != null) {
            adapter.setUseFilmeDestaque(useFilmeDestaque);
        }
    }

    public class FilmesAsyncTask extends AsyncTask<Void, Void, List<ItemFilme>> {

        private String orderBy = "popular?";

        public void setOrderBy(String order){
            this.orderBy = order;
        }

        @Override
        protected List<ItemFilme> doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try{
                String urlBase = "https://api.themoviedb.org/3/movie/" + orderBy;
                String apiKey = "api_key";
                String language = "language";

                Uri uriApi = Uri.parse(urlBase).buildUpon()
                        .appendQueryParameter(apiKey,BuildConfig.TMDB_API_KEY)
                        .appendQueryParameter(language,"pt-BR")
                        .build();

                URL url = new URL(uriApi.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String linha;
                StringBuffer buffer = new StringBuffer();
                while ((linha = reader.readLine()) != null){
                    buffer.append(linha);
                    buffer.append("\n");
                }

                return JsonUtil.fromJsonToList(buffer.toString());

            }catch (IOException e){
                e.printStackTrace();
                Log.e("Erro", String.valueOf(e));
            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ItemFilme> itemFilmes) {
            adapter.clear();
            adapter.addAll(itemFilmes);
            adapter.notifyDataSetChanged();

        }

    }

    public interface Callback {
        void onItemSelected(ItemFilme itemFilme);
    }

    public boolean verificaConexao(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            return ni != null && ni.isConnected();
        }

        return false;
    }


}
