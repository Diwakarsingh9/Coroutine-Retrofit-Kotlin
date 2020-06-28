package com.kotlin.todolist.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.todolist.R
import com.kotlin.todolist.model.Todos
import kotlinx.android.synthetic.main.layout_parent.view.*

class TodosAdapter(
    private val list: List<Todos>?
) :
    RecyclerView.Adapter<TodosAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TodoViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_parent, parent, false)
        )


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val itemtodo = list!![position]
        holder.bind(itemtodo)
    }

    override fun getItemCount() = list?.size ?: 0


    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemtodo: Todos) {
            itemView.title?.text = itemtodo.title?.capitalize()
            itemView.completestatus?.text =
                itemView.completestatus.context.resources.getString(R.string.completed_status) + " " + itemtodo.completed?.toString()
                    ?.toUpperCase()
            itemView.idText?.text = itemtodo.id?.toString()
        }
    }


}