package com.example.mydatasiswa.uicontroller.route

import com.example.mydatasiswa.R

object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = R.string.detail_siswa
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}
