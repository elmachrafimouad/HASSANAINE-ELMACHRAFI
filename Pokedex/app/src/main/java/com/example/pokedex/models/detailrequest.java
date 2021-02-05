package com.example.pokedex.models;

import android.telephony.mbms.StreamingServiceInfo;

import java.util.List;
import java.util.List;

public class detailrequest {

    public List<detail> types;
    public int height;
    public int weight;

    public detailrequest() {
    }

    public detailrequest(List<detail> types, int height, int weight) {
        this.types = types;
        this.height = height;
        this.weight = weight;
    }

    public List<detail> getTypes() {
        return types;
    }

    public void setTypes(List<detail> types) {
        this.types = types;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

