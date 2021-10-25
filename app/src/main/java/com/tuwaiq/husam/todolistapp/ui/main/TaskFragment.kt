package com.tuwaiq.husam.todolistapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tuwaiq.husam.todolistapp.R
import com.tuwaiq.husam.todolistapp.data.Task

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private lateinit var viewModel: TaskViewModel
    private lateinit var editTitleView: EditText
    private lateinit var btnSend: Button
    private lateinit var btnCancel: Button

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
        btnSend.setOnClickListener {
            if (editTitleView.text.isNotEmpty()) {
                viewModel.insertTasktoList(Task(editTitleView.text.toString()))
            } else {
                Toast.makeText(view.context, "String is Empty", Toast.LENGTH_SHORT).show()
            }
            activity?.apply {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .addToBackStack("Save Task F to Main F")
                    .commit()
            }
        }
        btnCancel.setOnClickListener {
            activity?.apply {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .addToBackStack("Cancel Task F to Main F")
                    .commit()
            }
        }
    }
}