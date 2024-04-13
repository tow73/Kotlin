package dduwcom.mobile.project.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dduwcom.mobile.project.data.WordDto
import dduwcom.mobile.project.databinding.ListItemBinding

class WordAdapter: RecyclerView.Adapter<WordAdapter.WordHolder>() {

    var wordList: List<WordDto>? = null
    var itemClickListener: OnWordItemClickListener? = null

    override fun getItemCount(): Int {
        return wordList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val itemBinding = ListItemBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        return WordHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        val dto = wordList?.get(position)
        holder.itemBinding.tvData.text = dto?.toString()
        holder.itemBinding.clItem.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }
    }

    class WordHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    interface OnWordItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnWordItemClickListener) {
        itemClickListener = listener
    }
}