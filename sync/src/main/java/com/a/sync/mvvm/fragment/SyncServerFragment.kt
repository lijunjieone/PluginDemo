package com.a.sync.mvvm.fragment

import android.Manifest
import android.content.Intent
import android.view.View
import com.a.base.RBaseFragment
import com.a.findfragment.FragmentAnnotation
import com.a.sync.R
import com.a.sync.databinding.FragmentSyncServerBinding
import com.a.sync.mvvm.Utils
import com.a.sync.mvvm.viewmodel.SyncServerViewModel
import com.bn.utils.toast
import com.jwsd.libzxing.QRCodeManager
import com.jwsd.libzxing.activity.CaptureActivity
import com.permissionx.guolindev.PermissionX


@FragmentAnnotation("SyncServer", "Sync")
class SyncServerFragment : RBaseFragment<SyncServerViewModel, FragmentSyncServerBinding>(),
    View.OnClickListener {
    override fun getContentId(): Int = R.layout.fragment_sync_server

    override fun initView() {
        binding.onClickListener = this
        binding.viewModel = viewModel
        binding.tvCreateQr.setImageBitmap(
            QRCodeManager.getInstance().createQRCode("this is a text", 200, 200)
        )
        val host = "ws://${Utils.IP_ADDRESS_BY_WIFI}:${Utils.MC_WS_PORT}/mc"
        binding.ivCode.setImageBitmap(QRCodeManager.getInstance().createQRCode(host, 500, 500))
        binding.tvHost.text = host

    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.message -> {
                val intent = Intent(requireContext(), CaptureActivity::class.java)
                intent.putExtra("type", 0)
                startActivityForResult(intent, REQUEST_CODE)

            }
            R.id.tv_event -> {
                PermissionX.init(activity)
                    .permissions(
                        Manifest.permission.CAMERA
                    )
                    .request { allGranted, grantedList, deniedList ->
                        if (allGranted) {
                            "All permissions are granted".toast()
                        } else {
                            "These permissions are denied: $deniedList".toast()
                        }
                    }
            }
            R.id.tv_create_qr -> {
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        "test: requestCode:${requestCode} resultCode:${resultCode} data:${data?.extras}".toast()
        binding.tvEvent.text = data?.getStringExtra("result")

    }

    companion object {
        const val REQUEST_CODE = 100
    }

}
