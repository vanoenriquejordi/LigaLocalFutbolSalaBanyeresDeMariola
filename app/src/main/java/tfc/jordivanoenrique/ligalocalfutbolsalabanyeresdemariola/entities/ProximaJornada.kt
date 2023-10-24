package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProximaJornada(@get:Exclude var id: String = "",
                          var equipo1: String = "",
                      var equipo2: String = "",
                      var tiempo: String="",
                      var photoUrlClass: String =""){

}