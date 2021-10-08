package coder.giz.android.architecture.guide.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import coder.giz.android.architecture.databinding.FragmentUserProfileBinding
import coder.giz.android.architecture.guide.model.User
import coder.giz.android.architecture.guide.viewmodel.UserProfileViewModel

/**
 * Created By GizFei on 2021/10/7
 */
class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val mBinding: FragmentUserProfileBinding get() = _binding!!

    private val mViewModel: UserProfileViewModel by viewModels(
        factoryProducer = { SavedStateViewModelFactory(activity?.application, this) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        mViewModel.user.observe(viewLifecycleOwner) {
            // 更新用户界面
            it?.let(this::updateUI)
        }
    }

    private fun updateUI(user: User) {
        mBinding.user = user
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}