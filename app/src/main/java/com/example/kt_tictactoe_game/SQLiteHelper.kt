package com.example.kt_tictactoe_game

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.Exception

class SQLiteHelper (context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="playerdata.db"
        private const val TBL_PLAYER="tbl_player"
        private const val ID="pid"
        private const val NAME="name"
        private const val ADDRESS="address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createtblplayer=("CREATE TABLE "+ TBL_PLAYER + "("
                + ID +" INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + ADDRESS + " TEXT" + ")")
            db?.execSQL(createtblplayer)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PLAYER")
        onCreate(db)
    }

    //insert
    fun insertPlayer(std:PlayerModel):Long{
        val db = this.writableDatabase
        val  contentValues = ContentValues()

        contentValues.put(ID,std.pid)
        contentValues.put(NAME,std.name)
        contentValues.put(ADDRESS,std.address)

        val success =db.insert(TBL_PLAYER, null,contentValues)
        db.close()
        return success
    }

    //print
    @SuppressLint("Range", "Recycle")
    fun getallPlayer(): ArrayList<PlayerModel>{
        val  stdList :ArrayList<PlayerModel> = ArrayList()
        val selectQuery ="SELECT * FROM $TBL_PLAYER"
        val db = this.readableDatabase

        val cursor : Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var pid :Int
        var name:String
        var address:String

        if (cursor.moveToFirst()){
            do {
                pid=cursor.getInt(cursor.getColumnIndex("pid"))
                name=cursor.getString(cursor.getColumnIndex("name"))
                address=cursor.getString(cursor.getColumnIndex("address"))
                val std = PlayerModel(pid=pid,name=name,address=address)
                stdList.add(std)
            }while (cursor.moveToNext())
        }
        return stdList
    }

    //for update
    fun updatePlayer(std:PlayerModel):Int
    {
        val db = this.writableDatabase
        val  contentValues = ContentValues()

        contentValues.put(ID,std.pid)
        contentValues.put(NAME,std.name)
        contentValues.put(ADDRESS,std.address)

        val success =db.update(TBL_PLAYER,contentValues,"pid="+std.pid, null)
        db.close()
        return success
    }

    fun deletePlayerById(pid:Int):Int{
        val db = this.writableDatabase
        val  contentValues = ContentValues()

        contentValues.put(ID,pid)

        val success =db.delete(TBL_PLAYER,"pid=$pid", null)
        db.close()
        return success
    }
}