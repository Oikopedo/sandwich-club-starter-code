package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        TextView  originTv =findViewById(R.id.origin_tv);
        TextView  descriptionTv = findViewById(R.id.description_tv);
        TextView  ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);

        TextView origin = findViewById(R.id.origin);
        TextView  description = findViewById(R.id.description);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        decideVisibility(origin,originTv,sandwich.getPlaceOfOrigin());
        decideVisibility(description,descriptionTv,sandwich.getDescription());
        descriptionTv.setText(sandwich.getDescription());
        ingredientsTv.setText(listStringToString(sandwich.getIngredients()));
        alsoKnownTv.setText(listStringToString(sandwich.getAlsoKnownAs()));
        //Log.v("aa",sandwich.getPlaceOfOrigin());

        setTitle(sandwich.getMainName());
    }

    private String listStringToString(List<String> list) {
        String res="";
        for (String s:list){
            res+=s+"\n";
        }
        return res;
    }

    private void decideVisibility(TextView text,TextView fromSandwitch,String s){
        if (s.contentEquals("")){
            text.setVisibility(View.INVISIBLE);
            fromSandwitch.setVisibility(View.INVISIBLE);
        }else{
            text.setVisibility(View.VISIBLE);
            fromSandwitch.setVisibility(View.VISIBLE);
            fromSandwitch.setText(s);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
