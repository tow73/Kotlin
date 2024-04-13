package ddwu.com.mobile.adaptereventtest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.adaptereventtest.databinding.ListItemBinding

class BindingFoodAdapter(val foods: ArrayList<FoodDto>)
    : RecyclerView.Adapter<BindingFoodAdapter.FoodViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
    lateinit var listener : OnItemClickListener
    fun setOnItemClickListener (listener: OnItemClickListener) {
        this.listener = listener //setter 생성
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }
    lateinit var longListener: OnItemLongClickListener
    fun setOnItemLongClickListener (longListener: OnItemLongClickListener) {
        this.longListener = longListener
    }

    override fun getItemCount(): Int = foods.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemBinding =ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(itemBinding, listener, longListener) //viewHolder에 리스너 전달해 viewHolder가 listener 수행
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemBinding.ivPhoto.setImageResource(foods[position].photo)
        holder.itemBinding.tvFood.text = foods[position].food
        holder.itemBinding.tvCount.text = foods[position].count.toString()
    }
    class FoodViewHolder(val itemBinding: ListItemBinding, listener: OnItemClickListener, longListener: OnItemLongClickListener)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            val TAG = "ViewHolder"
            itemBinding.root.setOnClickListener {
//                Log.d(TAG, "${adapterPosition}click!!!")
                listener.onItemClick(itemBinding.root, adapterPosition)
            }
            itemBinding.root.setOnLongClickListener {
                longListener.onItemLongClick(itemBinding.root, adapterPosition)
                true// longClickListener 반환값 자체가 false or true
            }
        }
    }
}