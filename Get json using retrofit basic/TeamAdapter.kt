package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_team.view.*

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.ItemHolder> {

    var list: ArrayList<Team>

    constructor(list: ArrayList<Team>) : super() {
        this.list = list
    }


    class ItemHolder : RecyclerView.ViewHolder {

        var textView_site_key: TextView
        var textView_site_nice: TextView

        constructor(itemView: View) : super(itemView) {

            textView_site_key = itemView.textView_site_key
            textView_site_nice = itemView.textView_site_nice

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.row_team, parent, false)
        var itemHolder = ItemHolder(v)

        return itemHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = list.get(holder.adapterPosition)
        holder.textView_site_key.text = item.site_key
        holder.textView_site_nice.text = item.site_nice
    }
}