package br.thiago.moviemdb.presenter.main.bottombar.profile.edit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import br.thiago.moviemdb.R
import br.thiago.moviemdb.databinding.BottomSheetPermissionDeniedBinding
import br.thiago.moviemdb.databinding.BottomSheetSelectImageBinding
import br.thiago.moviemdb.databinding.FragmentEditProfileBinding
import br.thiago.moviemdb.domain.model.user.User
import br.thiago.moviemdb.util.FirebaseHelper
import br.thiago.moviemdb.util.StateView
import br.thiago.moviemdb.util.applyScreenWindowInsets
import br.thiago.moviemdb.util.initToolbar
import br.thiago.moviemdb.util.showSnackBar
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditProfileViewModel by viewModels()

    private val GALLERY_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA

    private var currentPhotoUri: Uri? = null

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar)

        applyScreenWindowInsets(view = view)

        getUser()

        initObservers()

        initListeners()
    }

    private fun initListeners() {
        binding.imageEditProfile.setOnClickListener {
            openBottomSheetSelectImage()
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.validateData(
                name = binding.editFirstName.text.toString(),
                surName = binding.editSurname.text.toString(),
                phone = binding.editPhone.text.toString(),
                genre = binding.editGenre.text.toString(),
                country = binding.editCountry.text.toString()
            )
        }
    }

    private fun initObservers() {
        viewModel.validateData.observe(viewLifecycleOwner) { (validated, stringResId) ->
            if (!validated) {
                stringResId?.let {
                    showSnackBar(message = it)
                }
            } else {
                if (currentPhotoUri != null) {
                    saveUserImage()
                } else {
                    update()
                }
            }
        }
    }


    private fun update(urlImage: String? = null) {
        val user = User(
            id = FirebaseHelper.getUserId(),
            photoUrl = urlImage ?: user?.photoUrl,
            firstName = binding.editFirstName.text.toString(),
            surName = binding.editSurname.text.toString(),
            email = binding.editEmail.text.toString(),
            phone = binding.editPhone.text.toString(),
            genre = binding.editGenre.text.toString(),
            country = binding.editCountry.text.toString()
        )

        viewModel.update(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    showLoading(true)
                }

                is StateView.Success -> {
                    showLoading(false)
                    showSnackBar(message = R.string.text_update_profile_success_edit_profile_fragment)
                }

                is StateView.Error -> {
                    showLoading(false)
                    showSnackBar(
                        message = FirebaseHelper.validError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    showLoading(true)
                }

                is StateView.Success -> {
                    showLoading(false)

                    user = stateView.data

                    configData()
                }

                is StateView.Error -> {
                    showLoading(false)
                    showSnackBar(
                        message = FirebaseHelper.validError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    private fun saveUserImage() {
        currentPhotoUri?.let { uri ->
            viewModel.saveUserImage(uri).observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {
                        showLoading(true)
                    }

                    is StateView.Success -> {
                        showLoading(false)
                        update(stateView.data)
                    }

                    is StateView.Error -> {
                        showLoading(false)
                        showSnackBar(
                            message = FirebaseHelper.validError(
                                error = stateView.message ?: ""
                            )
                        )
                    }
                }
            }
        } ?: run {
            showSnackBar(message = R.string.error_no_image_selected)
        }
    }

    private fun configData() {
        binding.editFirstName.setText(user?.firstName)
        binding.editSurname.setText(user?.surName)
        binding.editEmail.setText(FirebaseHelper.getAuth().currentUser?.email)
        binding.editPhone.setText(user?.phone)
        binding.editGenre.setText(user?.genre)
        binding.editCountry.setText(user?.country)

        binding.textPhotoEmpty.isVisible = user?.photoUrl?.isEmpty() == true
        binding.imageProfile.isVisible = user?.photoUrl?.isNotEmpty() == true

        if (user?.photoUrl?.isNotEmpty() == true) {
            Glide
                .with(requireContext())
                .load(user?.photoUrl)
                .into(binding.imageProfile)
        } else {
            binding.textPhotoEmpty.text = getString(
                R.string.text_photo_empty_edit_profile_fragment,
                user?.firstName?.first(),
                user?.surName?.first()
            )
        }
    }

    private fun openBottomSheetSelectImage() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetSelectImageBinding.inflate(
            layoutInflater, null, false
        )

        bottomSheetBinding.btnCamera.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkCameraPermission()
        }

        bottomSheetBinding.btnGallery.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkGalleryPermission()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun showBottomSheetPermissionDenied() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetPermissionDeniedBinding.inflate(
            layoutInflater, null, false
        )

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.btnAccept.setOnClickListener {
            bottomSheetDialog.dismiss()

            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireActivity().packageName, null)
            )
            startActivity(intent)
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun checkGalleryPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (checkPermissionGranted(GALLERY_PERMISSION)) {
                pickImageLauncher.launch("image/*")
            } else {
                galleryPermissionLauncher.launch(GALLERY_PERMISSION)
            }
        } else {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun checkCameraPermission() {
        if (checkPermissionGranted(CAMERA_PERMISSION)) {
            openCamera()
        } else {
            cameraPermissionLauncher.launch(CAMERA_PERMISSION)
        }
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        photoFile?.let {
            currentPhotoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                it
            )
            takePictureLauncher.launch(currentPhotoUri)
        }
    }

    private fun checkPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            Glide
                .with(requireContext())
                .load(R.drawable.loading)
                .into(binding.progressLoading)

            binding.progressLoading.isVisible = true
        } else {
            binding.progressLoading.isVisible = false
        }
    }

    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return imageFile
    }

    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                pickImageLauncher.launch("image/*")
            } else {
                showBottomSheetPermissionDenied()
            }
        }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                showBottomSheetPermissionDenied()
            }
        }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            currentPhotoUri = it
            binding.imageProfile.setImageURI(it)
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoUri != null) {
            binding.imageProfile.setImageURI(currentPhotoUri)
            binding.imageProfile.isVisible = true // Torna a ImageView visível
            binding.textPhotoEmpty.isVisible = false // Torna o TextView invisível
            binding.imageProfile.invalidate() // Força a atualização da UI
            Log.d("Camera", "Foto capturada: $currentPhotoUri")
        } else {
            Log.d("Camera", "Falha ao capturar a foto.")
        }
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                currentPhotoUri = it
                binding.imageProfile.setImageURI(it)
                binding.imageProfile.isVisible = true
                binding.textPhotoEmpty.isVisible = false
                binding.imageProfile.invalidate()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}