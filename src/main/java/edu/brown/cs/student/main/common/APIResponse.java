package edu.brown.cs.student.main.common;

import com.squareup.moshi.Moshi;

public interface APIResponse {
    public default String serialize() {
        Moshi moshi = new Moshi.Builder().build();

        return moshi.adapter(this.getClass()).toJson(null);
    }
}