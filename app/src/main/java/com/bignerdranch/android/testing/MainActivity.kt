package com.bignerdranch.android.testing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        var getRecipeButton : ImageButton = findViewById(R.id.getRecipeButton)
        getRecipeButton.setOnClickListener() {
            val intent = Intent(this@MainActivity, RecipeActivity::class.java)
            startActivity(intent)
        }

    }

}