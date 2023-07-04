package com.example.tipcalculator

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.tipcalculator.databinding.FragmentFirstBinding

private const val TAG = "FirstFragment"
private const val INITIAL_TIP_PERCENT = 15
class FirstFragment : Fragment(R.layout.fragment_first) {
    //private lateinit var seekBarTip : SeekBar
    private lateinit var binding: FragmentFirstBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        binding.seekBarTip.progress = INITIAL_TIP_PERCENT

        binding.tvTipPercent.text = "$INITIAL_TIP_PERCENT%%"
        updateTipDescription(INITIAL_TIP_PERCENT)
        binding.seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                binding.tvTipPercent.text = "$progress"
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        binding.etBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanged $s")
                computeTipAndTotal()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription= when(tipPercent){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        binding.tvRater.text=tipDescription
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat()/binding.seekBarTip.max,
            ContextCompat.getColor(requireContext(), R.color.color_worst_tip),
            ContextCompat.getColor(requireContext(), R.color.color_best_tip)
        ) as Int
        binding.tvRater.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if(binding.etBaseAmount.text.isEmpty()){
            binding.tvTipAmount.text = ""
            binding.tvTotalAmount.text = ""
            return
        }
        //1. Getting values of Bill and Tip percent
        val billAmount= binding.etBaseAmount.text.toString().toDouble()
        val tipPercent= binding.seekBarTip.progress
        //2. Calculating Tip and Total Amount
        val tipAmount = billAmount * tipPercent/100
        val totalAmount = billAmount + tipAmount
        //3. Update the UI
        binding.tvTipAmount.text="%.2f".format(tipAmount)
        binding.tvTotalAmount.text="%.2f".format(totalAmount)


    }
}
