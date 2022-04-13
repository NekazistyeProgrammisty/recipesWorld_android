package com.bignerdranch.android.testing.cards

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.testing.R
import com.bignerdranch.android.testing.databinding.ActivityRecipeBinding
import com.bignerdranch.android.testing.databinding.CardItemLayoutBinding
import com.bignerdranch.android.testing.databinding.RecipeItemLayoutBinding
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable


class MyCardAdapter(private val context: Context, val foodItemList:ArrayList<MyCardData>) :  RecyclerView.Adapter<MyCardAdapter.ViewHolder>()  ,Filterable   {
    var recipeFilterList :ArrayList<MyCardData>

    // exampleListFull . exampleList

    init {
        recipeFilterList = foodItemList as ArrayList<MyCardData>
    }
    class ViewHolder(foodItemLayoutBinding: CardItemLayoutBinding,
                     buttonReverse: ImageButton = foodItemLayoutBinding.buttonReverse, buttonReverseback: ImageButton = foodItemLayoutBinding.buttonReverse1) : RecyclerView.ViewHolder(foodItemLayoutBinding.root){
       public  val buttonReverse: ImageButton = foodItemLayoutBinding.buttonReverse
        public val buttonReverseback: ImageButton = foodItemLayoutBinding.buttonReverse1
        public  val front = foodItemLayoutBinding.cardRecipe
        public val back = foodItemLayoutBinding.cardBack
        public val imageRecipe = foodItemLayoutBinding.imageView
        private val binding = foodItemLayoutBinding

        fun bind(foodItem: MyCardData , count : Int){
            binding.imageView.setImageResource(foodItem.image)
            binding.textRecycler.text = foodItem.text
        //   binding.textRecycler.text = count.toString()
        }
        @SuppressLint("ResourceType")
        fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
            try {
                visibleView.setVisibility(View.VISIBLE)
                val scale = context.resources.displayMetrics.density
                val cameraDist = 8000 * scale
                visibleView.cameraDistance = cameraDist
                inVisibleView.cameraDistance = cameraDist
                val flipOutAnimatorSet =
                    AnimatorInflater.loadAnimator(
                        context,
                        R.anim.reverseback
                    ) as AnimatorSet
                flipOutAnimatorSet.setTarget(inVisibleView)
                val flipInAnimationSet =
                    AnimatorInflater.loadAnimator(
                        context,
                        R.anim.reverse
                    ) as AnimatorSet
                flipInAnimationSet.setTarget(visibleView)
                flipOutAnimatorSet.start()
                flipInAnimationSet.start()

              //  flipInAnimationSet.doOnEnd { inVisibleView.setVisibility(View.INVISIBLE)}

            } catch (e: Exception) {

            }
        }
        fun reverseFront(){
            binding.buttonReverse.setOnClickListener(){

            }
        }



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var foodItem = recipeFilterList[position]
        var shimmer = Shimmer.ColorHighlightBuilder().setBaseColor(Color.parseColor("#F3F3F3"))
            .setBaseAlpha(1F).setHighlightColor(Color.parseColor("#E7E7E7" ))
            .setHighlightAlpha(1F)
            .setDropoff(50F)
            .build()
        var shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)

        Glide.with(context).load(recipeFilterList[position].image)
            .placeholder(shimmerDrawable)
            .into(holder.imageRecipe)

       // (holder as ViewHolder).bind(foodItem,position   )
        holder.buttonReverse.setOnClickListener(){
     holder.flipCard(context,holder.front,holder.back)
        }
        holder.buttonReverseback.setOnClickListener(){
            holder.flipCard(context,holder.back,holder.front)
        }

            }

    override fun getItemCount(): Int {
       return recipeFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    recipeFilterList = foodItemList as ArrayList<MyCardData>
                } else {
                    val resultList = ArrayList<MyCardData>()
                    for (row in foodItemList) {
                        if (row.text.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    recipeFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = recipeFilterList
                recipeFilterList.forEach { println(it.toString()+ " ") }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                recipeFilterList = results?.values as ArrayList<MyCardData>
                notifyDataSetChanged()
            }
        }
    }


}