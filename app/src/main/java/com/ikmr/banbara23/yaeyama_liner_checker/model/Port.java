package com.ikmr.banbara23.yaeyama_liner_checker.model;

/**
 * 運航ルートのEnumクラス
 */
public enum Port {
    HATERUMA("波照間島", "波照間"),
    UEHARA("上原(西表島)", "上原"),
    OOHARA("大原(西表島)", "大原"),
    HATOMA("鳩間島", "鳩間"),
    KUROSHIMA("黒島", "黒島"),
    KOHAMA("小浜島", "小浜"),
    TAKETOMI("竹富島", "竹富"),
    HATOMA_UEHARA("鳩間島・上原(西表島)", "鳩間島経由西表島・上原"),
    PREMIUM_DREAM("プレミアムドリーム", "プレミアムドリーム"),
    SUPER_DREAM("スーパードリーム", "スーパードリーム");

    // 表示名
    private String port;
    // 検索用の名前
    private String portSimple;

    /**
     * コンストラクタ
     *
     * @param port
     * @param portSimple
     */
    Port(String port, String portSimple) {
        this.port = port;
        this.portSimple = portSimple;
    }

    public String getValue() {
        return port;
    }

    public String getPort() {
        return port;
    }

    public String getPortSimple() {
        return portSimple;
    }

}
