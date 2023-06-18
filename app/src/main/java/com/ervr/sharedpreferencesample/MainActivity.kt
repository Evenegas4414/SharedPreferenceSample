package com.ervr.sharedpreferencesample

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.ervr.sharedpreferencesample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PREFS_FILE_NAME = "MyPrefs"
    private lateinit var sharedPreferences: SharedPreferences
    private val KEY_INTEGER = "integer"
    private val KEY_TEXT = "text"
    private val KEY_RADIO = "radio"
    private val KEY_DECIMAL = "decimal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

        // Mostrar los datos guardados en las preferencias
        binding.editTextInteger.setText(sharedPreferences.getInt(KEY_INTEGER, 0).toString())
        binding.editTextText.setText(sharedPreferences.getString(KEY_TEXT, ""))
        binding.radioButton.isChecked = sharedPreferences.getBoolean(KEY_RADIO, false)
        binding.editTextDecimal.setText(sharedPreferences.getFloat(KEY_DECIMAL, 0.0f).toString())

        // Validación del campo de número entero
        binding.editTextInteger.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                binding.textViewInteger.text = ""
            } else {
                if (isValidInteger(text.toString())) {
                    binding.textViewInteger.text = "Entero ingresado: $text"
                } else {
                    binding.textViewInteger.text = ""
                    Toast.makeText(this, "Entero no valido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Validación del campo de texto
        binding.editTextText.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                binding.textViewText.text = ""
            } else {
                if (isValidText(text.toString())) {
                    binding.textViewText.text = "Texto ingresado: $text"
                } else {
                    binding.textViewText.text = ""
                    Toast.makeText(this, "Texto Invalido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Validación del campo de número decimal
        binding.editTextDecimal.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                binding.textViewDecimal.text = ""
            } else {
                if (isValidDecimal(text.toString())) {
                    binding.textViewDecimal.text = "Decimal ingresado: $text"
                } else {
                    binding.textViewDecimal.text = ""
                    Toast.makeText(this, "Decimal invalido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Guardar los datos en las preferencias al presionar el botón "Save"
        binding.buttonSave.setOnClickListener {
            val integerText = binding.editTextInteger.text.toString()
            val text = binding.editTextText.text.toString()
            val radio = binding.radioButton.isChecked
            val decimalText = binding.editTextDecimal.text.toString()

            if (isValidInteger(integerText) && isValidText(text) && isValidDecimal(decimalText)) {
                val editor = sharedPreferences.edit()
                editor.putInt(KEY_INTEGER, integerText.toInt())
                editor.putString(KEY_TEXT, text)
                editor.putBoolean(KEY_RADIO, radio)
                editor.putFloat(KEY_DECIMAL, decimalText.toFloat())
                editor.apply()

                Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor ingrese datos validos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para validar un número entero
    private fun isValidInteger(value: String): Boolean {
        return try {
            value.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    // Función para validar un texto (solo letras)
    private fun isValidText(value: String): Boolean {
        return value.matches("[a-zA-Z ]+".toRegex())
    }

    // Función para validar un número decimal
    private fun isValidDecimal(value: String): Boolean {
        return try {
            value.toFloat()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}
