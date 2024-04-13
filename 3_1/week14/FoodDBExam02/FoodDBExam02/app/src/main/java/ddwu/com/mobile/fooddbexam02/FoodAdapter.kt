package ddwu.com.mobile.fooddbexam02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.fooddbexam02.data.FoodDto
import ddwu.com.mobile.fooddbexam02.databinding.ListItemBinding

class FoodAdapter (val foods : ArrayList<FoodDto>)  // 원본 data 생성자에 넣기
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    val TAG = "FoodAdapter"

    /*재정의 필수 - 데이터의 개수 확인이 필요할 때 호출*/
    override fun getItemCount(): Int = foods.size

    /*재정의 필수 - 각 item view 의 view holder 생성 시 호출*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {  // 리스트 화면 하나(한 칸) 생성
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(itemBinding, listener, lcListener)
    }

    /*재정의 필수 - 각 item view 의 항목에 데이터 결합 시 호출*/
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {  // 화면에 데이터 결합, 문자열로 만들어 화면에 보여줌
        holder.itemBinding.tvText.text = foods[position].toString()
    }


    class FoodViewHolder(val itemBinding: ListItemBinding, listener: OnItemClickListener?, lcListener: OnItemLongClickListener?)  // ViewHolder: 한 칸에 대한 모양을 갖고 있는 class, 화면과 실체 객체 연결
        : RecyclerView.ViewHolder(itemBinding.root) {
            init {
                /*RecyclerView 항목 클릭 시 외부 click 이벤트 리스너 호출*/
                itemBinding.root.setOnClickListener{  // 클릭 하는 기능만 호출, MainActivity의 onItemClick 호출
//                    Toast.makeText(itemBinding.root.context, "Click $adapterPosition", Toast.LENGTH_SHORT).show()  // 원본 data 몇 번째 클릭했는지 확인
                    listener?.onItemClick(it, adapterPosition)  // RecyclerView 항목 클릭 시 외부(Main)에서 지정한 리스너 호출, 클릭하는 순간 실행
                }
                itemBinding.root.setOnLongClickListener{
                    lcListener?.onItemLongClick(it, adapterPosition)
                    true
                }
            }
        }


    /*사용자 정의 외부 click 이벤트 리스너 설정 */
    var listener : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null, adapter 클릭시 사용

    interface OnItemClickListener {  // MainActivity의 onItemClick에 전달할 것
        fun onItemClick(view : View, position : Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {  // 위에서 전달받아 listener에 저장
        this.listener = listener
    }

    /*사용자 정의 외부 longClick 이벤트 리스너 설정 */
    var lcListener : OnItemLongClickListener? = null  // adapter 클릭시 사용

    interface OnItemLongClickListener {  // MainActivity의 onItemLongClick에 전달할 것
        fun onItemLongClick(view : View, position : Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.lcListener = listener
    }
}



