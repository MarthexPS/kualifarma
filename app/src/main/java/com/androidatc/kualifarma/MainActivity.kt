package com.androidatc.kualifarma

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidatc.kualifarma.R
import com.androidatc.kualifarma.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Estado dinámico del carrito (Precios y Cantidades)
    private var countPara = 1
    private var countIbu = 2
    private val pricePara = 35.0
    private val priceIbu = 48.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pantalla inicial por defecto: Dashboard
        loadView(R.layout.fragment_dashboard)
        setupDashboardEvents()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadView(R.layout.fragment_dashboard)
                    setupDashboardEvents()
                    true
                }
                R.id.nav_pos -> {
                    loadView(R.layout.fragment_pos)
                    setupPosEvents()
                    true
                }
                R.id.nav_inventory -> {
                    loadView(R.layout.fragment_inventory)
                    setupInventoryEvents()
                    true
                }
                R.id.nav_profile -> {
                    showProfileView()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadView(layoutId: Int) {
        binding.fragmentContainer.removeAllViews()
        val view = LayoutInflater.from(this).inflate(layoutId, binding.fragmentContainer, false)
        binding.fragmentContainer.addView(view)
    }

    // 1. DASHBOARD
    private fun setupDashboardEvents() {
        findViewById<View>(R.id.tvAvatarProfile)?.setOnClickListener {
            binding.bottomNavigation.selectedItemId = R.id.nav_profile
        }

        findViewById<Button>(R.id.btnGoToPos)?.setOnClickListener {
            binding.bottomNavigation.selectedItemId = R.id.nav_pos
        }

        findViewById<View>(R.id.cardStockBajo)?.setOnClickListener {
            Toast.makeText(this, "Filtrando medicamentos con Stock Bajo", Toast.LENGTH_SHORT).show()
            binding.bottomNavigation.selectedItemId = R.id.nav_inventory
        }

        findViewById<View>(R.id.cardVencimientos)?.setOnClickListener {
            Toast.makeText(this, "Filtrando lotes próximos a vencer", Toast.LENGTH_SHORT).show()
            binding.bottomNavigation.selectedItemId = R.id.nav_inventory
        }
    }

    // 2. INVENTARIO Y FILTRADO
    private fun setupInventoryEvents() {
        findViewById<Button>(R.id.btnNewProduct)?.setOnClickListener {
            loadView(R.layout.fragment_add_product)
            setupAddProductEvents()
        }

        findViewById<ImageView>(R.id.ivCameraScanInv)?.setOnClickListener {
            showCameraScanner()
        }

        findViewById<View>(R.id.cardProduct1)?.setOnClickListener { showProductDetail() }
        findViewById<View>(R.id.cardProduct2)?.setOnClickListener { showProductDetail() }
        findViewById<View>(R.id.cardProduct3)?.setOnClickListener { showProductDetail() }

        findViewById<Chip>(R.id.chipFilterAll)?.setOnClickListener {
            Toast.makeText(this, "Mostrando todos los medicamentos", Toast.LENGTH_SHORT).show()
        }
        findViewById<Chip>(R.id.chipFilterLowStock)?.setOnClickListener {
            Toast.makeText(this, "Filtrando: Solo Stock Bajo", Toast.LENGTH_SHORT).show()
        }
        findViewById<Chip>(R.id.chipFilterExpiring)?.setOnClickListener {
            Toast.makeText(this, "Filtrando: Próximos a caducar", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. PUNTO DE VENTA (POS) CON INCREMENTO Y DECREMENTO
    private fun setupPosEvents() {
        val tvCountPara = findViewById<TextView>(R.id.tvCountPara)
        val tvCountIbu = findViewById<TextView>(R.id.tvCountIbu)
        val tvTotalSummary = findViewById<TextView>(R.id.tvTotalSummary)

        // Refrescar cantidades iniciales en pantalla
        tvCountPara?.text = countPara.toString()
        tvCountIbu?.text = countIbu.toString()

        // Función para recalcular total de productos y costo
        fun updateSummary() {
            val totalItems = countPara + countIbu
            val totalPrice = (countPara * pricePara) + (countIbu * priceIbu)
            tvTotalSummary?.text = "$totalItems productos | $${String.format("%.2f", totalPrice)} MXN"
        }

        // Inicializar texto de la barra inferior
        updateSummary()

        // Controles de Paracetamol
        findViewById<Button>(R.id.btnPlusPara)?.setOnClickListener {
            countPara++
            tvCountPara?.text = countPara.toString()
            updateSummary()
        }

        findViewById<Button>(R.id.btnMinusPara)?.setOnClickListener {
            if (countPara > 0) {
                countPara--
                tvCountPara?.text = countPara.toString()
                updateSummary()
            }
        }

        // Controles de Ibuprofeno
        findViewById<Button>(R.id.btnPlusIbu)?.setOnClickListener {
            countIbu++
            tvCountIbu?.text = countIbu.toString()
            updateSummary()
        }

        findViewById<Button>(R.id.btnMinusIbu)?.setOnClickListener {
            if (countIbu > 0) {
                countIbu--
                tvCountIbu?.text = countIbu.toString()
                updateSummary()
            }
        }

        // Búsqueda y escáner
        findViewById<EditText>(R.id.etSearchProduct)?.setOnClickListener {
            showProductDetail()
        }

        findViewById<ImageView>(R.id.ivCameraScanPos)?.setOnClickListener {
            showCameraScanner()
        }

        // Chips
        findViewById<Chip>(R.id.chipAnalgesicos)?.setOnClickListener {
            Toast.makeText(this, "Categoría: Analgésicos", Toast.LENGTH_SHORT).show()
        }
        findViewById<Chip>(R.id.chipAntibioticos)?.setOnClickListener {
            Toast.makeText(this, "Categoría: Antibióticos", Toast.LENGTH_SHORT).show()
        }

        // Cobrar
        findViewById<Button>(R.id.btnPay)?.setOnClickListener {
            showCheckoutView()
        }
    }

    // 4. ESCÁNER DE CÁMARA
    private fun showCameraScanner() {
        loadView(R.layout.fragment_camera_scanner)

        findViewById<TextView>(R.id.btnBackFromCamera)?.setOnClickListener {
            binding.bottomNavigation.selectedItemId = R.id.nav_pos
        }

        findViewById<Button>(R.id.btnSimulateScan)?.setOnClickListener {
            Toast.makeText(this, "Código detectado: AMX-224 (Amoxicilina)", Toast.LENGTH_SHORT).show()
            showProductDetail()
        }
    }

    // 5. VENTANA DE COBRO
    private fun showCheckoutView() {
        loadView(R.layout.fragment_checkout)

        findViewById<TextView>(R.id.btnBackFromCheckout)?.setOnClickListener {
            binding.bottomNavigation.selectedItemId = R.id.nav_pos
        }

        findViewById<Button>(R.id.btnConfirmPayment)?.setOnClickListener {
            Toast.makeText(this, "¡Pago procesado con éxito! Imprimiendo ticket...", Toast.LENGTH_LONG).show()
            // Resetear carrito tras el cobro
            countPara = 0
            countIbu = 0
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    // 6. FORMULARIO Y OTRAS VISTAS
    private fun setupAddProductEvents() {
        findViewById<TextView>(R.id.btnBackFromAdd)?.setOnClickListener {
            binding.bottomNavigation.selectedItemId = R.id.nav_inventory
        }

        findViewById<Button>(R.id.btnSaveProduct)?.setOnClickListener {
            Toast.makeText(this, "¡Medicamento dado de alta!", Toast.LENGTH_SHORT).show()
            binding.bottomNavigation.selectedItemId = R.id.nav_inventory
        }
    }

    private fun showProductDetail() {
        loadView(R.layout.fragment_product_detail)

        findViewById<TextView>(R.id.btnBack)?.setOnClickListener {
            binding.bottomNavigation.selectedItemId = R.id.nav_inventory
        }
    }

    private fun showProfileView() {
        loadView(R.layout.fragment_profile)

        findViewById<Button>(R.id.btnLogout)?.setOnClickListener {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}