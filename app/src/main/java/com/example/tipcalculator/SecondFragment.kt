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
import com.example.tipcalculator.databinding.FragmentSecondBinding
import org.w3c.dom.Text
private const val TAG = "Second Fragment"
class SecondFragment : Fragment(R.layout.fragment_second) {
    private lateinit var binding: FragmentSecondBinding
    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        binding.etAmountBase.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanges $s")
                computeDividedBill()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        binding.etPeopleNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanged2 $s")
                computeDividedBill()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun computeDividedBill() {
        if(binding.etPeopleNumber.text.isEmpty() || binding.etAmountBase.text.isEmpty()){
            binding.tvFinalPayment.text=""
            return
        }
        //1. Getting the input values
        val amount = binding.etAmountBase.text.toString().toDouble()
        val peopleNumber = binding.etPeopleNumber.text.toString().toInt()
        //2. Calculating final amount
        val finalAmount = amount / peopleNumber
        //3. Printing the final amount
        binding.tvFinalPayment.text = "%.2f".format(finalAmount)
    }
}
