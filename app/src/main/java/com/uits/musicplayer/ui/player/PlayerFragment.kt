package com.uits.musicplayer.ui.player


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.uits.musicplayer.R


class PlayerFragment : Fragment() {

   companion object {
        fun newInstance() = PlayerFragment()
    }

//    private lateinit var viewModel: PlayerViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

}



