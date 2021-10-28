package com.tuwaiq.husam.todolistapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tuwaiq.husam.todolistapp.R
import com.tuwaiq.husam.todolistapp.TaskRecyclerAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var adapter: TaskRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        recyclerView = view.findViewById(R.id.rvRecycleView_Main)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        viewModel.getTaskList().observe(viewLifecycleOwner) {
            adapter = TaskRecyclerAdapter(it, viewModel)
            recyclerView.adapter = adapter
        }

        btnAdd = view.findViewById(R.id.btnAdd_Main)
        btnAdd.setOnClickListener {
            moveToTask()
        }

    }

    private fun moveToTask() {
        val action: NavDirections = MainFragmentDirections.actionMainFragmentToTaskFragment()
        findNavController().navigate(action)
    }
}