package org.benedetto.imageranking;

import com.google.gson.Gson;

public class TestUtil {

    public static void gsonPrint(Object o) {
        System.err.println(new Gson().toJson(o));
    }

}
