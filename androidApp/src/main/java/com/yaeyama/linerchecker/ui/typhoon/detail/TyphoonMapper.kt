package com.yaeyama.linerchecker.ui.typhoon.detail

import com.yaeyama.linerchecker.domain.typhoon.Typhoon

/**
 * DomainのTyphoonモデルをUIのTyphoonDetailUiModelに変換する
 */
fun Typhoon.toTyphoonDetailUiModel(): TyphoonDetailUiModel {
    return TyphoonDetailUiModel(
        name = this.name,
        dateTime = this.dateTime,
        img = this.img,
        scale = this.scale,
        intensity = this.intensity,
        pressure = this.pressure,
        area = this.area,
        maxWindSpeedNearCenter = this.maxWindSpeedNearCenter
    )
}