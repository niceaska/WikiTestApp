package ru.niceaska.wikitest.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import ru.niceaska.wikitest.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initTitle();
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
            geoViewModel.startLocationService()
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