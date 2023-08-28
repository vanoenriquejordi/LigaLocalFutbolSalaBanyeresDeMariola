package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Publicaciones(@get:Exclude var id: String = "",
                         var title: String = "",
                         var photoUrl: String ="",
                         var comment: String="",
                         var separacion: String="---")
