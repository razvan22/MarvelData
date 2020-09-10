package com.example.marveldata

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okio.IOException

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
            fetchData()
        }
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        super.startActivity(intent)
        MarvelRetrofit.marvelService.getAllCharacters(limit = 1, offset = 1)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    result.data.results.forEach {character ->
                        charList.characters.add(character)
                        Log.d("__", "characters "+ character.name + "add to list")
                    }
                }
            }
    }

    private val client = OkHttpClient()

    fun fetchData() {
        val request = Request.Builder()
            .url("https://jsonplaceholder.typicode.com/todos/1")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    println(response.body!!.string())
                }
            }
        })
    }
}