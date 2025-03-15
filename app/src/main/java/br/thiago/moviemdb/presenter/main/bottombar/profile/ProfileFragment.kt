package br.thiago.moviemdb.presenter.main.bottombar.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.thiago.moviemdb.R
import br.thiago.moviemdb.databinding.BottomSheetLogoutBinding
import br.thiago.moviemdb.databinding.FragmentProfileBinding
import br.thiago.moviemdb.domain.model.menu.MenuProfile
import br.thiago.moviemdb.domain.model.menu.MenuProfileType
import br.thiago.moviemdb.domain.model.user.User
import br.thiago.moviemdb.presenter.auth.activity.AuthActivity
import br.thiago.moviemdb.presenter.auth.activity.AuthActivity.Companion.AUTHENTICATION_PARAMETER
import br.thiago.moviemdb.presenter.auth.enums.AuthenticationDestinations
import br.thiago.moviemdb.presenter.main.bottombar.profile.adapter.ProfileMenuAdapter
import br.thiago.moviemdb.util.FirebaseHelper
import br.thiago.moviemdb.util.StateView
import br.thiago.moviemdb.util.applyScreenWindowInsets
import br.thiago.moviemdb.util.showSnackBar
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var mAdapter: ProfileMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyScreenWindowInsets(
            view = view,
            applyBottom = false
        )

        getUser()

        initRecycler()
    }

    private fun initRecycler() {
        mAdapter = ProfileMenuAdapter(
            items = MenuProfile.items,
            context = requireContext(),
            onClick = { type ->
                when (type) {
                    MenuProfileType.PROFILE -> {
                        findNavController().navigate(R.id.action_menu_profile_to_editProfileFragment)
                    }

                    MenuProfileType.NOTIFICATION -> {

                    }

                    MenuProfileType.DOWNLOAD -> {
                        val bottomNavigation =
                            activity?.findViewById<BottomNavigationView>(R.id.btnv)
                        bottomNavigation?.selectedItemId = R.id.menu_download
                    }

                    MenuProfileType.SECURITY -> {

                    }

                    MenuProfileType.LANGUAGE -> {

                    }

                    MenuProfileType.DARK_MODE -> {

                    }

                    MenuProfileType.HELPER -> {

                    }

                    MenuProfileType.PRIVACY_POLICY -> {

                    }

                    MenuProfileType.LOGOUT -> {
                        showBottomSheetLogout()
                    }
                }
            }
        )

        with(binding.recyclerItems) {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun configData(user: User) {
        binding.textUsername.text = getString(
            R.string.text_user_full_name_profile_fragment,
            user.firstName,
            user.surName
        )
        binding.textEmail.text = FirebaseHelper.getAuth().currentUser?.email

        binding.textPhotoEmpty.isVisible = user.photoUrl?.isEmpty() == true
        binding.imageProfile.isVisible = user.photoUrl?.isNotEmpty() == true

        if (user.photoUrl?.isNotEmpty() == true) {
            Glide
                .with(requireContext())
                .load(user.photoUrl)
                .into(binding.imageProfile)
        } else {
            binding.textPhotoEmpty.text = getString(
                R.string.text_user_full_name_profile_fragment,
                user.firstName?.first(),
                user.surName?.first()
            )
        }
    }

    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}

                is StateView.Success -> {
                    stateView.data?.let {
                        configData(user = it)
                    }
                }

                is StateView.Error -> {
                    showSnackBar(
                        message = FirebaseHelper.validError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    private fun showBottomSheetLogout() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetLogoutBinding.inflate(
            layoutInflater, null, false
        )

        bottomSheetBinding.btnCancel.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetBinding.btnConfirm.setOnClickListener {
            bottomSheetDialog.dismiss()
            logout()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        activity?.finish()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.putExtra(AUTHENTICATION_PARAMETER, AuthenticationDestinations.LOGIN_SCREEN)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}