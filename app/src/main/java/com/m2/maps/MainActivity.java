package com.m2.maps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.m2.maps.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(viewBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView = viewBinding.ImageView;
        Picasso.get()
                .load("https://oyster.ignimgs.com/mediawiki/apis.ign.com/dota-2/4/4d/Dota-2-new-map.jpg")
                .resize(300,300)
                .centerCrop()
                .placeholder(R.color.black)
                .into(imageView);
        viewBinding.ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
    }
}