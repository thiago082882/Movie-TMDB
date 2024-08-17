package br.thiago.moviemdb.presenter.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.thiago.moviemdb.R
import br.thiago.moviemdb.databinding.FragmentLoginBinding
import br.thiago.moviemdb.presenter.main.activity.MainActivity
import br.thiago.moviemdb.util.FirebaseHelper
import br.thiago.moviemdb.util.StateView
import br.thiago.moviemdb.util.hideKeyboard
import br.thiago.moviemdb.util.initToolbar
import br.thiago.moviemdb.util.isEmailValid
import br.thiago.moviemdb.util.onNavigate
import br.thiago.moviemdb.util.showSnackBar

import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener { validateData() }

        binding.btnForgot.setOnClickListener {
            findNavController().onNavigate(R.id.action_loginFragment_to_forgotFragment)
        }

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
                login(email, password)
            } else {
                showSnackBar(message = R.string.text_password_empty_login_fragment)
            }
        } else {
            showSnackBar(message = R.string.text_email_empty_login_fragment)
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
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