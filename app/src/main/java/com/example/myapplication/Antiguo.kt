package com.example.myapplication

import com.example.myapplication.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

//class Antiguo : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }*/

    var operacion: String? = null
    var operando1 = 0L
    var operando2 = 0L
    var borrarTexto = false
    var numActual = 0L
    //var cara1 = binding.imgCara.setImageResource(R.drawable.cara1)
    //var cara2 = binding.imgCara.setImageResource(R.drawable.cara2)


    fun onClickNumeros(vista: View) {
        val boton = vista as Button
        val numero = boton.text.toString()

        if (borrarTexto) {
            binding.tvNumeros.text = ""
            borrarTexto = false
            binding.imgCara.setImageResource(R.drawable.cara1)
        }

        if (binding.tvNumeros.text.length < 12) {
            binding.tvNumeros.append(numero)
        }
    }

    fun onClickOperaciones(vista: View) {
        val boton = vista as Button
        operando1 = binding.tvNumeros.text.toString().toLong()
        borrarTexto = true
        binding.imgCara.setImageResource(R.drawable.cara1)
        binding.btIgual.isEnabled = true

        operacion = when (boton) {
            binding.btnSuma -> "suma"
            binding.btnResta -> "resta"
            binding.btnMultiplicacion -> "multiplicacion"
            binding.btnDivision -> "division"
            else -> null
        }
    }

    fun mostrarResultado(): Long {
        val texto = binding.tvNumeros.text.toString()
        if (texto.isEmpty()) return 0L
        operando2 = texto.toLong()

        return when (operacion) {
            "suma" -> operando1 + operando2
            "resta" -> operando1 - operando2
            "multiplicacion" -> operando1 * operando2
            "division" -> if (operando2 != 0L) operando1 / operando2 else 0L
            else -> 0L
        }
    }

    fun onClickIgual(vista: View) {
        val texto = binding.tvNumeros.text.toString()

        if (texto.isEmpty()) {
            binding.imgCara.setImageResource(R.drawable.cara1)
            binding.tvNumeros.text = ""
        } else {
            val resultado = mostrarResultado().toString()
            binding.imgCara.setImageResource(R.drawable.cara2)
            binding.tvNumeros.text = resultado
        }
    }

    fun onClickBorrarTodo(vista : View) {
        binding.tvNumeros.text = ""
        binding.imgCara.setImageResource(R.drawable.cara1)
    }

    fun onClickBorrarActual(vista : View) {

    }

    fun onClickDelete(vista: View) {
        val textoActual = binding.tvNumeros.text.toString()
        binding.imgCara.setImageResource(R.drawable.cara1)

        if (textoActual.isNotEmpty()) {
            val nuevoTexto = textoActual.dropLast(1)
            binding.tvNumeros.text = nuevoTexto

            numActual = if (nuevoTexto.isNotEmpty()) nuevoTexto.toLong() else 0L
        }
    }


    fun onClickNegativo(vista : View) {
        val textoActual = binding.tvNumeros.text.toString()
        binding.imgCara.setImageResource(R.drawable.cara1)

        val numNegativo = textoActual.toString().toLong() * -1
        binding.tvNumeros.text = numNegativo.toString()

    }

    fun onClickDecimal(vista : View) {

    }

    fun onClickPorciento(vista : View) {

    }
//}