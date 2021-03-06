package com.aliumujib.cameralib

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View

internal class PermissionsDelegate(private val activity: Activity) {

    fun hasCameraPermission(): Boolean {
        val permissionCheckResult = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
        )
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    fun hasStoragePermission(): Boolean {
        val permissionCheckResult = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE
        )
    }

    fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE
        )
    }

    fun resultGranted(requestCode: Int,
                      permissions: Array<String>,
                      grantResults: IntArray): Boolean {

        if (requestCode != REQUEST_CODE) {
            return false
        }

        if (grantResults.isEmpty()) {
            return false
        }
        if (permissions[0] != Manifest.permission.CAMERA) {
            return false
        }

        val noPermissionView = activity.findViewById<View>(R.id.no_permission)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            noPermissionView.visibility = View.GONE
            return true
        }

        requestCameraPermission()
        noPermissionView.visibility = View.VISIBLE
        return false
    }

    companion object {

        private val REQUEST_CODE = 10
    }
}
