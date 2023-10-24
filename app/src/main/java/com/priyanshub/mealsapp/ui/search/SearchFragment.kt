package com.priyanshub.mealsapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyanshub.mealsapp.R
import com.priyanshub.mealsapp.data.models.Meal
import com.priyanshub.mealsapp.databinding.FragmentSearchBinding
import com.priyanshub.mealsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var mealAdapter: MealsAdapter
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        binding.etSearch.setText(viewModel.mealNameStateFlow.value)
        binding.btnSearch.setOnClickListener {
            val search = binding.etSearch.text
            if (search.isNotEmpty() && search != null){
                viewModel.getSearchedNews(search.toString())
                viewModel.changeDefaultMealSearch(search.toString())
            }
        }
        binding.etSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val search = binding.etSearch.text
                if (search.isNotEmpty() && search != null){
                    viewModel.getSearchedNews(search.toString())
                    viewModel.changeDefaultMealSearch(search.toString())
                }
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken,0)
                return@OnKeyListener true
            }
            false
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(emptyList())
        viewModel.getSearchedNews(viewModel.mealNameStateFlow.value)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.mealsStateFlow.collectLatest {
                    when(it){
                        is Resource.Success -> {
                            val meals = it.data?.meals
                            if (meals != null) {
                                setupRecyclerView(meals)
                            }
                            else{
                                Toast.makeText(requireContext(),"No Meal Found",Toast.LENGTH_SHORT).show()
                            }
                            binding.progressBar.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
                            TODO()
                        }
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(list: List<Meal> ) {
        mealAdapter = MealsAdapter(ArrayList(list), object : MealClickListener {
            override fun onMealClick(meal: Meal) {
                val action = SearchFragmentDirections.actionSearchFragmentToDescriptionFragment(meal)
                findNavController().navigate(action)
            }
        })

        binding.rvMeals.apply {
            adapter = mealAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}