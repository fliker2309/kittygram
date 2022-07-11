package com.example.kittygram.presentation.ui.home

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.kittygram.databinding.FragmentHomeBinding
import com.example.kittygram.domain.model.Cat
import com.example.kittygram.presentation.ui.home.adapter.CatActionListener
import com.example.kittygram.presentation.ui.home.adapter.HomeAdapter
import com.example.kittygram.utils.Constants.Companion.MIME_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var adapter: HomeAdapter

    private lateinit var imageLoader: ImageLoader

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setPermissionCallback()
        imageLoader = Coil.imageLoader(requireContext())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = HomeAdapter(object : CatActionListener {
            override fun onCatDownload(cat: Cat) {
                getBitmapFromUrl(cat.url)
            }

            override fun onCatLike(cat: Cat) {
                // TODO: implement add to favorite
                Toast.makeText(requireContext(), "onLikeClick ${cat.url}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.apply {
            catRV.adapter = adapter
            catRV.layoutManager =
                GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("tag", "initObservers")
            viewModel.flow.collectLatest { pagingData: PagingData<Cat> ->
                adapter.submitData(pagingData)
                Log.d("tag", "success adapter")
                Log.d("tag", "pagingData: $pagingData")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                if (it.append is LoadState.Error) {
                    adapter.retry()
                } else  Log.d("tag", "loadStateFlow: $it")
            }
        }
    }

    private fun setPermissionCallback() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun saveImage(bitmap: Bitmap) {
        val fileName = "cat_${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, MIME_TYPE)
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imageDir, fileName)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(requireContext(), "Image saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getBitmapFromUrl(bitmapUrl: String) = lifecycleScope.launch {
        val request = ImageRequest.Builder(requireContext())
            .data(bitmapUrl)
            .build()
        try {
            val downloadedBitmap = (imageLoader.execute(request).drawable as BitmapDrawable).bitmap
            saveImage(downloadedBitmap)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Error downloading image, check your internet connection",
                Toast.LENGTH_SHORT
            ).show()
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissionAndDownloadBitmap(bitmapUrl: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                getBitmapFromUrl(bitmapUrl)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                Toast.makeText(
                    requireContext(),
                    "Permission is needed to save image",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}
