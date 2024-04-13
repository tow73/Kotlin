package ddwu.com.mobile.adaptereventtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(val foods: ArrayList<FoodDto>)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun getItemCount(): Int = foods.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FoodViewHolder(itemView)
    }
    // 원본 data를 순서에 맞게 결합
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.photo.setImageResource(foods[position].photo)
        holder.food.text = foods[position].food
        holder.count.text = foods[position].count.toString()
    }
    // 1. 화면을 담는 viewHolder 생성
    class FoodViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val photo = view.findViewById<ImageView>(R.id.ivPhoto)
        val food = view.findViewById<TextView>(R.id.tvFood)
        val count = view.findViewById<TextView>(R.id.tvCount)
    }
}