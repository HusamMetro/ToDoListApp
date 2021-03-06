package com.tuwaiq.husam.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.husam.todolistapp.data.model.Task
import com.tuwaiq.husam.todolistapp.ui.main.MainFragmentDirections
import com.tuwaiq.husam.todolistapp.ui.main.MainViewModel
import java.util.*

class TaskRecyclerAdapter(
    private val taskList: List<Task>,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox_Item)
        val textViewTask: TextView = itemView.findViewById(R.id.txtViewTitle_RV)
        val dateTextView: TextView = itemView.findViewById(R.id.txtViewEndDate_RV)
        val mainConLayout: ConstraintLayout = itemView.findViewById(R.id.mainConstraintLayout_Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.textViewTask.text = task.title
        holder.checkBox.isChecked = task.checked
        if (task.endDate.isNotEmpty()) {
            holder.dateTextView.text = task.endDate
            task.pastDueDate = getCalenderResult(task.endDate)
        }
        changeBackgroundColor(holder, task.checked, task.pastDueDate)
        holder.checkBox.setOnClickListener {
            val checked = holder.checkBox.isChecked
            task.checked = checked
            changeBackgroundColor(holder, task.checked, task.pastDueDate)
            updateList(task, position, mainViewModel)
        }
        holder.itemView.setOnClickListener { view ->
            val action: NavDirections =
                MainFragmentDirections.actionMainFragmentToTaskFragment(task)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = taskList.size

    private fun changeBackgroundColor(
        holder: TaskViewHolder,
        checked: Boolean,
        pastDeoDate: Boolean
    ) = when {
        /*pastDeoDate -> holder.mainConLayout.setBackgroundColor(
            holder.itemView.resources.getColor(
                R.color.light_pink,
                null
            )
        )*/
        pastDeoDate -> holder.mainConLayout.setBackgroundResource(R.drawable.shape_red)
        checked -> holder.mainConLayout.setBackgroundResource(R.drawable.shape_green)
        else -> {
            holder.mainConLayout.setBackgroundResource(R.drawable.shape_blue)
        }
    }


    private fun getCalenderResult(endDate: String): Boolean {
        val list = endDate.split("/")
        val day: Int = list[0].toInt()
        val month: Int = (list[1].toInt()) - 1
        val year: Int = list[2].toInt()
        val currentDate = Calendar.getInstance()
        val calEndDate = Calendar.getInstance()
        calEndDate.set(year, month, day)
        return calEndDate < currentDate
    }

    private fun updateList(task: Task, position: Int, mainViewModel: MainViewModel) {
        mainViewModel.updateTaskOnList(task)
        notifyItemChanged(position)
    }
}