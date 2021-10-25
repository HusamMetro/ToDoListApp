package com.tuwaiq.husam.todolistapp.data

object Repo {

    private val taskList: MutableList<Task> = mutableListOf()
    fun getTaskList(): List<Task> {
        return taskList
    }

    fun fillTaskListWithData() {
        if (taskList.isEmpty()) {
            for (index in 0..15) {
                taskList += Task(" Task Title = $index ")
            }
        }
    }

    fun insertTasktoList(task: Task) {
        taskList += task
    }

}