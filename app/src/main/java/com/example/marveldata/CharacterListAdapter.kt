package com.example.marveldata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.character_view.view.*

class CharacterListAdapter : RecyclerView.Adapter<CustomViewHolder>() {


    override fun getItemCount(): Int {
        return charList.characters.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForCharacter = layoutInflater.inflate(R.layout.character_view, parent, false)
        return CustomViewHolder(cellForCharacter)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val charName = charList.characters.get(position).name
        holder?.view?.textView_characterName?.text = charName.toString()
        holder?.view.character_number.text =  position.toString()

//        holder?.view.imageView.(charList.characters.get(position).thumbnail)
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){}