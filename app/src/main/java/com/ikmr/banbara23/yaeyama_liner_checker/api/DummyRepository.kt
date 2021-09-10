package com.ikmr.banbara23.yaeyama_liner_checker.api

import com.ikemura.shared.model.tyhoon.Typhoon

object DummyRepository {

    private fun makeDummyData(): List<Typhoon> =
        listOf(
            Typhoon(
                name = "台風18号(ミートク)",
                dateTime = "01日15:00現在",
                img = "https://static.tenki.jp/static-images/typhoon-detail/typhoon_1918/2019/10/01/typhoon_1918_2019-10-01-15-00-00-middle.jpg",
                scale = "---",
                intensity = "---",
                pressure = "980hPa",
                area = "東シナ海",
                maxWindSpeedNearCenter = "30m/s"
            ),
            Typhoon(
                name = "台風19号(タマーキ)",
                dateTime = "02日15:00現在",
                img = "https://static.tenki.jp/static-images/typhoon-detail/typhoon_1918/2019/10/01/typhoon_1918_2019-10-01-15-00-00-middle.jpg",
                scale = "大型",
                intensity = "強い",
                pressure = "981hPa",
                area = "東シナ海",
                maxWindSpeedNearCenter = "40m/s"
            ),
            Typhoon(
                name = "台風20号(シバーム)",
                dateTime = "03日15:00現在",
                img = "https://static.tenki.jp/static-images/typhoon-detail/typhoon_1918/2019/10/01/typhoon_1918_2019-10-01-15-00-00-middle.jpg",
                scale = "超ド級",
                intensity = "ヤバイ",
                pressure = "982hPa",
                area = "東シナ海",
                maxWindSpeedNearCenter = "40m/s"
            )
        )
}
