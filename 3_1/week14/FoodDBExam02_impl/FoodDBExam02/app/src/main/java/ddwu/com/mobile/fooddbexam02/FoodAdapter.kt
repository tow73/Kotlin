package ddwu.com.mobile.fooddbexam02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.fooddbexam02.data.FoodDto
import ddwu.com.mobile.fooddbexam02.databinding.ListItemBinding

class FoodAdapter (val foods : ArrayList<FoodDto>)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    val TAG = "FoodAdapter"

    /*재정의 필수 - 데이터의 개수 확인이 필요할 때 호출*/
    override fun getItemCount(): Int = foods.size

    /*재정의 필수 - 각 item view 의 view holder 생성 시 호출*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        /*바인딩 객체 및 사용자 정의 외부 리스너 전달하여 ViewHolder 생성 후 반환*/
        return FoodViewHolder(itemBinding, listener, lcListener)
    }

    /*재정의 필수 - 각 item view 의 항목에 데이터 결합 시 호출*/
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemBinding.tvText.text = foods[position].toString()
    }


    /*화면의 View 를 바인딩 형태로 보관하는 ViewHolder 클래스*/
    class FoodViewHolder(val itemBinding: ListItemBinding,
                         listener: OnItemClickListener?,
                         lcListener: OnItemLongClickListener?)
        : RecyclerView.ViewHolder(itemBinding.root) {
            init {
                /*list_item 의 root 항목(ConstraintLayout) 클릭 시*/
                itemBinding.root.setOnClickListener{
                    listener?.onItemClick(it, adapterPosition)  // adapterPosition 은 클릭 위치 index
                }

                /*list_item 의 root 항목(ConstraintLayout) 롱클릭 시*/
                itemBinding.root.setOnLongClickListener{
                    lcListener?.onItemLongClick(it, adapterPosition)
                    true
                }

            }
        }


    /*사용자 정의 외부 롱클릭 이벤트 리스너 정의 부분*/
    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    var lcListener : OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.lcListener = listener
    }


    /*사용자 정의 외부 클릭 이벤트 리스너 정의 부분*/
    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    var listener : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}



