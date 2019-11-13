package com.gsrikar.wikipediasearch.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gsrikar.wikipediasearch.R
import kotlinx.android.synthetic.main.detail_fragment.*


class DetailFragment : Fragment() {

    companion object {
        private const val SUPPORT_SERVICE_ACTION =
            "android.support.customtabs.action.CustomTabsService"
        private const val ANDROIDX_SERVICE_ACTION =
            "androidx.browser.customtabs.CustomTabsService\n"
        private const val CHROME_PACKAGE = "com.android.chrome"
    }

    private lateinit var viewModel: DetailViewModel

    /**
     * List of arguments passed to the Detail Fragment
     */
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.detail_fragment,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        context?.let {
            checkChromeAvailability(
                it,
                "https://en.wikipedia.org/?curid=${args.pageId}"
            )
        }
    }

    private fun isChromeCustomTabsSupported(context: Context): Boolean {
        val serviceIntent = Intent(SUPPORT_SERVICE_ACTION)
        val resolveInfoList =
            context.packageManager.queryIntentServices(serviceIntent, 0)
        return resolveInfoList.isNotEmpty()
    }

    private fun checkChromeAvailability(context: Context, url: String) {
        if (isChromeCustomTabsSupported(context)) {
            // Chrome tabs is supported
            showCustomTabs(context, url)
            // Go back to the previous fragment
            view?.post { findNavController().popBackStack() }
        } else {
            // Chrome custom tabs is not supported
            loadWebView(url)
        }
    }

    /**
     * Load the url in the web view
     */
    private fun loadWebView(url: String) {
        // Enable javascript on the web page
        webView.settings.javaScriptEnabled = true
        // Load the web page
        webView.loadUrl(url)
    }

    /**
     * How the custom tab
     */
    private fun showCustomTabs(context: Context, url: String) {
        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
        val customTabsIntentBuilder = CustomTabsIntent.Builder()
        // Set the toolbar color
        customTabsIntentBuilder.setToolbarColor(
            ContextCompat.getColor(
                context,
                R.color.colorPrimary
            )
        )
        // Shows Website name in the toolbar if true
        customTabsIntentBuilder.setShowTitle(true)
        // Animate when launch the custom tabs is launched
        customTabsIntentBuilder.setStartAnimations(
            context,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        // Animate when launch the custom tabs is exited
        customTabsIntentBuilder.setExitAnimations(
            context,
            R.anim.push_down_in,
            R.anim.push_down_out
        )
        // Call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        val customTabsIntent = customTabsIntentBuilder.build()
        // Launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

}
