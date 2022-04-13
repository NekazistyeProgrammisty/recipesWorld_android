package com.bignerdranch.android.testing

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.testing.cards.MyCardAdapter
import com.bignerdranch.android.testing.cards.MyCardData
import com.bignerdranch.android.testing.databinding.ActivityRecipeBinding
import com.facebook.shimmer.ShimmerFrameLayout
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList


class RecipeActivity : AppCompatActivity() {
    private val foodItemsList: MutableList<MyCardData> = mutableListOf()
    private lateinit var adapter: MyCardAdapter
    private lateinit var b: ActivityRecipeBinding

    lateinit var constraintLayout : ConstraintLayout
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var shimerFrameLayout : ShimmerFrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


b= ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(b.root)

        constraintLayout = findViewById(R.id.constraint_recipe)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progress_bar)
        shimerFrameLayout = findViewById(R.id.shimmer_layout)

        for(i in 0 until 29) {
    var foodItem = MyCardData(i.toString(), R.drawable.bliny)
    foodItemsList.add(foodItem)
}
         /*foodItem = MyCardData("Бифстроганов", R.drawable.beefstroganoff)
        foodItemsList.add(foodItem)
        foodItem = MyCardData("Курица гриль ", R.drawable.chicken)
        foodItemsList.add(foodItem)
         foodItem = MyCardData("Окрошка", R.drawable.okroshka)
        foodItemsList.add(foodItem)
         foodItem = MyCardData("Шницель из куриного филе", R.drawable.scnicel)
        foodItemsList.add(foodItem)
         foodItem = MyCardData("Кутабы с зеленью и сыром", R.drawable.kutab)
        foodItemsList.add(foodItem)
         foodItem = MyCardData("Мясные кутабы", R.drawable.kutabmeat)
        foodItemsList.add(foodItem)
         foodItem = MyCardData("Жареный сэндвич с ветчиной и сыром", R.drawable.hotsandwich)
        foodItemsList.add(foodItem)
         foodItem = MyCardData("Гаспачо", R.drawable.gaspacho)
        foodItemsList.add(foodItem)
         foodItem = MyCardData("Уха из речной рыбы, с картофелем", R.drawable.uxa)
        foodItemsList.add(foodItem)
*/


        var feedButton : ImageButton = findViewById(R.id.feedButton)
        feedButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }
        setUpAdapter()

        shimerFrameLayout.startShimmer()


        var filteredList :MutableList<MyCardData> = mutableListOf()
        val searchView = b.searchRecipe

        b.searchRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
               return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("onQueryTextChange", "query: " + p0)
                adapter?.filter?.filter(p0)
                return true
            }

        })

    }



    private fun setUpAdapter() {
        adapter= MyCardAdapter(this, foodItemsList as ArrayList<MyCardData>)
       b.apply {

           recyclerView.layoutManager= GridLayoutManager(this@RecipeActivity, 2)
           recyclerView.adapter=adapter

       }

    }







}