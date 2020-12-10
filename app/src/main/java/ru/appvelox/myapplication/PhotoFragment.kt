package ru.appvelox.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        @Suppress("DEPRECATION")
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        val photo = view.findViewById<ImageView>(R.id.photo)

        val photoUrl = arguments!!.getString("photo")

        Picasso.get().load(photoUrl).into(photo)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        @Suppress("DEPRECATION")
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}