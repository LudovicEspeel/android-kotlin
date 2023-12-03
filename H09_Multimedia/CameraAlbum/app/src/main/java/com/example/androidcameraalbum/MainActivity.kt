package com.example.cameraalbum

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.cameraalbum.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val takePhoto = 1
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // nieuwe manier:
        // (als je echter afbeelding wilt zoeken van album, gebruik dan ActivityResultContracts.GetContent())
        val launcher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // Handle the success
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                binding.imageView.setImageBitmap(rotateIfRequired(bitmap))

            } else {
                // Handle failure
                Toast.makeText(this, "Failed to take photo", Toast.LENGTH_SHORT).show()
            }
        }
        //

        binding.takePhoteButton.setOnClickListener {
            // Create File object to store the image
            outputImage = File(externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(this, "com.example.cameraalbum.fileprovider", outputImage)
            }
            else
            {
                imageUri = Uri.fromFile(outputImage)
            }

            // Start Camera app
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

            // begint deprecated te geraken:
            //startActivityForResult(intent, takePhoto)

            // andere manier:
            launcher.launch(imageUri)
        }

        // TODO: add button to open album + implement functionality...
    }

    // begint deprecated te geraken:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == RESULT_OK) {
                    // Display the photo
                    val bitmap = BitmapFactory.decodeStream(contentResolver.
                    openInputStream(imageUri))
                    binding.imageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
        }
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                return rotateBitmap(bitmap, 90)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                return rotateBitmap(bitmap, 180)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                return rotateBitmap(bitmap, 270)
            }
        }

        return bitmap
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle() // Recycle the bitmap object

        return rotatedBitmap
    }
}
