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

class ProfileFragment : Fragment(), FragmentAux {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh()
        setupButton()
    }

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

        mBinding.btnDrive.setOnClickListener {
            val url = "https://drive.google.com/drive/folders/1vsc_WrzmXXjEQk8hWmL2ZGOFcleOMG1p"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        mBinding.btnDriveApp.setOnClickListener {
            val url = "https://drive.google.com/drive/folders/1zuR0_1MrBSOb1vn4HNKSDWJ--Y3l5HJm?usp=sharing"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        mBinding.btnOpenMaps.setOnClickListener {
            val latitude = 38.7141235
            val longitude = -0.6469255

            val uri = Uri.parse("geo:0,0?q=$latitude,$longitude")

            val mapIntent = Intent(Intent.ACTION_VIEW, uri)

            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)
        }
    }


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
    override fun refresh() {
        with(mBinding) {
            tvName.text = LLFSApplication.currentUser.displayName
            tvEmail.text = LLFSApplication.currentUser.email
        }
    }
}
