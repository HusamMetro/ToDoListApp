package com.tuwaiq.husam.todolistapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.husam.todolistapp.R
import com.tuwaiq.husam.todolistapp.TaskRecyclerAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerView = view.findViewById(R.id.rvRecycleView_Main)
        // sample of list of Task
        viewModel.fillTaskListWithData()
        recyclerView.adapter = TaskRecyclerAdapter(viewModel.getTaskList())
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        //
        btnAdd = view.findViewById(R.id.btnAdd_Main)
        btnAdd.setOnClickListener {
            val activity = context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, TaskFragment.newInstance())
                .addToBackStack("toTask")
                .commit()
        }

    }
}