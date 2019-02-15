package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        TextView  origin =findViewById(R.id.origin_tv);
        TextView  description = findViewById(R.id.description_tv);
        TextView  ingredients = findViewById(R.id.ingredients_tv);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);

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
        origin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
        ingredients.setText(listStringToString(sandwich.getIngredients()));
        alsoKnownAs.setText(listStringToString(sandwich.getAlsoKnownAs()));
        //Log.v("aa",sandwich.getPlaceOfOrigin());

        setTitle(sandwich.getMainName());
    }

    private String listStringToString(List<String> list) {
        String res="\n";
        for (String s:list){
            res+=s+"\n";
        }
        return res;
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
