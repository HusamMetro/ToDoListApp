package com.tuwaiq.husam.todolistapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.tuwaiq.husam.todolistapp.R
import com.tuwaiq.husam.todolistapp.data.model.Task
import kotlinx.android.synthetic.main.task_fragment.*
import java.util.*


class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }
    private lateinit var viewModel: TaskViewModel

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
        val args: TaskFragmentArgs by navArgs()
        if (args.keyTask == null) {
            compose_task.setContent {
                // Compose Land
                MaterialTheme {
                    TaskDetails()
                }
            }
        } else {
            compose_task.setContent {
                // Compose Land
                MaterialTheme {
                    TaskDetails(task = args.keyTask!!)
                }
            }
        }
    }

    private fun returnToMainFragment() {
        findNavController().popBackStack()
    }

    private fun sendButtonFunction() {
        if (titleTextState.value.text.isNotEmpty() && titleTextState.value.text.isNotBlank()) {
            viewModel.insertTaskToList(
                Task(
                    titleTextState.value.text,
                    startDate,
                    endDate,
                    descriptionTextState.value.text
                )
            )
        } else {
//            Toast.makeText(view.context, "String is Empty", Toast.LENGTH_SHORT).show()
        }
        returnToMainFragment()
    }

    private fun updateFunction(task: Task) {
        if (titleTextState.value.text.isNotEmpty() && titleTextState.value.text.isNotBlank()) {
            task.title = titleTextState.value.text
            task.description = descriptionTextState.value.text
            task.endDate = dateEndTextState.value.text
            if (::comDescriptionTextState.isInitialized) {
                task.desCompleted = comDescriptionTextState.value.text
            }
            viewModel.updateTaskOnList(task)
            returnToMainFragment()
        } else {
//            Toast.makeText(view.context, "String is Empty", Toast.LENGTH_SHORT).show()
        }

    }

    private fun cancelButtonFunction() {
        returnToMainFragment()
    }

    private fun deleteButtonFunction(task: Task) {
        viewModel.deleteTaskFromList(task)
        returnToMainFragment()
    }

    //----------------------------Jetpack Compose-----------------------------
    private lateinit var titleTextState: MutableState<TextFieldValue>
    private lateinit var dateEndTextState: MutableState<TextFieldValue>
    private lateinit var descriptionTextState: MutableState<TextFieldValue>
    private lateinit var comDescriptionTextState: MutableState<TextFieldValue>
    private lateinit var focusManager: FocusManager

    @Composable
    fun TaskDetails(task: Task? = null) {
        focusManager = LocalFocusManager.current
        Surface(color = Color.LightGray) {
            Column(verticalArrangement = Arrangement.Bottom) {
//                WelcomingTitle()
                if (task != null) {
                    TaskPastDouDate(task)
                } else {
                    TaskNew()
                }
            }
        }
    }

    @Composable
    fun WelcomingTitle() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(40.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                    text = "To Do List",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    @Composable
    fun TaskNew() {
        NewCard()
        TaskNewButtons()
    }

    @Composable
    fun UpdateCard(task: Task) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                val enabled = false
                val singleLine = false
                if (task.pastDueDate) {
                    TaskName(name = task.title, enabled = enabled)
                    TaskCreationDate(date = task.startDate)
                    TaskEndDate(date = task.endDate, enabled = enabled)
                    TaskDescription(
                        des = task.description,
                        singleLine = false,
                        enabled = enabled
                    )
                    TaskComDescription(ComDes = task.desCompleted, singleLine = singleLine)
                } else {
                    TaskName(name = task.title)
                    TaskCreationDate(date = task.startDate)
                    TaskEndDate(date = task.endDate)
                    TaskDescription(des = task.description, singleLine = singleLine)
                    if (task.checked) {
                        TaskComDescription(ComDes = task.desCompleted, singleLine = singleLine)
                    }
                }
            }
        }
    }

    @Composable
    fun NewCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                TaskName()
                TaskCreationDate()
                TaskEndDate()
                TaskDescription(singleLine = false)
            }
        }
    }


    @Composable
    fun TaskPastDouDate(task: Task) {
        UpdateCard(task)
        TaskUpdateButtons(task)
    }

    @Composable
    fun TaskUpdateButtons(task: Task) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ExtendedFloatingActionButton(
                        backgroundColor = colorResource(id = R.color.light_pink),
                        onClick = { cancelButtonFunction() },
                        icon = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Cancel"
                            )
                        },
                        text = { Text("Cancel") }
                    )
                    ExtendedFloatingActionButton(
                        backgroundColor = colorResource(id = R.color.light_pink),
                        onClick = { updateFunction(task) },
                        icon = {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "Save"
                            )
                        },
                        text = { Text("Save") }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                ExtendedFloatingActionButton(
                    backgroundColor = colorResource(id = R.color.light_pink),
                    onClick = { deleteButtonFunction(task) },
                    icon = {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete"
                        )
                    },
                    text = { Text("Delete") }
                )
            }
        }
    }

    @Composable
    fun TaskNewButtons() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = 10.dp,
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ExtendedFloatingActionButton(
                    backgroundColor = colorResource(id = R.color.light_pink),
                    onClick = { cancelButtonFunction() },
                    icon = {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Cancel"
                        )
                    },
                    text = { Text("Cancel") }
                )
                ExtendedFloatingActionButton(
                    backgroundColor = colorResource(id = R.color.light_pink),
                    onClick = { sendButtonFunction() },
                    icon = {
                        Icon(
                            Icons.Filled.Done,
                            contentDescription = "Save"
                        )
                    },
                    text = { Text("Save") }
                )
            }
        }
    }


    @Composable
    fun TaskName(name: String = "", enabled: Boolean = true) {
        titleTextState = remember { mutableStateOf(TextFieldValue(name)) }
        CreateTextField(textState = titleTextState, enabled = enabled)
        /*Text(
            text = name,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h4
        )*/
    }

    @Composable
    fun TaskCreationDate(date: String = "") {
        CreateText(text = "Creation Date : $date")
    }

    @Composable
    fun CreateText(text: String) {
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.h6
        )
    }

    @Composable
    fun TaskEndDate(date: String = "", enabled: Boolean = true) {
        dateEndTextState = remember { mutableStateOf(TextFieldValue(date)) }
        CreateDateTextField(textState = dateEndTextState, enabled)
    }

    @Composable
    fun TaskDescription(des: String = "", singleLine: Boolean, enabled: Boolean = true) {
        descriptionTextState = remember { mutableStateOf(TextFieldValue(des)) }
        CreateTextField(textState = descriptionTextState, singleLine = singleLine, enabled)
    }

    @Composable
    fun TaskComDescription(ComDes: String = "", singleLine: Boolean, enabled: Boolean = true) {
        comDescriptionTextState = remember { mutableStateOf(TextFieldValue(ComDes)) }
        CreateTextField(
            textState = comDescriptionTextState,
            singleLine = singleLine,
            enabled = enabled
        )
    }

    private fun getLabel(textState: MutableState<TextFieldValue>): String =
        when (textState) {
            titleTextState -> "Task Title"
            dateEndTextState -> "End Task Date "
            descriptionTextState -> "Task Description"
            comDescriptionTextState -> "Task Completed Description"
            else -> ""
        }

    @Composable
    fun CreateDateTextField(textState: MutableState<TextFieldValue>, enabled: Boolean) {
        val label = getLabel(textState)
        val modifierClick: Modifier = if (enabled) {
            Modifier
                .fillMaxWidth()
                .clickable { showDatePicker(textState) }
        } else {
            Modifier.fillMaxWidth()
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = dateEndTextState.value,
                onValueChange = { dateEndTextState.value = it },
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                label = { Text(text = label) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                enabled = false,
                modifier = modifierClick
            )
        }
    }

    @Composable
    fun CreateTextField(
        textState: MutableState<TextFieldValue>,
        singleLine: Boolean = true,
        enabled: Boolean
    ) {
        val label = getLabel(textState)
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = textState.value,
                enabled = enabled,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                label = { Text(text = label) },
                isError = textState.value.text.isEmpty() && label == "Task Title",
                singleLine = singleLine,
                maxLines = 2,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions(onDone = { if (singleLine) focusManager.clearFocus() })
            )
        }
    }

    private fun showDatePicker(textState: MutableState<TextFieldValue>) {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        activity?.let {
            picker.show(it.supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener { long ->
                getDateFromCalendar(textState, long)
            }
        }
    }

    private fun getDateFromCalendar(
        textState: MutableState<TextFieldValue>,
        long: Long
    ) {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val start = Calendar.getInstance()
        startDate = "${start.get(Calendar.DAY_OF_MONTH)}/${(start.get(Calendar.MONTH)) + 1}/${
            start.get(Calendar.YEAR)
        }"
        cal.timeInMillis = long
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        endDate = "$day/${(month + 1)}/$year"
        dateEndTextState.value = textState.value.copy(text = endDate)
    }

    @Preview
    @Composable
    fun TaskDetailsTheme() {
        MaterialTheme {
            Surface {
                Column {
                    TaskNew()

                }
            }
        }
    }

}