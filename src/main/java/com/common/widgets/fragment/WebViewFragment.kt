package com.common.widgets.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.common.widgets.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWebViewBinding.inflate(inflater, container, false).also { _binding = it }.root

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(BUNDLE_URL)?.let { url ->
            binding?.webView?.let {
                it.settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = arguments?.getBoolean(BUNDLE_ZOOM_ENABLE, false) ?: false
                    useWideViewPort = arguments?.getBoolean(BUNDLE_ZOOM_ENABLE, false) ?: false
                    builtInZoomControls = arguments?.getBoolean(BUNDLE_ZOOM_ENABLE, false) ?: false
                }
                it.loadUrl(url)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BUNDLE_URL = "BUNDLE_URL"
        private const val BUNDLE_ZOOM_ENABLE = "BUNDLE_ZOOM_ENABLE"
        fun newInstance(url: String, enableZoom: Boolean = false) = WebViewFragment().apply {
            arguments = bundleOf(BUNDLE_URL to url, BUNDLE_ZOOM_ENABLE to enableZoom)
        }
    }
}