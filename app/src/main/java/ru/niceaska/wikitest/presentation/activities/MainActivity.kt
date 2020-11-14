package ru.niceaska.wikitest.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import ru.niceaska.wikitest.MyApp
import ru.niceaska.wikitest.R
import ru.niceaska.wikitest.presentation.fragments.ImagesTitleFragment
import ru.niceaska.wikitest.presentation.fragments.ListFragment
import ru.niceaska.wikitest.presentation.fragments.ProgressFragment
import ru.niceaska.wikitest.presentation.models.createResultObserver
import ru.niceaska.wikitest.presentation.viewmodels.MainViewModel
import ru.niceaska.wikitest.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

/**
 * Основная активти [ActivityCompat] приложения
 */
class MainActivity : AppCompatActivity(), ImageTitlesViewer, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: MainViewModel
    private var isSavedState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        (applicationContext as? MyApp)?.initFeatureComponent()?.inject(this)

        viewModel = ViewModelProvider(this, factory)
            .get(MainViewModel::class.java)

        initTitle();
        initObservers()

        if (savedInstanceState != null) {
            isSavedState = true
        }
    }

    private fun initObservers() {
        viewModel.resultLiveData.observe(this, createResultObserver(
            { list ->
                if (list != null && !isSavedState) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ListFragment.newInstance(list))
                        .commit()
                }
            },
            { loading -> showProgress(loading) },
            { showError(it) }
        ))
        viewModel.resultImagesTitlesLiveData.observe(this, createResultObserver(
            { list ->
                if (list != null && supportFragmentManager.backStackEntryCount == 0) {
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.container, ImagesTitleFragment.newInstance(list))
                        .commit()
                }
            },
            { loading -> showProgress(loading) },
            { showError(it) }
        ))
    }

    override fun onResume() {
        super.onResume()
        checkGooglePlayServices()
    }

    override fun onDestroy() {
        super.onDestroy()
        (applicationContext as? MyApp)?.destroyFeatureComponent()
    }

    override fun onRefresh() {
        isSavedState = false
        viewModel.fetchData(true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE && grantResults.size > 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.fetchData(false)
        } else {
            finish()
        }
    }

    override fun showImageTitles(id: Long) {
        viewModel.getArticleImagesTitles(id)
    }

    private fun showProgress(enable: Boolean) {
        if (enable) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, ProgressFragment(), PROGRESS)
                .commit()
        } else {
            supportFragmentManager
                .findFragmentByTag(PROGRESS)?.let { progressFragment ->
                    supportFragmentManager.beginTransaction()
                        .remove(progressFragment)
                        .commit()
                }
        }
    }

    private fun showError(error: Throwable) = Toast.makeText(
        this, error.message ?: getString(R.string.unknown_error), Toast.LENGTH_LONG
    ).show()

    private fun checkGooglePlayServices() {
        val googleApiAvailability: GoogleApiAvailability = GoogleApiAvailability.getInstance()
        val statusCode: Int = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (statusCode != ConnectionResult.SUCCESS) {
            googleApiAvailability.getErrorDialog(this, statusCode, 0) { finish() }.show()
        } else {
            checkGeoPermissions()
        }
    }

    private fun checkGeoPermissions() =
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            || checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            viewModel.fetchData(false)
        } else {
            requestPermission()
        }

    private fun checkPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() =
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_CODE
        )

    private fun initTitle() {
        title = getString(R.string.wiki_title)
    }

    companion object {
        private const val PROGRESS = "progress"
        private const val REQUEST_CODE = 101
    }
}