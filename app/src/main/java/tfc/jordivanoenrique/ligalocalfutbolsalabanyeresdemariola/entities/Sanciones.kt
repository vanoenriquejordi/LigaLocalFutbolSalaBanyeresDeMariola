package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Sanciones(@get:Exclude var id: String = "",
                  var equipo: String = "",
                  var nombrejugador: String = "",
                  var partidos: String = "",
                  var photoUrlClass: String =""){

}