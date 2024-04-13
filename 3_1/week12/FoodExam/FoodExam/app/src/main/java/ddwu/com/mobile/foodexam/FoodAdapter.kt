package ddwu.com.mobile.foodexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.foodexam.databinding.ListItemBinding

class FoodAdapter(val foods: ArrayList<FoodDto>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun getItemCount() = foods.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.tvFood.text = foods[position].toString()
    }

    class FoodViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
}