package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.tvNumeros.text = "0"
    }

    var numero1 = ""
    var numero2 = ""
    var operacion: Operaciones? = null
    enum class Operaciones { SUMA, RESTA, MULTIPLICACION, DIVISION }

    fun resetearVariables() {
        numero1 = ""
        numero2 = ""
        operacion = null
        binding.tvNumeros.text = "0"

    }

    fun onClickNumeros(vista: View) {
        val boton = vista as Button
        val numero = boton.text.toString()
        binding.imgCara.setImageResource(R.drawable.cara1)


        if (binding.tvNumeros.text.length < 12) {

            val numeroActual = if (operacion == null) numero1 else numero2

            if (numero != "." || !numeroActual.contains(".")) {
                val numeroNuevo = numeroActual + numero
                if (operacion == null) {
                    numero1 = numeroNuevo
                } else {
                    numero2 = numeroNuevo
                }
                val cadena = "${(binding.tvNumeros.text.toString().trimStart('0'))}${numero}"
                binding.tvNumeros.text = cadena
            }
        }
    }

    fun onClickOperaciones(vista: View) {
        val boton = vista as Button
        val signo = boton.text.toString()
        binding.imgCara.setImageResource(R.drawable.cara1)

        if (numero2.isNotEmpty()) {
            mostrarResultado()
        }else if (numero1.isNotEmpty() && operacion == null) {
            operacion = when(boton) {
                binding.btnSuma -> Operaciones.SUMA
                binding.btnResta -> Operaciones.RESTA
                binding.btnMultiplicacion -> Operaciones.MULTIPLICACION
                binding.btnDivision -> Operaciones.DIVISION
                else -> null
            }
            if (binding.tvNumeros.text.length < 12) {
                binding.tvNumeros.append(signo)
            }
        }
    }

    fun mostrarResultado() {
        var operando1 = numero1.toFloat()
        var operando2 = numero2.toFloat()
        binding.imgCara.setImageResource(R.drawable.cara2)

        val resultado = when(operacion){
            Operaciones.SUMA -> operando1 + operando2
            Operaciones.RESTA ->  operando1 - operando2
            Operaciones.MULTIPLICACION -> operando1 * operando2
            Operaciones.DIVISION -> operando1 / operando2
            else -> 0f
        }

        if (resultado % 1 != 0F) {
            binding.tvNumeros.text = resultado.toFloat().toString()
            numero1 = resultado.toString()
        }else {
            binding.tvNumeros.text = resultado.toLong().toString()
            numero1 = resultado.toString()
        }

        numero2 = ""
        operacion = null
    }

    fun onClickIgual(vista: View) {
        if (numero2.isNotEmpty()) {
            mostrarResultado()
        }
    }

    fun onClickBorrarTodo(vista : View) {
        binding.tvNumeros.text = ""
        resetearVariables()
        binding.imgCara.setImageResource(R.drawable.cara3)

    }

    fun onClickBorrarActual(vista : View) {
        val caracteres = listOf("+", "-", "/", "x")
        val encontrado = binding.tvNumeros.text.findAnyOf(caracteres)
        binding.imgCara.setImageResource(R.drawable.cara3)


        if (encontrado != null) {
            val separado = binding.tvNumeros.text.split(encontrado.second)
            var cadena = ""
            if (separado[1] == "") {
                cadena = separado[0]
                operacion = null
            } else {
                cadena = separado[0] + encontrado.second
                numero2 = ""
            }
            binding.tvNumeros.text = cadena
        } else {
            binding.tvNumeros.text = ""
            numero1 = ""
        }
        comprobarVacio()
    }

    fun onClickDelete(vista: View) {
        val textoActual = binding.tvNumeros.text
        binding.imgCara.setImageResource(R.drawable.cara3)

        if (textoActual.isNotEmpty()) {
            val caracteres = listOf("+", "-", "/", "x")
            val encontrado = textoActual.findAnyOf(caracteres)

            if (encontrado != null) {
                val separado = textoActual.split(encontrado.second)
                var cadena = ""
                if (separado[1] == "") {
                    cadena = separado[0]
                    operacion = null
                } else {
                    numero2 = separado[1].dropLast(1)
                    cadena = separado[0] + encontrado.second + numero2
                }
                binding.tvNumeros.text = cadena
            } else {
                numero1 = textoActual.dropLast(1).toString()
                binding.tvNumeros.text = numero1
            }
        }
        comprobarVacio()
    }

    fun onClickNegativo(vista : View) {
        if(numero2.isEmpty() && numero1.isNotEmpty()) {
            numero1 = (numero1.toFloat() * -1).toString()
            binding.tvNumeros.text = numero1
        }else if (numero2.isNotEmpty()) {
            val signo = when (operacion) {
                Operaciones.SUMA -> "+"
                Operaciones.RESTA ->"-"
                Operaciones.DIVISION -> "/"
                Operaciones.MULTIPLICACION -> "x"
                else -> null
            }
            numero2 = (numero2.toLong() * -1).toString()
            var cadena = numero1 + signo + if (numero2.contains("-")) "($numero2)" else numero2
            binding.tvNumeros.text = cadena
        }
    }

    fun onClickPorciento(vista : View) {

        if(numero1.isNotEmpty()) {
            var numeroPorciento = numero1.toFloat()/100
            binding.tvNumeros.text = numeroPorciento.toString()
            numero1 = numeroPorciento.toString()
            operacion = null
        }
    }

    fun onClickDecimal(vista: View) {
        if (numero1.isNotEmpty() || numero2.isNotEmpty()) {
            onClickNumeros(vista)
        }
    }

    fun comprobarVacio() {
        if (binding.tvNumeros.text.isEmpty()) binding.tvNumeros.text = "0"
    }
}