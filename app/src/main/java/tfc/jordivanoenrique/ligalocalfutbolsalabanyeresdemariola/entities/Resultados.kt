package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Resultados(@get:Exclude var id: String = "", var equipo1: String = "",
                         var goles1: String = "",
                         var equipo2: String = "",
                      var goles2: String ="",
                      var tiempo: String="",
                         var photoUrlClass: String =""){

}