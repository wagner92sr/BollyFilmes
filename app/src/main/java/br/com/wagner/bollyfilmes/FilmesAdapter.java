package br.com.wagner.bollyfilmes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class FilmesAdapter extends ArrayAdapter<ItemFilme> {

    private static final int VIEW_TYPE_DESTAQUE = 0;

    private static final int VIEW_TYPE_ITEM = 1;

    private boolean useFilmeDestaque = false;

    public FilmesAdapter(Context context, ArrayList<ItemFilme> filmes){
        super(context,0,filmes);
    }

    public static class ItemFilmeHolder{
        TextView titulo;
        TextView descricao;
        TextView dataLancamento;
        RatingBar avaliacao;
        ImageView poster;

        public ItemFilmeHolder(View view){
            titulo = view.findViewById(R.id.item_titulo);
            descricao = view.findViewById(R.id.item_desc);
            dataLancamento = view.findViewById(R.id.item_data);
            avaliacao = view.findViewById(R.id.item_avaliacao);
            poster = view.findViewById(R.id.item_poster);
        }

    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        int viewType = getItemViewType(position);
        ItemFilme filme = getItem(position);
        View itemView = convertView;

       /* switch (viewType){
            case VIEW_TYPE_DESTAQUE: {

                itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_filme_destaque, parent, false);

                TextView titulo = itemView.findViewById(R.id.item_titulo);
                titulo.setText(filme.getTitulo());

                RatingBar avaliacao = itemView.findViewById(R.id.item_avaliacao);
                avaliacao.setRating(filme.getAvaliacao());

                ImageView capa = (ImageView) itemView.findViewById(R.id.item_capa);
                Picasso.get()
                        .load(filme.getCapaPath())
                        .placeholder(R.drawable.capa)
                        .resize(300,300)
                        .centerInside()
                        .into(capa);
               // new DownloadImageTask(capa).execute(filme.getCapaPath());

                break;

            }
            case VIEW_TYPE_ITEM: { */

                itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_filme, parent, false);

                ItemFilmeHolder holder;

                if (itemView.getTag() == null){
                    holder = new ItemFilmeHolder(itemView);
                    itemView.setTag(holder);
                }else{
                    holder = (ItemFilmeHolder) itemView.getTag();
                }

        //holder.titulo.setText(filme.getTitulo());
        // holder.descricao.setText(filme.getDescricao());
        // holder.dataLancamento.setText(filme.getDataLancamento());
        // holder.avaliacao.setRating(filme.getAvaliacao());

                Picasso.get()
                        .load(filme.getCapaPath())
                        .placeholder(R.drawable.capa)
                        .resize(1000, 1000)
                        .centerInside()
                        .into(holder.poster);

                //new DownloadImageTask(holder.poster).execute(filme.getPosterPath());

        // break;

        // }
        //  }

        return itemView;

    }

    @Override
    public int getItemViewType(int position) {
        //return (position == 0 ? VIEW_TYPE_DESTAQUE: VIEW_TYPE_ITEM);
        return (position == 0 && useFilmeDestaque ? VIEW_TYPE_DESTAQUE : VIEW_TYPE_ITEM);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;
    }
}
