package com.example.trashclassifier

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var classifier: ClassifierHelper
    private lateinit var imageView: ImageView
    private lateinit var resultText: TextView
    private lateinit var adviceText: TextView

    private val pickImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { classifyImage(it) }
    }

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            photoUri?.let { classifyImage(it) }
        }
    }

    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        classifier = ClassifierHelper(this)

        imageView = findViewById(R.id.imageView)
        resultText = findViewById(R.id.resultText)
        adviceText = findViewById(R.id.adviceText)

        val btnGallery = findViewById<Button>(R.id.btnGallery)
        val btnCamera = findViewById<Button>(R.id.btnCamera)

        btnGallery.setOnClickListener {
            pickImage.launch("image/*")
        }

        btnCamera.setOnClickListener {
            photoUri = createImageUri()
            photoUri?.let { takePhoto.launch(it) }
        }
    }

    private fun createImageUri(): Uri {
        val fileName = "photo_${System.currentTimeMillis()}.jpg"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )!!
    }

    private fun classifyImage(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val resized = Bitmap.createScaledBitmap(bitmap!!, 224, 224, true)

        imageView.setImageBitmap(resized)

        val result = classifier.classify(resized)
        resultText.text = result.nombreEs
        adviceText.text = "${result.contenedor}\n\n${result.consejo}"
    }
}
