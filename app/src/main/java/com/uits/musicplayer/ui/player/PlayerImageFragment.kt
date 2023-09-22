package com.uits.musicplayer.ui.player

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBindings
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.databinding.FragmentDashboardBinding
import com.uits.musicplayer.databinding.FragmentPlayerImageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerImageFragment() : Fragment() {
    private var _binding: FragmentPlayerImageBinding? = null
    private val binding get() = _binding!!
    lateinit var root: View

    companion object {
        fun newInstance(image: String) = PlayerImageFragment().apply {
            arguments = Bundle().apply {
                putString("image", image)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerImageBinding.inflate(inflater, container, false)
        root = binding.root
        val imvAlbumPlayer: AppCompatImageView? = binding.imvAlbumPlayer
        val img = arguments?.getString("image")
        if (img != null) {
            Log.d("ppp", img)
        }
        if (!img.isNullOrEmpty()) {
            if (imvAlbumPlayer != null) {
                Glide.with(requireContext()).load(img).centerCrop()
                    .placeholder(R.mipmap.ic_launcher).into(imvAlbumPlayer)
            }
        }

        return root
    }
}