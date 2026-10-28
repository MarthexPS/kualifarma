package com.androidatc.kualifarma.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidatc.kualifarma.databinding.FragmentPosBinding

class PosFragment : Fragment() {

    private var _binding: FragmentPosBinding? = null
    private val binding get() = _binding!!

    private var countPara = 1
    private var countIbu = 2
    private val pricePara = 35.0
    private val priceIbu = 48.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // Corregido: recibe view y savedInstanceState

        fun updateSummary() {
            val totalItems = countPara + countIbu
            val totalPrice = (countPara * pricePara) + (countIbu * priceIbu)
            binding.tvTotalSummary.text = "$totalItems productos | $${String.format("%.2f", totalPrice)} MXN"
        }

        updateSummary()

        // Incrementar y decrementar Paracetamol
        binding.btnPlusPara.setOnClickListener {
            countPara++
            binding.tvCountPara.text = countPara.toString()
            updateSummary()
        }

        binding.btnMinusPara.setOnClickListener {
            if (countPara > 0) {
                countPara--
                binding.tvCountPara.text = countPara.toString()
                updateSummary()
            }
        }

        // Incrementar y decrementar Ibuprofeno
        binding.btnPlusIbu.setOnClickListener {
            countIbu++
            binding.tvCountIbu.text = countIbu.toString()
            updateSummary()
        }

        binding.btnMinusIbu.setOnClickListener {
            if (countIbu > 0) {
                countIbu--
                binding.tvCountIbu.text = countIbu.toString()
                updateSummary()
            }
        }

        // Clicks en Chips de Filtro
        binding.chipAll.setOnClickListener {
            Toast.makeText(requireContext(), "Mostrando todos los medicamentos", Toast.LENGTH_SHORT).show()
        }
        binding.chipAnalgesicos.setOnClickListener {
            Toast.makeText(requireContext(), "Categoría: Analgésicos", Toast.LENGTH_SHORT).show()
        }
        binding.chipAntibioticos.setOnClickListener {
            Toast.makeText(requireContext(), "Categoría: Antibióticos", Toast.LENGTH_SHORT).show()
        }
        binding.chipAntivirales.setOnClickListener {
            Toast.makeText(requireContext(), "Categoría: Antivirales", Toast.LENGTH_SHORT).show()
        }
        binding.chipDiabetes.setOnClickListener {
            Toast.makeText(requireContext(), "Categoría: Diabetes", Toast.LENGTH_SHORT).show()
        }

        // Botón Cobrar
        binding.btnPay.setOnClickListener {
            Toast.makeText(requireContext(), "Procesando cobro...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}