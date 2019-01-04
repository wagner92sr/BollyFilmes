package br.com.wagner.bollyfilmes;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/*
{
adult: false,
backdrop_path: "/87hTDiay2N2qWyX4Ds7ybXi9h8I.jpg",
belongs_to_collection: null,
budget: 63000000,
genres: [],
homepage: "http://www.foxmovies.com/movies/fight-club",
id: 550,
imdb_id: "tt0137523",
original_language: "en",
original_title: "Fight Club",
overview: "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground "fight clubs" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
popularity: 23.96,
poster_path: "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg",
production_companies: [],
production_countries: [],
release_date: "1999-10-15",
revenue: 100853753,
runtime: 139,
spoken_languages: [],
status: "Released",
tagline: "Mischief. Mayhem. Soap.",
title: "Fight Club",
video: false,
vote_average: 8.4,
vote_count: 14524
}

 */

public class ItemFilme implements Serializable {

    private long id;

    private String titulo;

    private String descricao;

    private String dataLancamento;

    private String posterPath;

    private String capaPath;

    private float avaliacao;

    public ItemFilme(long id, String titulo, String descricao, String dataLancamento, String posterPath, String capaPath, float avaliacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.posterPath = posterPath;
        this.capaPath = capaPath;
        this.avaliacao = avaliacao;
    }

    public ItemFilme(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getLong("id");
        this.titulo = jsonObject.getString("title");
        this.descricao = jsonObject.getString("overview");
        this.dataLancamento = jsonObject.getString("release_date");
        this.posterPath = jsonObject.getString("poster_path");
        this.capaPath = jsonObject.getString("backdrop_path");
        this.avaliacao = (float) jsonObject.getDouble("vote_average");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getCapaPath() {
        return capaPath;
    }

    public void setCapaPath(String capaPath) {
        this.capaPath = capaPath;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }
}
