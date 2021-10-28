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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tuwaiq.husam.todolistapp.R
import com.tuwaiq.husam.todolistapp.data.model.Task
import java.util.*

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private lateinit var viewModel: TaskViewModel
    private lateinit var editTitleView: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextCompletedDescription: EditText
    private lateinit var dateTextView: TextView
    private lateinit var completedTextView: TextView
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
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        variablesAssign(view)

        val args: TaskFragmentArgs by navArgs()

        if (args.keyIndex < 0) {
            btnSend.setOnClickListener {
                sendButtonFunction(it)
            }
        } else {
            updateUI(view, args.keyTask!!, args.keyIndex)
        }
        dateTextView.setOnClickListener {
            getDateFromDatePickerDialog(it)
        }
        btnCancel.setOnClickListener {
            cancelButtonFunction(it)
        }

    }
    private fun variablesAssign (view: View) {
        editTitleView = view.findViewById(R.id.editTitle1_Task)
        btnSend = view.findViewById(R.id.btnSave_Task)
        btnCancel = view.findViewById(R.id.btnCancel_Task)
        btnDelete = view.findViewById(R.id.btnDelete_Task)
        dateTextView = view.findViewById(R.id.txtViewEndDate_Task)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        editTextCompletedDescription = view.findViewById(R.id.editTextCompletedDescription)
        completedTextView = view.findViewById(R.id.txtView4_Task)
    }

    private fun getDateFromDatePickerDialog(view: View) {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        startDate = "$day/$month/$year"
        val datePD = DatePickerDialog(view.context, { _, y, m, d ->
            endDate = "$d/${m + 1}/$y"
            dateTextView.text = endDate
        }, year, month, day)
        datePD.datePicker.minDate = cal.timeInMillis
        datePD.show()
    }

    private fun updateUI(view: View, task: Task, index: Int) {
        editTitleView.setText(task.title)
        dateTextView.text = (task.endDate)
        editTextDescription.setText(task.description)
        editTextCompletedDescription.setText(task.desCompleted)
        if (task.pastDueDate || task.checked) {
//                editTitleView.isEnabled = false
//                editTextDescription.isEnabled = false
            editTextCompletedDescription.visibility = VISIBLE
            completedTextView.visibility = VISIBLE
            if (task.pastDueDate) {
                editTitleView.isEnabled = false
                editTextDescription.isEnabled = false
                dateTextView.isEnabled = false
            }
        }
        btnDelete.visibility = VISIBLE
        btnDelete.setOnClickListener {
            deleteButtonFunction(it, task)
        }
        btnSend.setOnClickListener {
            updateFunction(view, task, index)
        }
    }

    private fun returnToMainFragment() {
//        val action : NavDirections = TaskFragmentDirections.actionTaskFragmentToMainFragment()
        findNavController().popBackStack()
    }

    private fun sendButtonFunction(view: View) {
        if (editTitleView.text.isNotEmpty() && editTitleView.text.isNotBlank()) {
            viewModel.insertTaskToList(
                Task(
                    editTitleView.text.toString(),
                    startDate,
                    endDate,
                    editTextDescription.text.toString()
                )
            )

        } else {
            Toast.makeText(view.context, "String is Empty", Toast.LENGTH_SHORT).show()
        }
        returnToMainFragment()
    }

    private fun updateFunction(view: View, task: Task, index: Int) {
        if (editTitleView.text.isNotEmpty() && editTitleView.text.isNotBlank()) {
            task.title = editTitleView.text.toString()
            task.description = editTextDescription.text.toString()
            task.endDate = dateTextView.text.toString()
            task.desCompleted = editTextCompletedDescription.text.toString()
            viewModel.updateTaskOnList(task, index)

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