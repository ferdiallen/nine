package com.example.nineintelligence.domain.models

data class TryoutInfoDetail(
    val soalTypeList: TypeListModel? = null, val mapelList: MapelModel? = null
) {
    data class TypeListModel(
        val tps: Int? = null, val literasi: Int? = null
    )

    data class MapelModel(
        val penalaran: Int? = null,
        val pengetahuanUmum: Int? = null,
        val membacaMenulis: Int? = null,
        val memahamiBacaanDanMenulis: Int? = null,
        val literasiBahasaIndonesia: Int? = null,
        val literasiBahasaInggris: Int? = null,
        val penalaranMatematika: Int? = null,
    )

}

fun calculateMapelAndData(data: List<GetSoalModel>): TryoutInfoDetail {
    val soalType = TryoutInfoDetail.TypeListModel(data.filter {
        it.type == 1
    }.size, data.filter {
        it.type == 2
    }.size)
    val mapelList = TryoutInfoDetail.MapelModel(
        data.filter {
            it.mapel == 1
        }.size,
        data.filter {
            it.mapel == 2
        }.size,
        data.filter {
            it.mapel == 3
        }.size,
        data.filter {
            it.mapel == 4
        }.size,
        data.filter {
            it.mapel == 5
        }.size,
        data.filter {
            it.mapel == 6
        }.size,
        data.filter {
            it.mapel == 7
        }.size,
    )
    return TryoutInfoDetail(soalType, mapelList)
}