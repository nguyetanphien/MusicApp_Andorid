package com.uits.musicplayer.ui.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uits.musicplayer.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    companion object {
        fun newInstance() = PlayerImageFragment()
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
        return inflater.inflate(R.layout.fragment_player_image, container, false)
    }
}