package com.kamilagcabay.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.kamilagcabay.texttospeech.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var tts : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            tts = TextToSpeech(this@MainActivity,this@MainActivity)
            btnStart.setOnClickListener{
                val text = editText.text.toString()
                if (text.isEmpty()) {
                    Toast.makeText(this@MainActivity,"Please enter the Text",Toast.LENGTH_LONG).show()
                } else {
                    speak(text)
                }
            }
        }

    }
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this@MainActivity,"Language Not Supported",Toast.LENGTH_LONG).show()

            } else {
                binding.btnStart.isEnabled = true
                tts.setSpeechRate(1.0f)
            }
        } else {
            Toast.makeText(this@MainActivity,"Error Please Try Again",Toast.LENGTH_LONG).show()
        }
    }

    private fun speak(text: String) {
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }



    override fun onDestroy() {
        if (tts.isSpeaking){
            tts.stop()
        }
        tts.shutdown()

        super.onDestroy()
    }
}