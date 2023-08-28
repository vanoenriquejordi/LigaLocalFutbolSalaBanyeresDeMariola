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
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.FragmentMengolBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.databinding.ItemMengolBinding
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.entities.MenGol
import tfc.jordivanoenrique.ligalocalfutbolsalabanyeresdemariola.utils.FragmentAux

class MenGolFragment : Fragment(), FragmentAux {

    private lateinit var mBindingMaxGol: FragmentMengolBinding
    private lateinit var mFirebaseAdapterClass: FirebaseRecyclerAdapter<MenGol, SnapshotHolder>
    private lateinit var mLayoutManagerClass: RecyclerView.LayoutManager
    private lateinit var mSnapshotsMaxGol: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBindingMaxGol = FragmentMengolBinding.inflate(inflater, container, false)
        return mBindingMaxGol.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirebase()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupFirebase() {
        mSnapshotsMaxGol = FirebaseDatabase.getInstance().reference.child(LLFSApplication.PATH_MENGOL)
    }

    private fun setupAdapter() {
        val query = mSnapshotsMaxGol
        val options = FirebaseRecyclerOptions.Builder<MenGol>().setQuery(query) {
            val snapshot = it.getValue(MenGol::class.java)
            snapshot!!.id = it.key!!
            snapshot
        }.build()

        mFirebaseAdapterClass = object : FirebaseRecyclerAdapter<MenGol, SnapshotHolder>(options) {
            private lateinit var mContext: Context

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapshotHolder {
                mContext = parent.context
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_maxgol, parent, false)
                return SnapshotHolder(view)
            }

            override fun onBindViewHolder(holder: SnapshotHolder, position: Int, model: MenGol) {
                val snapshotClass = getItem(position)
                with(holder) {
                    with(binding) {
                        tvPosicion.text = snapshotClass.posicion
                        tvEquipo.text = snapshotClass.equipo
                        tvGoles.text=snapshotClass.goles
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
                Snackbar.make(mBindingMaxGol.root, error.message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        mLayoutManagerClass = LinearLayoutManager(context)
        mBindingMaxGol.recyclerViewClass.apply {
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
        mBindingMaxGol.recyclerViewClass.smoothScrollToPosition(0)
    }

    inner class SnapshotHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMengolBinding.bind(view)

    }
}
