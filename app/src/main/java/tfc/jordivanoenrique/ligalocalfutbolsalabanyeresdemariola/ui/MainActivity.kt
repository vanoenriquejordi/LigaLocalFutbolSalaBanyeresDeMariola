package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.R
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.LLFSApplication
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.ActivityMainBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities.ProximaJornada
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.ClassFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.DeportividadFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.HomeFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.MaxGolFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.MenGolFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.ProfileFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.ProximaJornadaFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.ResultadosFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.SancionesFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments.TeamFragment
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.FragmentAux
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.MainAux

class MainActivity : AppCompatActivity(), MainAux {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mActiveFragment: Fragment
    private var mFragmentManager: FragmentManager? = null

    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private var mFirebaseAuth: FirebaseAuth? = null

    private val authResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            Toast.makeText(this, R.string.main_auth_welcome, Toast.LENGTH_SHORT).show()
        } else {
            if (IdpResponse.fromResultIntent(it.data) == null) {
                finish()
            }
        }
    }

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecer la orientación a portrait (vertical)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigation)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miItem1 -> {
                    Toast.makeText(this, "Publicaciones", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Publicaciones
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem2 -> {
                    Toast.makeText(this, "Clasificación", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Clasificación
                    val fragment = ClassFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem3 -> {
                    Toast.makeText(this, "Resultados", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Resultados
                    val fragment = ResultadosFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem10 -> {
                    Toast.makeText(this, "Próxima Jornada", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Próxima Jornada
                    val fragment = ProximaJornadaFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem4 -> {
                    Toast.makeText(this, "Máximo goleador", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Máximo goleador
                    val fragment = MaxGolFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem5 -> {
                    Toast.makeText(this, "Menor goleado", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Menor goleado
                    val fragment = MenGolFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem6 -> {
                    Toast.makeText(this, "Deportividad", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Deportividad
                    val fragment = DeportividadFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem7 -> {
                    Toast.makeText(this, "Sanciones", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Sanciones
                    val fragment = SancionesFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem8 -> {
                    Toast.makeText(this, "Equipos", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de Equipo
                    val fragment = TeamFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.miItem9 -> {
                    Toast.makeText(this, "Más información", Toast.LENGTH_SHORT).show()
                    // Cambiar al fragmento de MásInformación
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.hostFragment, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        val btnOpenDrawer: Button = findViewById(R.id.btnOpenDrawer)
        btnOpenDrawer.setOnClickListener {
            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
            drawerLayout.openDrawer(GravityCompat.START)
        }


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAuth()
    }

    private fun setupAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { it ->
            if (it.currentUser == null) {
                authResult.launch(
                    AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            listOf(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.AnonymousBuilder().build())
                        )
                        .build()
                )
            } else {
                LLFSApplication.currentUser = it.currentUser!!

                val fragmentProfile = mFragmentManager?.findFragmentByTag(ProfileFragment::class.java.name)
                fragmentProfile?.let {
                    (it as FragmentAux).refresh()
                }

                if (mFragmentManager == null) {
                    mFragmentManager = supportFragmentManager
                    setupBottomNav(mFragmentManager!!)
                }
            }
        }
    }

    private fun setupBottomNav(fragmentManager: FragmentManager) {
        mFragmentManager?.let {
            for (fragment in it.fragments) {
                it.beginTransaction().remove(fragment!!).commit()
            }
        }

        val homeFragment = HomeFragment()
        val classFragment = ClassFragment()
        val profileFragment = ProfileFragment()
        val maxgolFragment = MaxGolFragment()
        val mengolFragment = MenGolFragment()
        val deportividadFragment = DeportividadFragment()
        val sancionesFragment = SancionesFragment()
        val resultadosFragment = ResultadosFragment()
        val teamsFragment = TeamFragment()
        val proxjornFragment = ProximaJornadaFragment()

        mActiveFragment = homeFragment

        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, profileFragment, ProfileFragment::class.java.name)
            .hide(profileFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, maxgolFragment, MaxGolFragment::class.java.name)
            .hide(maxgolFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, mengolFragment, MenGolFragment::class.java.name)
            .hide(mengolFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, classFragment, ClassFragment::class.java.name)
            .hide(classFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, deportividadFragment, DeportividadFragment::class.java.name)
            .hide(deportividadFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, sancionesFragment, SancionesFragment::class.java.name)
            .hide(sancionesFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, resultadosFragment, ResultadosFragment::class.java.name)
            .hide(resultadosFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, teamsFragment, TeamFragment::class.java.name)
            .hide(teamsFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, proxjornFragment, ProximaJornada::class.java.name)
            .hide(proxjornFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, homeFragment, HomeFragment::class.java.name).commit()
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth?.addAuthStateListener(mAuthListener)
    }

    override fun onPause() {
        super.onPause()
        mFirebaseAuth?.removeAuthStateListener(mAuthListener)
    }

    /*
    *   MainAux
    * */
    override fun showMessage(resId: Int, duration: Int) {
        Snackbar.make(mBinding.root, resId, duration)
            .setAnchorView(mBinding.navigation)
            .show()
    }
}
