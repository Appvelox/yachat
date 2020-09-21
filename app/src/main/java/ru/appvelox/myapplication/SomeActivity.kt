package ru.appvelox.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_some.*
import kotlinx.android.synthetic.main.item_message1.view.*
import kotlin.random.Random

class SomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_some)

        recyclerView.adapter = SomeAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

class SomeAdapter: RecyclerView.Adapter<SomeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message1, parent, false)

        return SomeViewHolder(view)
    }

    override fun getItemCount() = 100

    override fun onBindViewHolder(holder: SomeViewHolder, position: Int) {
    }
}

class SomeViewHolder(view: View): RecyclerView.ViewHolder(view){

}