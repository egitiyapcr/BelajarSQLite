package com.example.belajarsqlite.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.belajarsqlite.R
import com.example.belajarsqlite.model.Jadwal
import com.example.belajarsqlite.viewmodel.JadwalViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mJadwalViewModel: JadwalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mJadwalViewModel = ViewModelProvider(this).get(JadwalViewModel::class.java)

        val waktu = args.curJadwal.waktu.split(":").toTypedArray()
        view.edt_update_hari.setText(args.curJadwal.hari)
        view.edt_update_jam.setText(waktu[0])
        view.edt_update_menit.setText(waktu[1])
        view.edt_update_nama_mk.setText(args.curJadwal.mataKuliah)
        view.edt_update_nm_dosen.setText(args.curJadwal.namaDosen)

        view.btn_add.setOnClickListener {
            updateItem()
        }


        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val hari = edt_update_hari.text.toString()
        val jam = edt_update_jam.text.toString()
        val menit = edt_update_menit.text.toString()
        val waktu = jam + ":" + menit
        val mk = edt_update_nama_mk.text.toString()
        val nm_dosen = edt_update_nm_dosen.text.toString()

        if (cekInput(hari, jam, menit, mk, nm_dosen)) {
            val updatedData = Jadwal(args.curJadwal.id, hari, waktu, mk, nm_dosen)
            mJadwalViewModel.updateJadwal(updatedData)

            Toast.makeText(requireContext(), "Data berhasil ter Update", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragment_update_to_fragment_list)
        } else {
            Toast.makeText(requireContext(), "Silahkan isi semua datanya", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun cekInput(
        hari: String,
        jam: String,
        menit: String,
        mk: String,
        dosen: String
    ): Boolean {
        return !(TextUtils.isEmpty(hari) && TextUtils.isEmpty(jam) && TextUtils.isEmpty(menit) &&
                TextUtils.isEmpty(mk) && TextUtils.isEmpty(mk))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_hapus, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) {
            hapusJadwal()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusJadwal() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Iya") { _, _ ->
            mJadwalViewModel.hapusJadwal(args.curJadwal)
            Toast.makeText(
                requireContext(),
                "MK ${args.curJadwal.mataKuliah} berhasil dihapus",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_fragment_update_to_fragment_list)
        }
        builder.setNegativeButton("Tidak"){ _, _ -> }
        builder.setTitle("Hapus ${args.curJadwal.mataKuliah} ?")
        builder.setMessage("Apakah kamu yakin ingin menghapus data ini?")
        builder.create().show()
    }
}