package com.tuwaiq.husam.todolistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.husam.todolistapp.data.Repo
import com.tuwaiq.husam.todolistapp.data.model.Task
import com.tuwaiq.husam.todolistapp.ui.main.TaskFragment
import java.util.*

class TaskRecyclerAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox_Item)
        val textViewTask: TextView = itemView.findViewById(R.id.txtViewTitle_RV)
        val dateTextView: TextView = itemView.findViewById(R.id.txtViewEndDate_RV)
        val textViewEndDate: TextView = itemView.findViewById(R.id.txtView2_RV)
        val horizontalLine: Guideline = itemView.findViewById(R.id.guidelineH1_Item)
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
//        holder.horizontalLine.setGuidelinePercent(1.00F)
        if (task.endDate.isNotEmpty()) {
            holder.dateTextView.text = task.endDate
            task.completed = getCalenderResult(task.endDate, holder.itemView)
//            holder.horizontalLine.setGuidelinePercent(0.50F)
        }
//            holder.dateTextView.visibility = View.VISIBLE
//            holder.textViewEndDate.visibility = View.VISIBLE
//        } else {
//            holder.dateTextView.visibility = View.GONE
//            holder.textViewEndDate.visibility = View.GONE
//        }
        changeBackgroundColor(holder, task.checked,task.completed)
        holder.checkBox.setOnClickListener {
            val checked = holder.checkBox.isChecked
            task.checked = checked
            changeBackgroundColor(holder, task.checked,task.completed)
            updateList(task, position)
        }
        holder.itemView.setOnClickListener { view ->
            val activity: AppCompatActivity = view.context as AppCompatActivity
            val bundle = Bundle()
            val fragment = TaskFragment.newInstance()
            fragment.arguments = bundle
            bundle.putParcelable("taskKey", task)
            bundle.putInt("position", position)
            activity.supportFragmentManager.beginTransaction()
                .addToBackStack("MoveToTask")
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    private fun changeBackgroundColor(
        holder: TaskViewHolder,
        checked: Boolean,
        completed: Boolean
    ) {
        when {
            completed -> holder.mainConLayout.setBackgroundColor(
                holder.itemView.resources.getColor(
                    R.color.light_pink,
                    null
                )
            )
            checked -> holder.mainConLayout.setBackgroundColor(
                holder.itemView.resources.getColor(
                    R.color.purple_700,
                    null
                )
            )
            else -> {
                holder.mainConLayout.setBackgroundColor(
                    holder.itemView.resources.getColor(
                        R.color.light_blue,
                        null
                    )
                )
            }
        }
    }

    private fun getCalenderResult(endDate: String, itemView: View): Boolean {
        val list = endDate.split("/")
        val day: Int = list[0].toInt()
        val month: Int = list[1].toInt()
        val year: Int = list[2].toInt()
        val calEndDate = Calendar.getInstance()
        calEndDate.set(year, month, day)
        val currentDate = Calendar.getInstance()
        return calEndDate.compareTo(currentDate) < 0
    }

    override fun getItemCount(): Int = taskList.size

    private fun updateList(task: Task, position: Int) {
        Repo.updateTaskOnList(task, position)
        notifyDataSetChanged()
    }
}