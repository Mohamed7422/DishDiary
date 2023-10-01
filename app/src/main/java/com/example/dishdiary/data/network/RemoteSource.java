package com.example.dishdiary.data.network;

public interface RemoteSource {
    //make retrofit builder
    void makeRandomMealsCall(NetworkDelegate callback);
}
