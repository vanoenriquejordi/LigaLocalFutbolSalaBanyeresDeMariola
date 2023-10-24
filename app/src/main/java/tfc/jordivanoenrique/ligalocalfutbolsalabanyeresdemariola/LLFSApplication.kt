package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola

import android.app.Application
import com.google.firebase.auth.FirebaseUser

class LLFSApplication : Application() {
    companion object {
        const val PATH_PUBLICACIONES = "publicaciones"
        const val PATH_CLASS = "clasificacion"
        const val PATH_MAXGOL= "maximogoleador"
        const val PATH_MENGOL= "menorgoleado"
        const val PATH_DEPORTIVIDAD= "deportividad"
        const val PATH_SANCIONES= "sanciones"
        const val PATH_RESULTADOS= "resultados"
        const val PATH_TEAM= "equipos"
        const val PATH_PROXJORN= "proximajornada"

        lateinit var currentUser: FirebaseUser
    }
}