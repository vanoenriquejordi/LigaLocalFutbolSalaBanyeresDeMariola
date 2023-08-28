package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.R
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.LLFSApplication
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.FragmentProfileBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.FragmentAux

// Define una clase llamada ProfileFragment que extiende la clase Fragment e implementa la interfaz FragmentAux
class ProfileFragment : Fragment(), FragmentAux {

    // Declaración de variables miembro
    private lateinit var mBinding: FragmentProfileBinding

    // Sobrescribe el método onCreateView para inflar y devolver la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    // Sobrescribe el método onViewCreated, que se llama después de que la vista se haya creado
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Llama al método refresh() para actualizar la información del perfil y configura el botón
        refresh()
        setupButton()
    }

    // Configura el botón de cierre de sesión
    private fun setupButton() {
       /* mBinding.btnLogout.setOnClickListener {
            context?.let {
                // Muestra un cuadro de diálogo de confirmación de cierre de sesión
                MaterialAlertDialogBuilder(it)
                    .setTitle(R.string.dialog_logout_title)
                    .setPositiveButton(R.string.dialog_logout_confirm) { _, _ ->
                        // Llama al método singOut() para cerrar sesión
                        singOut()
                    }
                    .setNegativeButton(R.string.dialog_logout_cancel, null)
                    .show()
            }
        }*/

        // Agrega un listener al botón para abrir la dirección URL al hacer click
        mBinding.btnDrive.setOnClickListener {
            val url = "https://drive.google.com/drive/u/0/folders/1XF8Ac-JELUyKvfVqNkkqs6oLB_CcECHP"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // Agrega un listener al botón para abrir el google maps al hacer click
        mBinding.btnOpenMaps.setOnClickListener {
            // Coordenadas del punto en el mapa
            val latitude = 38.7141235
            val longitude = -0.6469255

            // Crea la URI para abrir Google Maps con las coordenadas
            val uri = Uri.parse("geo:0,0?q=$latitude,$longitude")

            // Crea un Intent implícito con la acción ACTION_VIEW y la URI de Google Maps
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)

            // Establece el paquete del intent para asegurarte de que se abra en Google Maps
            mapIntent.setPackage("com.google.android.apps.maps")

            // Inicia el intent
            startActivity(mapIntent)
        }
    }


    // Cierra sesión y realiza acciones después de cerrar sesión
    /*private fun singOut() {
        context?.let {
            AuthUI.getInstance().signOut(it)
                .addOnCompleteListener {
                    // Muestra un mensaje de éxito y realiza acciones después de cerrar sesión
                    Toast.makeText(context, R.string.profile_logout_success, Toast.LENGTH_SHORT).show()
                    mBinding.tvName.text = ""
                    mBinding.tvEmail.text = ""

                    // Cambia la selección en la barra de navegación inferior a la opción de inicio (Home)
                    (activity?.findViewById(R.id.navigation) as? BottomNavigationView)?.selectedItemId =
                        R.id.miItem1
                }
        }
    }*/

    /*
    *   Implementación de FragmentAux
    * */
    // Implementación del método de la interfaz FragmentAux para actualizar la información del perfil
    override fun refresh() {
        with(mBinding) {
            // Actualiza el nombre y el correo electrónico del usuario actual en las vistas correspondientes
            tvName.text = LLFSApplication.currentUser.displayName
            tvEmail.text = LLFSApplication.currentUser.email
        }
    }
}
