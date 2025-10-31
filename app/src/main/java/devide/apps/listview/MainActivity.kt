package devide.apps.listview

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        val gestureDetector = GestureDetector(
            this,
            object : GestureDetector.SimpleOnGestureListener(){
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    val position = _lvl1.pointToPosition(e.x.toInt(),e.y.toInt())
                    if(position != ListView.INVALID_POSITION){
                        val selectedItem = data[position]
                        showActionDialog(position,selectedItem,data,lvadapter)
                    }
                    return true
                }


            }
        )
    }

    private fun showActionDialog(
        position: Int,
        selectedItem: String,
        data: MutableList<String>,
        lvadapter: ArrayAdapter<String>
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ITEM $selectedItem")
        builder.setMessage("Pilih tindakan:")

        builder.setPositiveButton("Update") { _, _ ->
            showUpdateDialog(position, selectedItem,data,adapter)
        }

        builder.setNegativeButton("Hapus"){_,_ ->
            data.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(
                this,
                "Hapus Item $selectedItem",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNeutralButton("Batal"){dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun showUpdateDialog(
        position: Int,
        oldValue: String,
        data: MutableList<String>,
        adapter: ArrayAdapter<String>
    ){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Data")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50,40,50,10)

        val tvOld = TextView(this)
        tvOld.text = "Data lama: $oldValue"
        tvOld.textSize = 16f

        val etNew = EditText(this)
        etNew.hint = "Masukan data baru"
        etNew.setText(oldValue)

        layout.addView(tvOld)
        layout.addView(etNew)

        builder.setView(layout)
        builder.setPositiveButton("Simpan") { dialog, _ ->
            val newValue = etNew.text.toString().trim()
            if (newValue.isNotEmpty()) {
                data[position] = newValue
                adapter.notifyDataSetChanged()
                Toast.makeText(
                    this,
                    "Data diupdate jadi: $newValue",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Data baru tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()

    }
}

