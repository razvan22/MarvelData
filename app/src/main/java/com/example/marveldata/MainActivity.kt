package com.example.marveldata

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

object charList{
        var characters: MutableList<Character> = mutableListOf()
    }

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        main_button.setOnClickListener(){

            val intent = Intent(this, CharacterListView::class.java)
            startActivity(intent)
            Log.d("__",charList.characters?.size.toString())

        }
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        super.startActivity(intent)
        MarvelRetrofit.marvelService.getAllCharacters(limit = 10, offset = 1)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    result.data.results.forEach {character ->
                        charList.characters?.add(character)
                        Log.d("__", "characters list size :"+charList.characters?.size.toString())
                        Log.d("__", character.name.toString())
                    }


                }
            }
    }


}