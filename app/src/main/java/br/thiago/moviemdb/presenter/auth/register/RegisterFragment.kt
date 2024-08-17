package br.thiago.moviemdb.presenter.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.thiago.moviemdb.R
import br.thiago.moviemdb.databinding.FragmentRegisterBinding
import br.thiago.moviemdb.presenter.main.activity.MainActivity
import br.thiago.moviemdb.util.FirebaseHelper
import br.thiago.moviemdb.util.StateView
import br.thiago.moviemdb.util.hideKeyboard
import br.thiago.moviemdb.util.initToolbar
import br.thiago.moviemdb.util.isEmailValid
import br.thiago.moviemdb.util.showSnackBar
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoading)
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (email.isEmailValid()) {
            if (password.isNotEmpty()) {
                hideKeyboard()
                register(email, password)
            } else {
                showSnackBar(message = R.string.text_password_empty_register_fragment)
            }
        } else {
            showSnackBar(message = R.string.text_email_empty_register_fragment)
        }
    }

    private fun register(email: String, password: String) {
        viewModel.register(email, password).observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressLoading.isVisible = true
                }
                is StateView.Success -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
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