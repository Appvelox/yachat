package ru.appvelox.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.appvelox.dialog.Dialog
import ru.appvelox.dialog.DialogView
import ru.appvelox.myapplication.MessageGenerator.generateDialogs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialogView.setOnItemClickListener(object : DialogView.OnDialogClickListener {
            override fun onClick(dialog: Dialog) {
                startActivity(Intent(this@MainActivity, ChatActivity::class.java))
            }
        })

        dialogView.setOnItemLongClickListener(object : DialogView.OnDialogLongClickListener {
            override fun onLongClick(dialog: Dialog) {
                Toast.makeText(this@MainActivity, "OnLongClick", Toast.LENGTH_SHORT).show()
            }
        })

        val dialogs = generateDialogs()

        dialogView.addDialogs(dialogs)
    }
}