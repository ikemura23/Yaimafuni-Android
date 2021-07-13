package com.ikmr.banbara23.yaeyama_liner_checker.ui.route

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.RouteStatusDetailDialogFragmentBinding

/**
 * ルールの運行ステータス画面 Dialog
 */
class RouteStatusDetailDialogFragment :
    BottomSheetDialogFragment() //DialogFragment(R.layout.route_status_detail_dialog_fragment)
{
    lateinit var binding: RouteStatusDetailDialogFragmentBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // setStyle(STYLE_NORMAL, R.style.TransparentDialog)

        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheetDialog = (it as? BottomSheetDialog) ?: return@setOnShowListener
                val bottomSheet =
                    bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) ?: return@setOnShowListener

                // 1. BottomSheetDialogのコンテンツ表示部分のlayout_heightにmatch_parentを指定する
                bottomSheet.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT

                // 2. BottomSheetBehaviorのpeekHeightを画面の縦幅と一致させる
                val screenHeight = Resources.getSystem().displayMetrics.heightPixels
                BottomSheetBehavior.from(bottomSheet).peekHeight = screenHeight
            }

            // window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RouteStatusDetailDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // override fun onStart() {
    //     super.onStart()
    //     // binding.root.height = ViewGroup.LayoutParams.MATCH_PARENT
    //     val view: FrameLayout = dialog?.findViewById(R.id.design_bottom_sheet)!!
    //     val behavior = BottomSheetBehavior.from(view)
    //     behavior.peekHeight = 3000
    //     behavior.state = BottomSheetBehavior.STATE_EXPANDED
    // }
}
