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


class DeportividadFragment : Fragment(), FragmentAux {

    private lateinit var mBindingClass: FragmentDeportividadBinding
    private lateinit var mFirebaseAdapterClass: FirebaseRecyclerAdapter<Deportividad, SnapshotHolder>
    private lateinit var mLayoutManagerClass: RecyclerView.LayoutManager
    private lateinit var mSnapshotsRefClass: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBindingClass = FragmentDeportividadBinding.inflate(inflater, container, false)
        return mBindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirebase()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupFirebase() {
        mSnapshotsRefClass = FirebaseDatabase.getInstance().reference.child(LLFSApplication.PATH_DEPORTIVIDAD)
    }

    private fun setupAdapter() {
        val query = mSnapshotsRefClass
        val options = FirebaseRecyclerOptions.Builder<Deportividad>().setQuery(query) {
            val snapshot = it.getValue(Deportividad::class.java)
            snapshot!!.id = it.key!!
            snapshot
        }.build()

        mFirebaseAdapterClass = object : FirebaseRecyclerAdapter<Deportividad, SnapshotHolder>(options) {
            private lateinit var mContext: Context

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapshotHolder {
                mContext = parent.context
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_deportividad, parent, false)
                return SnapshotHolder(view)
            }

            override fun onBindViewHolder(holder: SnapshotHolder, position: Int, model: Deportividad) {
                val snapshotClass = getItem(position)
                with(holder) {
                    with(binding) {
                        tvPosicion.text = snapshotClass.posicion
                        tvEquipo.text = snapshotClass.equipo
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
        val binding = ItemDeportividadBinding.bind(view)

    }
}
