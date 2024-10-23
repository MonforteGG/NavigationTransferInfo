package com.adm.navigationtransferinfo

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object PantallaUno : Screen()

    @Serializable
    data class PantallaDos(var texto: String) : Screen()

    @Serializable
    data object PantallaTres : Screen()
}