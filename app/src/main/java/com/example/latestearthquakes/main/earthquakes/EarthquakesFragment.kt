package com.example.latestearthquakes.main.earthquakes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.latestearthquakes.R
import com.example.latestearthquakes.data.model.DepremModel
import com.example.latestearthquakes.data.model.Depremler
import com.example.latestearthquakes.data.service.DepremlerAPIService
import com.example.latestearthquakes.databinding.FragmentBottomSheetBinding
import com.example.latestearthquakes.databinding.FragmentEarthquakesBinding
import com.example.latestearthquakes.main.adapter.DepremlerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

class EarthquakesFragment : Fragment() {

    private lateinit var binding: FragmentEarthquakesBinding
    private lateinit var depremData: DepremModel
    private val BASE_URL = "https://deprem-api.herokuapp.com"
    private var retrofit: DepremlerAPIService? = null
    private val bottomSheet = BottomSheetFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.fragment_earthquakes, container, false)
        bottomSheet.onCreateView(layoutInflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DepremlerAPIService::class.java)
        Log.d("dataJSON", "okey...")
        loadPage()
        binding.pullToRefresh.setOnRefreshListener {
            loadPage()
        }

        bottomSheet.onClicked = { start, end ->
            binding.progressBar.visibility=View.VISIBLE
            binding.depremlerRecycler.visibility=View.INVISIBLE
            getEarthquakeFiltered(start, end) {
                setupRecyler(it)
            }
        }
        binding.filterIcon.setOnClickListener {
            bottomSheet.onViewCreated(view, savedInstanceState)
            bottomSheet.show(requireActivity().supportFragmentManager, "bottom sheet fragment")//neden fragment managerin ustu cizili
        }
    }

    private fun loadPage() {
        binding.pullToRefresh.visibility = View.INVISIBLE
        getEarthquakes {
            setupRecyler(it)
        }
    }
    private fun setupRecyler(depremModel: DepremModel?) {
        binding.depremlerRecycler.visibility=View.VISIBLE
        depremModel?.let {
            val adapter = DepremlerAdapter(it, Onclick = {
                binding.progressBar.visibility = View.VISIBLE
                val action = EarthquakesFragmentDirections.actionEarthquakesFragmentToFragmentDetails(it)
                Navigation.findNavController(this.requireView()).navigate(action)
            })
            binding.Title.visibility = View.VISIBLE
            binding.Date.text = getDate().substring(0, 10)
            binding.depremlerRecycler.adapter = adapter
            binding.progressBar.visibility = View.INVISIBLE
            binding.pullToRefresh.isRefreshing = false
            binding.pullToRefresh.visibility = View.VISIBLE
        }
    }

    private fun getEarthquakes(completed: (DepremModel) -> Unit) {

        val response = retrofit?.getEarthquakes()

        response?.enqueue(object : Callback<DepremModel> {
            override fun onResponse(call: Call<DepremModel>, response: Response<DepremModel>) {
                Log.d("dataJSON", Gson().toJson(response.body()))
                depremData = response.body()!!
                completed(depremData)
            }

            override fun onFailure(call: Call<DepremModel>, t: Throwable) {
                Log.d("dataJSON", t.localizedMessage.toString())
                t.printStackTrace()
            }
        })
    }

    @SuppressLint("NewApi")
    private fun getDate(): String {
        val instant = Instant.ofEpochMilli(System.currentTimeMillis())
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return date.toString()


    }

    fun getEarthquakeFiltered(start: Int, end: Int, completed: (DepremModel) -> Unit) {
        val response = retrofit?.getEarthquakeForSize(start)

        response?.enqueue(object : Callback<DepremModel> {
            override fun onResponse(call: Call<DepremModel>, response: Response<DepremModel>) {
                Log.d("dataJSON", Gson().toJson(response.body()))
                depremData = response.body()!!
                val depremGecici:DepremModel=depremData.copy()
                depremData.depremler=depremData.depremler?.filter {
                    it.buyukluk.ml.toString().toDouble() <= end
                }
                completed(depremData)
            }

            override fun onFailure(call: Call<DepremModel>, t: Throwable) {
                Log.d("dataJSON", t.localizedMessage.toString())
                t.printStackTrace()
            }
        })
    }
}