package com.walmart.test.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.findNavController
import com.baseframework.ui.BaseFragment
import com.walmart.test.R
import android.widget.Toast
import com.walmart.test.databinding.FragmentNasaBinding
import com.walmart.test.viewmodel.NasaViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.squareup.picasso.Picasso

class NasaFragment : BaseFragment<FragmentNasaBinding>(R.layout.fragment_nasa) {

    private val viewModel: NasaViewModel by sharedViewModel()

    override fun setUpFragmentUI(data: Intent?, savedInstanceState: Bundle?, view: View?) {
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(this, { showLoading ->
            if (showLoading) showProgress() else hideProgress()
        })

        viewModel.jokeData.observe(this, { nasa ->
            if (nasa != null) {
                if(nasa.notAvailable) {
                    Toast.makeText(activity,getString(R.string.error), Toast.LENGTH_LONG).show()
                }
                Picasso.get()
                    .load(nasa.hdurl)
                    .into(viewDataBinding.image);
                activity.setTitle(nasa.title)
                viewDataBinding.explanation.text = nasa.explanation
            } else {
                viewDataBinding.image.visibility = View.GONE
                viewDataBinding.explanation.text = getString(R.string.error)
            }
        })
        viewModel.getAstronomyPicture()
    }

}
