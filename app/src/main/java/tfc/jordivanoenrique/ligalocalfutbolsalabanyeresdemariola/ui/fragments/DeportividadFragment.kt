package tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.R
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.LLFSApplication
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.FragmentDeportividadBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.ItemDeportividadBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities.Deportividad
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.FragmentAux


// Define una clase llamada ClassFragment que extiende la clase Fragment e implementa la interfaz FragmentAux
class DeportividadFragment : Fragment(), FragmentAux {

    // Declaración de variables miembro
    private lateinit var mBindingClass: FragmentDeportividadBinding
    private lateinit var mFirebaseAdapterClass: FirebaseRecyclerAdapter<Deportividad, SnapshotHolder>
    private lateinit var mLayoutManagerClass: RecyclerView.LayoutManager
    private lateinit var mSnapshotsRefClass: DatabaseReference

    // Sobrescribe el método onCreateView para inflar y devolver la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBindingClass = FragmentDeportividadBinding.inflate(inflater, container, false)
        return mBindingClass.root
    }

    // Sobrescribe el método onViewCreated, que se llama después de que la vista se haya creado
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura la base de datos de Firebase, el adaptador y el RecyclerView
        setupFirebase()
        setupAdapter()
        setupRecyclerView()
    }

    // Configura la base de datos de Firebase
    private fun setupFirebase() {
        mSnapshotsRefClass = FirebaseDatabase.getInstance().reference.child(LLFSApplication.PATH_DEPORTIVIDAD)
    }

    // Configura el adaptador de FirebaseRecyclerAdapter para la lista de clasificaciones
    private fun setupAdapter() {
        // Construye una consulta FirebaseRecyclerOptions para la entidad Clasificacion
        val query = mSnapshotsRefClass
        val options = FirebaseRecyclerOptions.Builder<Deportividad>().setQuery(query) {
            val snapshot = it.getValue(Deportividad::class.java)
            snapshot!!.id = it.key!!
            snapshot
        }.build()

        // Crea un adaptador personalizado FirebaseRecyclerAdapter para la lista de clasificaciones
        mFirebaseAdapterClass = object : FirebaseRecyclerAdapter<Deportividad, SnapshotHolder>(options) {
            private lateinit var mContext: Context

            // Crea y devuelve una nueva vista para cada elemento de la lista
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapshotHolder {
                mContext = parent.context
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_deportividad, parent, false)
                return SnapshotHolder(view)
            }

            // Vincula los datos del modelo a la vista correspondiente en la lista
            override fun onBindViewHolder(holder: SnapshotHolder, position: Int, model: Deportividad) {
                val snapshotClass = getItem(position)
                with(holder) {
                    with(binding) {
                        tvPosicion.text = snapshotClass.posicion
                        tvEquipo.text = snapshotClass.equipo
                    }
                }
            }

            // Maneja los cambios en los datos y notifica al adaptador
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChanged() {
                super.onDataChanged()
                notifyDataSetChanged()
            }

            // Maneja errores de Firebase Database
            override fun onError(error: DatabaseError) {
                super.onError(error)
                Snackbar.make(mBindingClass.root, error.message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // Configura el RecyclerView y su layoutManager
    private fun setupRecyclerView() {
        mLayoutManagerClass = LinearLayoutManager(context)
        mBindingClass.recyclerViewClass.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManagerClass
            adapter = mFirebaseAdapterClass
        }
    }

    // Inicia la escucha del adaptador cuando el fragmento está en primer plano
    override fun onStart() {
        super.onStart()
        mFirebaseAdapterClass.startListening()
    }

    // Detiene la escucha del adaptador cuando el fragmento está en segundo plano
    override fun onStop() {
        super.onStop()
        mFirebaseAdapterClass.stopListening()
    }

    // Implementación del método de la interfaz FragmentAux para desplazarse a la posición superior
    override fun refresh() {
        mBindingClass.recyclerViewClass.smoothScrollToPosition(0)
    }

    // Clase interna para mantener la vista de cada elemento en el RecyclerView
    inner class SnapshotHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDeportividadBinding.bind(view)

    }
}
