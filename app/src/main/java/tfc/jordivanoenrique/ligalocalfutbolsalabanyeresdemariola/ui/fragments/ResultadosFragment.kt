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
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.FragmentResultadosBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.ItemResultadosBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities.Resultados
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.FragmentAux

class ResultadosFragment : Fragment(), FragmentAux {

    private lateinit var mBindingClass: FragmentResultadosBinding
    private lateinit var mFirebaseAdapterClass: FirebaseRecyclerAdapter<Resultados, SnapshotHolder>
    private lateinit var mLayoutManagerClass: RecyclerView.LayoutManager
    private lateinit var mSnapshotsRefClass: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBindingClass = FragmentResultadosBinding.inflate(inflater, container, false)
        return mBindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirebase()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupFirebase() {
        mSnapshotsRefClass = FirebaseDatabase.getInstance().reference.child(LLFSApplication.PATH_RESULTADOS)
    }

    private fun setupAdapter() {
        val query = mSnapshotsRefClass
        val options = FirebaseRecyclerOptions.Builder<Resultados>().setQuery(query) {
            val snapshot = it.getValue(Resultados::class.java)
            snapshot!!.id = it.key!!
            snapshot
        }.build()

        mFirebaseAdapterClass = object : FirebaseRecyclerAdapter<Resultados, SnapshotHolder>(options) {
            private lateinit var mContext: Context

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapshotHolder {
                mContext = parent.context
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_resultados, parent, false)
                return SnapshotHolder(view)
            }

            override fun onBindViewHolder(holder: SnapshotHolder, position: Int, model: Resultados) {
                val snapshotClass = getItem(position)
                with(holder) {
                    with(binding) {
                        tvEquipo1.text = snapshotClass.equipo1
                        tvGoles1.text = snapshotClass.goles1
                        tvEquipo2.text = snapshotClass.equipo2
                        tvGoles2.text = snapshotClass.goles2
                        tvVS.text = snapshotClass.tiempo
                    }
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChanged() {
                super.onDataChanged()
                notifyDataSetChanged()
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                Snackbar.make(mBindingClass.root, error.message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        mLayoutManagerClass = LinearLayoutManager(context)
        mBindingClass.recyclerViewClass.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManagerClass
            adapter = mFirebaseAdapterClass
        }
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapterClass.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapterClass.stopListening()
    }

    override fun refresh() {
        mBindingClass.recyclerViewClass.smoothScrollToPosition(0)
    }

    inner class SnapshotHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemResultadosBinding.bind(view)

    }
}
