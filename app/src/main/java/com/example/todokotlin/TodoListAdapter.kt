package com.example.todokotlin


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.todokotlin.model.Todo

class TodoListAdapter(
    context: Context,
    private val mResource: Int,
    private val mItems: MutableList<Todo>)
    : ArrayAdapter<Todo>(context, mResource, mItems) {

    private val mInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d("TodoListAdapter", "#getView position $position")
        var view = convertView
        if (convertView == null) {
            view = mInflater.inflate(mResource, null)
        }

        val isCompleted = view!!.findViewById<View>(R.id.todo_list_is_completed)
        val todoTask = view.findViewById<TextView>(R.id.todo_list_content)
        val delete = view.findViewById<View>(R.id.todo_list_delete)

        val item = mItems[position]

        todoTask.text = item.task

        val resId = if (item.isCompleted)
            R.drawable.round_icon_complete
        else
            R.drawable.round_icon
        val res = ResourcesCompat.getDrawable(context.resources, resId, null)
        isCompleted.background = res
        isCompleted.setOnClickListener {
            item.isCompleted = !item.isCompleted
            notifyDataSetChanged()
        }

        delete.setOnClickListener {
            mItems.removeAt(position)
            notifyDataSetChanged()
        }
        return view
    }
}