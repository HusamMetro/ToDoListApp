package com.tuwaiq.husam.todolistapp.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tuwaiq.husam.todolistapp.R
import com.tuwaiq.husam.todolistapp.data.model.Task
import java.util.*

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private lateinit var viewModel: TaskViewModel
    private lateinit var editTitleView: EditText
    private lateinit var dateTextView: TextView
    private lateinit var btnSend: Button
    private lateinit var btnCancel: Button
    private lateinit var btnDelete: Button
    private var startDate: String = ""
    private var endDate: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        editTitleView = view.findViewById(R.id.editTitle1_Task)
        btnSend = view.findViewById(R.id.btnSave_Task)
        btnCancel = view.findViewById(R.id.btnCancel_Task)
        btnDelete = view.findViewById(R.id.btnDelete_Task)
        dateTextView = view.findViewById(R.id.txtViewEndDate_Task)

        if (arguments?.size() == null) {
            dateTextView.setOnClickListener {
                getDateFromDatePickerDialog(it)
            }
            btnSend.setOnClickListener {
                sendButtonFunction(it)
            }
        } else {
            updateUI(view)
        }
        btnCancel.setOnClickListener {
            cancelButtonFunction(it)
        }

    }

    private fun getDateFromDatePickerDialog(view: View) {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        startDate = "$day/$month/$year"
        val datePD = DatePickerDialog(view.context, { _, y, m, d ->
            endDate = "$d/$m/$y"
            dateTextView.text = endDate
        }, year, month, day)
        cal.add(Calendar.DAY_OF_MONTH, 1)
        datePD.datePicker.minDate = cal.timeInMillis
        datePD.show()
    }

    private fun updateUI(view: View) {
        arguments?.getParcelable<Task>("taskKey")?.let { task ->
            editTitleView.setText(task.title)
            dateTextView.text = (task.endDate)
            btnDelete.visibility = VISIBLE
            btnDelete.setOnClickListener {
                deleteButtonFunction(it, task)
            }
            btnSend.setOnClickListener {
                updateFunction(view, task)
            }
        }
    }

    private fun returnToMainFragment() {
        activity?.apply {
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
    }

    private fun sendButtonFunction(view: View) {
        if (editTitleView.text.isNotEmpty() && editTitleView.text.isNotBlank()) {
            viewModel.insertTaskToList(Task(editTitleView.text.toString(), startDate, endDate))
        } else {
            Toast.makeText(view.context, "String is Empty", Toast.LENGTH_SHORT).show()
        }
        returnToMainFragment()
    }

    private fun updateFunction(view: View, task: Task) {
        if (editTitleView.text.isNotEmpty() && editTitleView.text.isNotBlank()) {
            viewModel.updateTaskOnList(
                task, Task(editTitleView.text.toString(), task.startDate, task.endDate)
            )
        } else {
            Toast.makeText(view.context, "String is Empty", Toast.LENGTH_SHORT).show()
        }
        returnToMainFragment()
    }

    private fun cancelButtonFunction(view: View) {
        returnToMainFragment()
    }

    private fun deleteButtonFunction(view: View, task: Task) {
        viewModel.deleteTaskFromList(task)
        returnToMainFragment()
    }
}