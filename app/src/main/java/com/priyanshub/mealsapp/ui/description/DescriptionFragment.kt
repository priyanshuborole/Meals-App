package com.priyanshub.mealsapp.ui.description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.priyanshub.mealsapp.R
import com.priyanshub.mealsapp.databinding.FragmentDescriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding
    private val args: DescriptionFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDescriptionBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(args.meal.strMealThumb).into(binding.ivMealImage)
        binding.apply {
            tvMealName.text = args.meal.strMeal
            val category = "Category - ${args.meal.strCategory}"
            tvCategory.text = category
            val area = "Area - ${args.meal.strArea}"
            tvArea.text = area
            val desc = "Description - \n${args.meal.strInstructions}"
            tvDesc.text = desc
            ivBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}