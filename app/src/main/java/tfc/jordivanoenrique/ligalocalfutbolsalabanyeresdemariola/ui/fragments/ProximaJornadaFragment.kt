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
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.FragmentProxjornadaBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.ItemProxjornBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities.ProximaJornada
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.FragmentAux

class ProximaJornadaFragment : Fragment(), FragmentAux {

    private lateinit var mBindingClass: FragmentProxjornadaBinding
    private lateinit var mFirebaseAdapterClass: FirebaseRecyclerAdapter<ProximaJornada, SnapshotHolder>
    private lateinit var mLayoutManagerClass: RecyclerView.LayoutManager
    private lateinit var mSnapshotsRefClass: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBindingClass = FragmentProxjornadaBinding.inflate(inflater, container, false)
        return mBindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirebase()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupFirebase() {
        mSnapshotsRefClass = FirebaseDatabase.getInstance().reference.child(LLFSApplication.PATH_PROXJORN)
    }

    private fun setupAdapter() {
        val query = mSnapshotsRefClass
        val options = FirebaseRecyclerOptions.Builder<ProximaJornada>().setQuery(query) {
            val snapshot = it.getValue(ProximaJornada::class.java)
            snapshot!!.id = it.key!!
            snapshot
        }.build()

        mFirebaseAdapterClass = object : FirebaseRecyclerAdapter<ProximaJornada, SnapshotHolder>(options) {
            private lateinit var mContext: Context

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapshotHolder {
                mContext = parent.context
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_proxjorn, parent, false)
                return SnapshotHolder(view)
            }

            override fun onBindViewHolder(holder: SnapshotHolder, position: Int, model: ProximaJornada) {
                val snapshotClass = getItem(position)
                with(holder) {
                    with(binding) {
                        tvEquipo1.text = snapshotClass.equipo1
                        tvEquipo2.text = snapshotClass.equipo2
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
        val binding = ItemProxjornBinding.bind(view)

    }
}
