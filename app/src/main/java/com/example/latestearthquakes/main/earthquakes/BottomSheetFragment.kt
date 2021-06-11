package com.example.latestearthquakes.main.earthquakes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.latestearthquakes.R
import com.example.latestearthquakes.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var behavior: BottomSheetBehavior<View>
    private var start: Int = 0
    private var end: Int = 10
    var onClicked: (start: Int, end: Int) -> Unit = { start, end -> }


    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior = BottomSheetBehavior.from(binding.bottomSheetLinearLayout)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.buttonFilter.setOnClickListener {
            val text = binding.numberStart.text.toString()
            start = if (text != "") text.toInt()
            else 0
            val text2 = binding.numberEnd.text.toString()
            end = if (text2 != "") text2.toInt()
            else 10
            onClicked(start, end)
            dismiss()
        }

    }


}