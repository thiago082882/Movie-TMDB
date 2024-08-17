package br.thiago.moviemdb.presenter.auth.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import br.thiago.moviemdb.R
import br.thiago.moviemdb.databinding.FragmentForgotBinding
import br.thiago.moviemdb.util.FirebaseHelper
import br.thiago.moviemdb.util.StateView
import br.thiago.moviemdb.util.hideKeyboard
import br.thiago.moviemdb.util.initToolbar
import br.thiago.moviemdb.util.isEmailValid
import br.thiago.moviemdb.util.showSnackBar

import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private val viewModel: ForgotViewModel by viewModels()

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initToolbar(toolbar = binding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        binding.btnForgot.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoading)
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString()

        if (email.isEmailValid()) {
            hideKeyboard()
            forgot(email)
        } else {
            showSnackBar(message = R.string.text_email_empty_forgot_fragment)
        }
    }

    private fun forgot(email: String) {
        viewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressLoading.isVisible = true
                }
                is StateView.Success -> {
                    showSnackBar(message = R.string.text_send_email_success_forgot_fragment)
                }
                is StateView.Error -> {
                    binding.progressLoading.isVisible = false
                    showSnackBar(
                        message = FirebaseHelper.validError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}