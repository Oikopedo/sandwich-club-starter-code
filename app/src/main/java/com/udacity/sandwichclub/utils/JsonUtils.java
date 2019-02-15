package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Log.v(TAG,json);
        try {
            JSONObject sandwitch = new JSONObject(json);
            JSONObject name = sandwitch.getJSONObject("name");
            List<String> alsoKnownAs = getStringsFromJSONArray(name.getJSONArray("alsoKnownAs"));
            List<String> ingredients = getStringsFromJSONArray(sandwitch.getJSONArray("ingredients"));
            return new Sandwich(name.getString("mainName"),alsoKnownAs
                    ,sandwitch.getString("placeOfOrigin")
                    ,sandwitch.getString("description")
                    ,sandwitch.getString("image"),ingredients);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getStringsFromJSONArray(JSONArray array)throws JSONException{
        List<String> res =new ArrayList<>(array.length());
        for (int i=0;i<array.length();i++){
            res.add(array.getString(i));
        }
        return  res;
    }
}
