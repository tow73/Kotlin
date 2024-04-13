package ddwu.com.mobile.adaptereventtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context, val layout: Int, val list: ArrayList<String>)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =LayoutInflater.from(context).inflate(layout, parent, false)
        return MyViewHolder(this, view, list)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.tvText.text = list[position]
    }

    class MyViewHolder(adapter: MyAdapter, view: View, dataList: ArrayList<String>): RecyclerView.ViewHolder(view) {
        val tvText =view.findViewById<TextView>(R.id.tvText)

        init {
            view.setOnClickListener {
                Toast.makeText(view.context, "${dataList[adapterPosition]}",Toast.LENGTH_SHORT).show()
                dataList.removeAt(adapterPosition)  //원본 data index 순서 바뀜 하지만 화면은 그대로 -> 오류로 앱 종료 -> 화면 갱신 필요
                adapter.notifyDataSetChanged()
            }
        }
    }
}