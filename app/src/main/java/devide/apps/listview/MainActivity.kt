package devide.apps.listview

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var data = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        data.addAll(listOf("1", "2", "3", "4", "5"))

        val lvadapter = ArrayAdapter(
        this,
        android.R.layout.simple_list_item_1,
        data)

        val _lvl1 = findViewById<ListView>(R.id.lv1)
        _lvl1.adapter = lvadapter

        val _btnTambah = findViewById<Button>(R.id.btnTambah)

        _btnTambah.setOnClickListener {
            val dtAkhir = Integer.parseInt(data.get(data.size-1))+1
            data.add("$dtAkhir")

            lvadapter.notifyDataSetChanged()
        }

        _lvl1.setOnItemClickListener{parent,view,position,id->
            Toast.makeText(
                this,
                data[position],
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}