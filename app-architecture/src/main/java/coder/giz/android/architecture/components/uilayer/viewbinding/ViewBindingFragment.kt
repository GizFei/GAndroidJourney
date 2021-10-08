package coder.giz.android.architecture.components.uilayer.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coder.giz.android.architecture.databinding.FragmentViewBindingBinding

/**
 * Created By GizFei on 2021/10/7
 */
class ViewBindingFragment : Fragment() {

    private var _binding: FragmentViewBindingBinding? = null
    private val binding: FragmentViewBindingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewBindingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}