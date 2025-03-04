package com.wldnasyrf.ds.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.wldnasyrf.ds.databinding.FragmentProfileBinding
import com.wldnasyrf.ds.ui.login.LoginActivity
import com.wldnasyrf.ds.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
        loggedInLayout()
        loggedOutLayout()
    }

    private fun loggedOutLayout() {
        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    private fun loggedInLayout() {
        binding.btnItemLogout.setOnClickListener {
            mainViewModel.logout()
        }
    }

    private fun setupLayout() {
        mainViewModel.userPreferences.observe(viewLifecycleOwner) { user ->
            if (user.token != "") {
                binding.loggedOutLayout.visibility = View.GONE
                binding.loggedInLayout.visibility = View.VISIBLE
            } else {
                binding.loggedInLayout.visibility = View.GONE
                binding.loggedOutLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}