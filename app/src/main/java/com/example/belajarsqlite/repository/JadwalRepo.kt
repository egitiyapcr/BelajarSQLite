package com.example.belajarsqlite.repository

import androidx.lifecycle.LiveData
import com.example.belajarsqlite.data.JadwalDAO
import com.example.belajarsqlite.model.Jadwal

class JadwalRepo(private val jadwalDAO: JadwalDAO) {

    val bacaSemuaData: LiveData<List<Jadwal>> = jadwalDAO.bacaSemuaData()

    suspend fun tambahJadwal(jadwal: Jadwal){
        jadwalDAO.tambahJadwal(jadwal)
    }

    suspend fun updateJadwal(jadwal: Jadwal){
        jadwalDAO.updateJadwal(jadwal)
    }

    suspend fun hapusJadwal(jadwal: Jadwal){
        jadwalDAO.hapusJadwal(jadwal)
    }

    suspend fun hapusSemuaJadwal(){
        jadwalDAO.hapusSemua()
    }
}