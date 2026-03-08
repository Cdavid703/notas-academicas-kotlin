package com.example.notasacademicas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etNombreEstudiante: EditText
    private lateinit var etMateria: EditText
    private lateinit var etConocimiento: EditText
    private lateinit var etDesempeno: EditText
    private lateinit var etProducto: EditText
    private lateinit var btnCalcular: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNombreEstudiante = findViewById(R.id.etNombreEstudiante)
        etMateria = findViewById(R.id.etMateria)
        etConocimiento = findViewById(R.id.etConocimiento)
        etDesempeno = findViewById(R.id.etDesempeno)
        etProducto = findViewById(R.id.etProducto)
        btnCalcular = findViewById(R.id.btnCalcular)
        tvResultado = findViewById(R.id.tvResultado)

        btnCalcular.setOnClickListener {
            calcularNota()
        }
    }

    private fun calcularNota() {
        val nombreEstudiante = etNombreEstudiante.text.toString().trim()
        val nombreMateria = etMateria.text.toString().trim()
        val conocimientoTexto = etConocimiento.text.toString().trim()
        val desempenoTexto = etDesempeno.text.toString().trim()
        val productoTexto = etProducto.text.toString().trim()

        if (nombreEstudiante.isEmpty() || nombreMateria.isEmpty() ||
            conocimientoTexto.isEmpty() || desempenoTexto.isEmpty() || productoTexto.isEmpty()
        ) {
            mostrarPopup(getString(R.string.error_campos))
            return
        }

        val conocimiento = conocimientoTexto.toDoubleOrNull()
        val desempeno = desempenoTexto.toDoubleOrNull()
        val producto = productoTexto.toDoubleOrNull()

        if (conocimiento == null || desempeno == null || producto == null) {
            mostrarPopup(getString(R.string.error_campos))
            return
        }

        if (conocimiento < 0 || conocimiento > 5 ||
            desempeno < 0 || desempeno > 5 ||
            producto < 0 || producto > 5
        ) {
            mostrarPopup(getString(R.string.error_notas))
            return
        }

        val notaDefinitiva = (conocimiento * 0.33) + (desempeno * 0.33) + (producto * 0.34)
        val notaFormateada = String.format(Locale.US, "%.2f", notaDefinitiva)

        tvResultado.text = getString(
            R.string.resultado_formato,
            nombreEstudiante,
            nombreMateria,
            notaFormateada
        )

        if (notaDefinitiva >= 4.0) {
            mostrarPopup(getString(R.string.popup_gano))
        } else {
            mostrarPopup(getString(R.string.popup_perdio))
        }
    }

    private fun mostrarPopup(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.popup_titulo))
            .setMessage(mensaje)
            .setPositiveButton(getString(R.string.popup_aceptar)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}