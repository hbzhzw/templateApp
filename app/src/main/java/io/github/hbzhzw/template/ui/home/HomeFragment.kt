package io.github.hbzhzw.template.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.github.hbzhzw.loger.logI
import io.github.hbzhzw.template.databinding.FragmentHomeBinding
import io.github.hbzhzw.utils.common.ByteUtils

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        textView.postDelayed({
            testByteUtils()
        }, 2000L)
        logI(TAG) { "onCreateView ..." }
        return root
    }

    fun testByteUtils() {
        val bytesData = ByteArray(10) { idx ->
            if (idx % 2 == 0 ) {
                0.toByte()
            } else {
                0xff.toByte()
            }
        }

        val bytesStr = ByteUtils.bytesToStr(bytesData)
        logI(TAG) { "bytes data to str: $bytesStr" }

        val data = ByteUtils.strToBytes(bytesStr)
        bytesData.forEachIndexed { idx, byte ->
            logI(TAG) {
                "idx: $idx, byte: $byte, data: ${data?.get(idx)}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}

