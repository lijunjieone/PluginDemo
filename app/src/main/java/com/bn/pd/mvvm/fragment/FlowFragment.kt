package com.bn.pd.mvvm.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.a.base.RBaseFragment
import com.a.findfragment.FragmentAnnotation
import com.bn.pd.R
import com.bn.pd.databinding.FragmentFlowBinding
import com.bn.pd.mvvm.viewmodel.FlowViewModel


@FragmentAnnotation("Flow", "Demo")
class FlowFragment : RBaseFragment<FlowViewModel, FragmentFlowBinding>(), View.OnClickListener {
    override fun getContentId(): Int = R.layout.fragment_flow

    override fun initView() {
        binding.onClickListener = this
        binding.viewModel = viewModel
    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.loadingLive.observe(this, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t == true) {
                    showLoadingDialog()
                } else {
                    hideLoadingDialog()
                }
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.message -> {
                viewModel.simpleCoroutines {
                    binding.message.text = "Hello World"
                }
            }
            R.id.tv_event -> {
                viewModel.simpleCoroutines2(1000) {
                    binding.tvEvent.text = "Show Hello world 1000ms"
                }
            }
            R.id.tv_job1 -> {
                viewModel.simpleJob1(1000) {
                    binding.tvJob1.text = "Job Not Join"
                }
            }

            R.id.tv_job2 -> {
                viewModel.simpleJob2(1000) {
                    binding.tvJob2.text = "Job And Join"
                }
            }
            R.id.tv_job3 -> {
                viewModel.simpleJob3 {
                    binding.tvJob3.text = it
                }
            }
            R.id.tv_repeat -> {
                viewModel.simpleJobRepeat {
                    binding.tvRepeat.text = it
                }
            }
            R.id.tv_cancel -> {
                viewModel.jobCancel {
                    binding.tvCancelJob.text = it
                }
            }

        }
    }


}
