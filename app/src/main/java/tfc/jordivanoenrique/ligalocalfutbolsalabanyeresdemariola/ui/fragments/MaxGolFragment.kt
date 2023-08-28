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
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.FragmentMaxgolBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.ItemMaxgolBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities.MaxGol
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.FragmentAux

// Define una clase llamada ClassFragment que extiende la clase Fragment e implementa la interfaz FragmentAux
class MaxGolFragment : Fragment(), FragmentAux {

    // Declaración de variables miembro
    private lateinit var mBindingMaxGol: FragmentMaxgolBinding
    private lateinit var mFirebaseAdapterClass: FirebaseRecyclerAdapter<MaxGol, SnapshotHolder>
    private lateinit var mLayoutManagerClass: RecyclerView.LayoutManager
    private lateinit var mSnapshotsMaxGol: DatabaseReference

    // Sobrescribe el método onCreateView para inflar y devolver la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBindingMaxGol = FragmentMaxgolBinding.inflate(inflater, container, false)
        return mBindingMaxGol.root
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
        mSnapshotsMaxGol = FirebaseDatabase.getInstance().reference.child(LLFSApplication.PATH_MAXGOL)
    }

    // Configura el adaptador de FirebaseRecyclerAdapter para la lista de clasificaciones
    private fun setupAdapter() {
        // Construye una consulta FirebaseRecyclerOptions para la entidad Clasificacion
        val query = mSnapshotsMaxGol
        val options = FirebaseRecyclerOptions.Builder<MaxGol>().setQuery(query) {
            val snapshot = it.getValue(MaxGol::class.java)
            snapshot!!.id = it.key!!
            snapshot
        }.build()

        // Crea un adaptador personalizado FirebaseRecyclerAdapter para la lista de clasificaciones
        mFirebaseAdapterClass = object : FirebaseRecyclerAdapter<MaxGol, SnapshotHolder>(options) {
            private lateinit var mContext: Context

            // Crea y devuelve una nueva vista para cada elemento de la lista
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapshotHolder {
                mContext = parent.context
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_maxgol, parent, false)
                return SnapshotHolder(view)
            }

            // Vincula los datos del modelo a la vista correspondiente en la lista
            override fun onBindViewHolder(holder: SnapshotHolder, position: Int, model: MaxGol) {
                val snapshotClass = getItem(position)
                with(holder) {
                    with(binding) {
                        tvPosicion.text = snapshotClass.posicion
                        tvEquipo.text = snapshotClass.equipo
                        tvGoles.text=snapshotClass.goles
                        tvNombreJugador.text=snapshotClass.nombrejugador
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
                Snackbar.make(mBindingMaxGol.root, error.message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // Configura el RecyclerView y su layoutManager
    private fun setupRecyclerView() {
        mLayoutManagerClass = LinearLayoutManager(context)
        mBindingMaxGol.recyclerViewClass.apply {
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
        mBindingMaxGol.recyclerViewClass.smoothScrollToPosition(0)
    }

    // Clase interna para mantener la vista de cada elemento en el RecyclerView
    inner class SnapshotHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMaxgolBinding.bind(view)

    }
}
