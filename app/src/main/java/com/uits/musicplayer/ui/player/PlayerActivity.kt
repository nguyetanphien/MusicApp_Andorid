package com.uits.musicplayer.ui.player


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.uits.musicplayer.R

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm, PlayerImageFragment.newInstance())
            .commitNow()

        liric()
        back()

    }

    private fun liric() {
        val ibtnPlayerList = findViewById<ImageButton>(R.id.ibtnPlayerList)

        var check = 1
        ibtnPlayerList.setOnClickListener(View.OnClickListener {
            if (check == 1) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fm, PlayerFragment.newInstance())
                    .commitNow()
                check = 0
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fm, PlayerImageFragment.newInstance())
                    .commitNow()
                check = 1

            }
        })
    }
    private fun back(){
        val back=findViewById<ImageButton>(R.id.ibtnbackPlayer)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}