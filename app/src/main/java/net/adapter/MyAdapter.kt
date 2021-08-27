package net.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_recycler.view.*
import net.basicmodel.R
import net.entity.DataEntity
import net.utils.Constant
import net.utils.LanguageManager
import net.utils.OnclickListener

/**
 * Copyright (C) 2021,2021/8/27, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class MyAdapter(
    val data: ArrayList<DataEntity>,
    val type: String,
    val onclickListener: OnclickListener
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var map: HashMap<String, String>? = null
    var view: View? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setMap() {
        map = LanguageManager.get().getLanguage()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = View.inflate(parent.context, R.layout.layout_item_recycler, null) as View
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.itemView) {
            val from = LanguageManager.get().getCountry(map!!, data[position].from)
            val to = LanguageManager.get().getCountry(map!!, data[position].to)
            val content = "$from - $to  (${data[position].from} - ${data[position].to})"
            item_tv1.text = content
            item_tv2.text = data[position].trans_result[0].src
            item_tv3.text = data[position].trans_result[0].dst
            item_img.setOnClickListener {
                when (type) {
                    Constant.TAG_COLLECTION -> {
                        onclickListener.onItemClick(holder.layoutPosition, Constant.TAG_COLLECTION)
                    }
                    Constant.TAG_HISTORY -> {
                        onclickListener.onItemClick(holder.layoutPosition, Constant.TAG_HISTORY)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}