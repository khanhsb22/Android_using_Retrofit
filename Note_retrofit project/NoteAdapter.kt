package com.example.note_retrofit_demo

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_note.view.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ItemHolder>{
    var context: Context
    var list: ArrayList<Note>

    companion object {
        var clickListener: ClickListener? = null
    }

    constructor(context: Context, list: ArrayList<Note>) : super() {
        this.context = context
        this.list = list
    }

    class ItemHolder : RecyclerView.ViewHolder, View.OnClickListener, View.OnLongClickListener {

        var textView_title: TextView
        var textView_content: TextView

        constructor(itemView: View) : super(itemView) {
            textView_title = itemView.textView_title
            textView_content = itemView.textView_content

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View) {
            clickListener?.onItemClick(adapterPosition, v)

        }

        override fun onLongClick(v: View): Boolean {
            clickListener?.onItemLongClick(adapterPosition, v)
            return false
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.row_note, parent, false)
        var itemHolder = ItemHolder(v)

        return itemHolder
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = list[holder.adapterPosition]
        holder.textView_title.text = item.title
        holder.textView_content.text = item.content
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View)
        fun onItemLongClick(position: Int, v: View)
    }

    fun setOnItemClickListener(clickListener: ClickListener?) {
        NoteAdapter.clickListener = clickListener!!
    }
}