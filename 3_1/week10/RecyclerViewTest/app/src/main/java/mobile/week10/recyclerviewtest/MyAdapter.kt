
package mobile.week10.recyclerviewtest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context, val layout: Int, val list: ArrayList<String>)
    : RecyclerView.Adapter <MyAdapter.MyViewHolder>() {

    val TAG = "MyAdapter"

    override fun getItemCount(): Int {  //1. 화면(칸)을 몇 개 만들 것인가
        return list.size //화면 개수=원본 데이터 개수
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {  //2. 화면 하나 보관 //data 많더라도 일부만 생성해 화면 바뀔 때 대체
        val view = LayoutInflater.from(context).inflate(layout, parent, false)  //한 칸에 해당하는 뷰 생성
//        Log.d(TAG, "create!!!")  //일부만 생성하고 더이상 생성하지 않음
        return MyViewHolder(view)  //MyViewHoler에 보관
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {  //4. 연결
        holder.textView.text = list[position]
//        Log.d(TAG, "bind!!!")  //일부 생성 후 다 대체
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {  //3. viewHolder에 한 칸에 대한 textView를 찾아 보관. 화면 보관
        val textView = view.findViewById<TextView>(R.id.textView)  //findViewById 한 번만 호출
    }

}