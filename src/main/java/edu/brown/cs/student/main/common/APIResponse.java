package edu.brown.cs.student.main.common;

import com.squareup.moshi.Moshi;

public interface APIResponse {
    default String serialize() {
        Moshi moshi = new Moshi.Builder().build();
<<<<<<< HEAD

        return moshi.adapter(this.getClass()).toJson(null);
=======
        return moshi.adapter(APIResponse.class).toJson(this);
>>>>>>> a81e47ceb9f706cc51a261a7ac6cef0782935f27
    }
}