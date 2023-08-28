package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Clasificacion(@get:Exclude var id: String = "", var posicion: String = "",
                  var equipo: String = "",
                  var puntuacion: String = "",
                  var photoUrlClass: String =""){

}