package com.example.todokotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todokotlin.model.Todo
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mTodoList = ArrayList<Todo>()
    private var mIsAllComplete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allCompleted = findViewById<TextView>(R.id.all_completed)
        val entry = findViewById<EditText>(R.id.todo_entry)
        val listView = findViewById<ListView>(R.id.todo_list_view)

        entry.setOnKeyListener { view, i, keyEvent ->
            Log.d("MainActivity", "#View.OnKeyListener.onKey " + entry.text.toString())
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                val task = entry.text.toString()
                if ("" != task) {
                    val todo = Todo(
                        mTodoList.size + 1, task, false
                    )
                    mTodoList.add(todo)
                    // ソフトキーボードを非表示
                    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(view.windowToken, 0)
                    // EditTextの内容をクリア
                    entry.editableText.clear()
                }
            }

            false
        }

        allCompleted.setOnClickListener {
            mIsAllComplete = !mIsAllComplete
            for (i in mTodoList.indices) {
                mTodoList[i].isCompleted = mIsAllComplete
            }
            val adapter = listView.adapter as TodoListAdapter
            adapter.notifyDataSetChanged()
        }

        val adapter = TodoListAdapter(this, R.layout.todo_list_view, mTodoList)
        listView.adapter = adapter
    }
}
