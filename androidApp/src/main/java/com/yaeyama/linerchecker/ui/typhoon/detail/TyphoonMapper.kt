package com.yaeyama.linerchecker.ui.typhoon.detail

import com.yaeyama_liner_checker.domain.tyhoon.Typhoon

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