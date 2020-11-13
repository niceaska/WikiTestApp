package ru.niceaska.wikitest.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import ru.niceaska.wikitest.MyApp
import ru.niceaska.wikitest.R
import ru.niceaska.wikitest.presentation.ViewModelFactory
import ru.niceaska.wikitest.presentation.fragments.ListFragment
import ru.niceaska.wikitest.presentation.viewmodels.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        (applicationContext as? MyApp)?.initListComponent()?.inject(this)

        viewModel = ViewModelProvider(this, factory)
            .get(MainViewModel::class.java)

        initTitle();
        initObservers()


    }

    private fun initObservers() {
        viewModel.placesList.observe(this, (Observer { list ->
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ListFragment.newInstance(list))
                .commit()
        }))
    }

    override fun onResume() {
        super.onResume()
        checkGooglePlayServices()
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
            viewModel.fetchData()
        } else {
            finish()
        }
    }

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
            viewModel.fetchData()
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
        private const val REQUEST_CODE = 101
    }
}