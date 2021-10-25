package com.tuwaiq.husam.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.husam.todolistapp.data.Task

class TaskRecyclerAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.textViewTask.text = task.TaskTitle
    }

    override fun getItemCount(): Int = taskList.size

}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewTask: TextView = itemView.findViewById(R.id.txtView1_RV)
}