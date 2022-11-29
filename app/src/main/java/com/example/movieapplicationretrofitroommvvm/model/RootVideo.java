package com.example.movieapplicationretrofitroommvvm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RootVideo implements Serializable {
    public int id;
    public ArrayList<ResultVideo> results;

    public RootVideo(int id, ArrayList<ResultVideo> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ResultVideo> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultVideo> results) {
        this.results = results;
    }
}
