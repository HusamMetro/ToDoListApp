package com.tuwaiq.husam.todolistapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.husam.todolistapp.R
import com.tuwaiq.husam.todolistapp.TaskRecyclerAdapter
import com.tuwaiq.husam.todolistapp.data.Task

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

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
        val taskList = mutableListOf<Task>()
        for (index in 0..15) {
             taskList += Task(" Task Title = $index ")
        }
        recyclerView.adapter = TaskRecyclerAdapter(taskList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

}