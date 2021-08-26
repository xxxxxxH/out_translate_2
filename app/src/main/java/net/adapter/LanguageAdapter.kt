package net.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_language.view.*
import net.basicmodel.R
import net.utils.OnclickListener

class LanguageAdapter(val data: ArrayList<String>,val listener: OnclickListener) :
    RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.layout_item_language, null) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            item_language.text = data[position]
            item_language.setOnClickListener {
                listener.onItemClick(holder.layoutPosition,"")
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}