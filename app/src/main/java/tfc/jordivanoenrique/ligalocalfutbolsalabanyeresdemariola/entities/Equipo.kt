package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Equipo(@get:Exclude var id: String = "", var equipo: String = "",
                         var primeraEquipacion: String = "",
                  var segundaEquipacion: String = "",
                         var photoUrlClass: String =""){

}