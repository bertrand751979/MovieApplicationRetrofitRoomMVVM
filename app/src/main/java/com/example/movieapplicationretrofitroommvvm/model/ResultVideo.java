package com.example.movieapplicationretrofitroommvvm.model;

import java.io.Serializable;
import java.util.Date;

public class ResultVideo implements Serializable {
    public String iso_639_1;
    public String iso_3166_1;
    public String name;
    public String key;
    public Date published_at;
    public String site;
    public int size;
    public String type;
    public boolean official;
    public String id;
    public int position;

    public ResultVideo(String iso_639_1, String iso_3166_1, String name, String key, Date published_at, String site, int size, String type, boolean official, String id, int position) {
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
        this.key = key;
        this.published_at = published_at;
        this.site = site;
        this.size = size;
        this.type = type;
        this.official = official;
        this.id = id;
        this.position = position;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getPublished_at() {
        return published_at;
    }

    public void setPublished_at(Date published_at) {
        this.published_at = published_at;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
