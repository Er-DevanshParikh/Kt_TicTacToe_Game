package com.example.kt_tictactoe_game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SQLiteAdapter: RecyclerView.Adapter<SQLiteAdapter.PlayerViewHolder>() {

    private var stdList:ArrayList<PlayerModel> = ArrayList()
    private var onClickItem:((PlayerModel)-> Unit)? = null
    private var onClickDeleteItem:((PlayerModel)-> Unit)? = null
    fun addItems(items:ArrayList<PlayerModel>)
    {
        this.stdList=items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback :(PlayerModel) -> Unit)
    {
        this.onClickItem=callback
    }

    fun setOnClickDeleteItem(callback :(PlayerModel) -> Unit)
    {
        this.onClickDeleteItem=callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= PlayerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.single_layout,parent,false)
    )

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.btndelete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class PlayerViewHolder(var view:View):RecyclerView.ViewHolder(view)
    {
        private var pid = view.findViewById<TextView>(R.id.tvid)
        private var name = view.findViewById<TextView>(R.id.tvname)
        private var address = view.findViewById<TextView>(R.id.tvaddress)
        var btndelete= view.findViewById<Button>(R.id.btndelete)
        fun bindView(std:PlayerModel)
        {
            pid.text= std.pid.toString()
            name.text= std.name
            address.text= std.address

        }
    }
}