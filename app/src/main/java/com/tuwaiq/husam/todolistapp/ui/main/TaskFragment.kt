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
    private lateinit var dateEndTextView: TextView
    private lateinit var dateCreationTextView: TextView
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
        viewModel = ViewModelProvider(this.requireActivity())[TaskViewModel::class.java]
        variablesAssign(view)
        val args: TaskFragmentArgs by navArgs()
        if (args.keyTask == null) {
            btnSend.setOnClickListener {
                sendButtonFunction(it)
            }
        } else {
            updateUI(view, args.keyTask!!)
        }
        dateEndTextView.setOnClickListener {
            getDateFromDatePickerDialog(it)
        }
        btnCancel.setOnClickListener {
            cancelButtonFunction()
        }
    }

    private fun variablesAssign(view: View) {
        editTitleView = view.findViewById(R.id.editTitle1_Task)
        btnSend = view.findViewById(R.id.btnSave_Task)
        btnCancel = view.findViewById(R.id.btnCancel_Task)
        btnDelete = view.findViewById(R.id.btnDelete_Task)
        dateEndTextView = view.findViewById(R.id.txtViewEndDate_Task)
        dateCreationTextView = view.findViewById(R.id.txtViewCreationDate_Task)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        editTextCompletedDescription = view.findViewById(R.id.editTextCompletedDescription)
        completedTextView = view.findViewById(R.id.txtView4_Task)
    }

    private fun getDateFromDatePickerDialog(view: View) {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        startDate = "$day/${(month + 1)}/$year"
        val datePD = DatePickerDialog(view.context, { _, y, m, d ->
            endDate = "$d/${m + 1}/$y"
            dateEndTextView.text = endDate
        }, year, month, day)
        datePD.datePicker.minDate = cal.timeInMillis
        datePD.show()
    }

    private fun updateUI(view: View, task: Task) {
        editTitleView.setText(task.title)
        dateEndTextView.text = (task.endDate)
        editTextDescription.setText(task.description)
        editTextCompletedDescription.setText(task.desCompleted)
        dateCreationTextView.text = task.startDate
        if (task.pastDueDate || task.checked) {
            editTextCompletedDescription.visibility = VISIBLE
            completedTextView.visibility = VISIBLE
            if (task.pastDueDate) {
                editTitleView.isEnabled = false
                editTextDescription.isEnabled = false
                dateEndTextView.isEnabled = false
            }
        }
        btnDelete.visibility = VISIBLE
        btnDelete.setOnClickListener {
            deleteButtonFunction(task)
        }
        btnSend.setOnClickListener {
            updateFunction(view, task)
        }
    }

    private fun returnToMainFragment() {
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

    private fun updateFunction(view: View, task: Task) {
        if (editTitleView.text.isNotEmpty() && editTitleView.text.isNotBlank()) {
            task.title = editTitleView.text.toString()
            task.description = editTextDescription.text.toString()
            task.endDate = dateEndTextView.text.toString()
            task.desCompleted = editTextCompletedDescription.text.toString()
            viewModel.updateTaskOnList(task)

        } else {
            Toast.makeText(view.context, "String is Empty", Toast.LENGTH_SHORT).show()
        }
        returnToMainFragment()
    }

    private fun cancelButtonFunction() {
        returnToMainFragment()
    }

    private fun deleteButtonFunction(task: Task) {
        viewModel.deleteTaskFromList(task)
        returnToMainFragment()
    }
}