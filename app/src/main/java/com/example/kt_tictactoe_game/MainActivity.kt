package com.example.kt_tictactoe_game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtAddrss: EditText
    private lateinit var btnadd: Button
    private lateinit var btnview: Button
    private lateinit var btnupdate: Button


    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private  var adapter: SQLiteAdapter?=null
    private var std:PlayerModel?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        //back to home navigation
        /*supportActionBar?.setDisplayHomeAsUpEnabled(true)*/


        //Listview with databasae
        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnadd.setOnClickListener { addPlayer() }
        btnview.setOnClickListener { getPlayer() }
        btnupdate.setOnClickListener { updatePlayer() }

        adapter?.setOnClickItem {
            Toast.makeText(this,it.name, Toast.LENGTH_SHORT).show()
            edtName.setText(it.name)
            edtAddrss.setText(it.address)
            std = it
        }
        adapter?.setOnClickDeleteItem {
            deletePlayer(it.pid)


        }
    }

    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_print -> {
            // User chose the "Print" item
            Toast.makeText(this, "Game Page..", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MainActivity, Game::class.java)
            startActivity(intent)
            true
        }
        else -> {
            Toast.makeText(this, "Not Supported..", Toast.LENGTH_LONG).show()
            super.onOptionsItemSelected(item)
        }

    }
    private fun getPlayer() {
        val stdList =sqLiteHelper.getallPlayer()
        Log.e("display","${stdList.size}")

        //display
        adapter?.addItems(stdList)
    }

    private fun addPlayer(){
        val name=edtName.text.toString()
        val address=edtAddrss.text.toString()

        if (name.isEmpty() || address.isEmpty())
        {
            Toast.makeText(this,"Please Enter Required Fields", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val std = PlayerModel(name = name,address = address)
            val status =sqLiteHelper.insertPlayer(std)

            if (status > -1 ){
                Toast.makeText(this,"Player added SuccessFully...", Toast.LENGTH_SHORT).show()
                clearEdittext()
                getPlayer()
            }
            else{
                Toast.makeText(this,"Record not inserted....", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePlayer() {
        val name = edtName.text.toString()
        val address = edtAddrss.text.toString()

        if(name == std?.name && address == std?.address )
        {
            Toast.makeText(this,"Record not changed....", Toast.LENGTH_SHORT).show()
            return
        }
        if(std == null ) return
        val  std = PlayerModel(pid= std!!.pid,name=name,address=address)
        val status =sqLiteHelper.updatePlayer(std)

        if (status > -1 ){
            Toast.makeText(this,"Record Update SuccessFully...", Toast.LENGTH_SHORT).show()
            clearEdittext()
            getPlayer()
        }
        else{
            Toast.makeText(this,"Record update failed....", Toast.LENGTH_SHORT).show()
        }
    }

    private  fun deletePlayer(pid:Int)
    {
        if(pid == null ) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are You Sure To Delete Data")
        builder.setCancelable(true)
        builder.setPositiveButton("yes") { dialog,_->
            sqLiteHelper.deletePlayerById(pid)
            getPlayer()
            dialog.dismiss()
        }
        builder.setNegativeButton("no") { dialog,_->
            dialog.dismiss()
        }

        val alert =builder.create()
        alert.show()

    }
    private fun clearEdittext(){
        edtName.setText("")
        edtAddrss.setText("")
        edtName.requestFocus()
    }

    private fun initRecyclerView()
    {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter= SQLiteAdapter()
        recyclerView.adapter=adapter
    }

    private fun initView()
    {
        edtName=findViewById(R.id.edtname)
        edtAddrss=findViewById(R.id.edtaddress)
        btnadd=findViewById(R.id.btnadd)
        btnview=findViewById(R.id.btnview)
        btnupdate=findViewById(R.id.btnupdate)
        recyclerView=findViewById(R.id.recyclerView)
    }
}