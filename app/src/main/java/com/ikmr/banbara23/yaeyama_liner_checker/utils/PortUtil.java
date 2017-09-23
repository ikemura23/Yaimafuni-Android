
package com.ikmr.banbara23.yaeyama_liner_checker.utils;

import android.content.Context;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Price;

import java.util.List;

/**
 * 港のutilクラス
 */
public class PortUtil {

    /**
     * 引数で渡された港の詳細のurlを取得する
     *
     * @param context
     * @param port
     * @return
     */
    public static String getAnneiDetailUrl(Context context, Port port) {

        switch (port) {
            case HATERUMA:
                return context.getString(R.string.url_annei_hateruma);
            case UEHARA:
                return context.getString(R.string.url_annei_uehara);
            case OOHARA:
                return context.getString(R.string.url_annei_oohara);
            case HATOMA:
                return context.getString(R.string.url_annei_hateruma);
            case KUROSHIMA:
                return context.getString(R.string.url_annei_kuroshima);
            case KOHAMA:
                return context.getString(R.string.url_annei_kohama);
            case TAKETOMI:
                return context.getString(R.string.url_annei_taketomi);
            default:
                return null;
        }
    }

    /**
     * 港の配列から希望する港を取得する
     *
     * @param liners 一覧データ
     * @param pot    希望する港
     * @return 欲しい港
     */
    public static Liner getMyPort(List<Liner> liners, Port pot) {
        for (Liner liner : liners) {
            if (pot == liner.getPort()) {
                return liner;
            }
        }
        return null;
    }

    /**
     * 安栄の料金を返す
     *
     * @param context
     * @param port
     * @return
     */
    public static Price getAnneiPrice(Context context, Port port) {
        Price price = new Price();
        price.setAdult(getAnneiAdultPrice(context, port));
        price.setChild(getAnneiChildPrice(context, port));
        price.setHandicapped(getAnneiHandicappedPrice(context, port));
        return price;
    }

    /**
     * 安栄の大人料金を返す
     *
     * @param context
     * @param port    港
     * @return 料金
     */
    public static String getAnneiAdultPrice(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_price_adult);
            case UEHARA:
                return context.getString(R.string.annei_uehara_price_adult);
            case OOHARA:
                return context.getString(R.string.annei_oohara_price_adult);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_price_adult);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_price_adult);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_price_adult);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_price_adult);
            default:
                return null;
        }
    }

    /**
     * 安栄の子供料金を返す
     *
     * @param context
     * @param port    港
     * @return 料金
     */
    public static String getAnneiChildPrice(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_price_child);
            case UEHARA:
                return context.getString(R.string.annei_uehara_price_child);
            case OOHARA:
                return context.getString(R.string.annei_oohara_price_child);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_price_child);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_price_child);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_price_child);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_price_child);
            default:
                return null;
        }
    }

    /**
     * 安栄の障害者料金を返す
     *
     * @param context
     * @param port    港
     * @return 料金
     */
    public static String getAnneiHandicappedPrice(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_price_handicapped);
            case UEHARA:
                return context.getString(R.string.annei_uehara_price_handicapped);
            case OOHARA:
                return context.getString(R.string.annei_oohara_price_handicapped);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_price_handicapped);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_price_handicapped);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_price_handicapped);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_price_handicapped);
            default:
                return null;
        }
    }

    /**
     * 安栄の走行距離を返す
     *
     * @param context コンテキスト
     * @param port    港
     * @return 時間
     */
    public static String getAnneiDistance(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_distance);
            case UEHARA:
                return context.getString(R.string.annei_uehara_distance);
            case OOHARA:
                return context.getString(R.string.annei_oohara_distance);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_distance);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_distance);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_distance);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_distance);
            default:
                return null;
        }
    }

    /**
     * 安栄の走行距離を返す
     *
     * @param context コンテキスト
     * @param port    港
     * @return 時間
     */
    public static String getAnneiTime(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_time);
            case UEHARA:
                return context.getString(R.string.annei_uehara_time);
            case OOHARA:
                return context.getString(R.string.annei_oohara_time);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_time);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_time);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_time);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_time);
            default:
                return null;
        }
    }

    public static String getYkfTime(Context context, Port port) {
        switch (port) {
            case UEHARA:
                return context.getString(R.string.ykf_uehara_time);
            case OOHARA:
                return context.getString(R.string.ykf_oohara_time);
            case HATOMA:
                return context.getString(R.string.ykf_hatoma_time);
            case KUROSHIMA:
                return context.getString(R.string.ykf_kuroshima_time);
            case KOHAMA:
                return context.getString(R.string.ykf_kohama_time);
            case TAKETOMI:
                return context.getString(R.string.ykf_taketomi_time);
            default:
                return null;
        }
    }

    public static String getDreamTime(Context context, Port port) {
        switch (port) {
            case HATOMA_UEHARA:
                return context.getString(R.string.dream_uehara_time);
            case OOHARA:
                return context.getString(R.string.dream_oohara_time);
            case KUROSHIMA:
                return context.getString(R.string.dream_kuroshima_time);
            case KOHAMA:
                return context.getString(R.string.dream_kohama_time);
            case TAKETOMI:
                return context.getString(R.string.dream_taketomi_time);
            default:
                return null;
        }
    }

    public static Price getYkfPrice(Context context, Port port) {
        Price price = new Price();
        price.setAdult(getYkfPriceAdult(context, port));
        price.setChild(getYkfPriceChild(context, port));
        price.setHandicapped(null);
        return price;
    }

    public static String getYkfPriceAdult(Context context, Port port) {
        switch (port) {
            case UEHARA:
                return context.getString(R.string.ykf_uehara_price_adult);
            case OOHARA:
                return context.getString(R.string.ykf_oohara_price_adult);
            case HATOMA:
                return context.getString(R.string.ykf_hatoma_price_adult);
            case KUROSHIMA:
                return context.getString(R.string.ykf_kuroshima_price_adult);
            case KOHAMA:
                return context.getString(R.string.ykf_kohama_price_adult);
            case TAKETOMI:
                return context.getString(R.string.ykf_taketomi_price_adult);
            default:
                return null;
        }
    }

    public static String getYkfPriceChild(Context context, Port port) {
        switch (port) {
            case UEHARA:
                return context.getString(R.string.ykf_uehara_price_child);
            case OOHARA:
                return context.getString(R.string.ykf_oohara_price_adult);
            case HATOMA:
                return context.getString(R.string.ykf_hatoma_price_adult);
            case KUROSHIMA:
                return context.getString(R.string.ykf_kuroshima_price_adult);
            case KOHAMA:
                return context.getString(R.string.ykf_kohama_price_adult);
            case TAKETOMI:
                return context.getString(R.string.ykf_taketomi_price_adult);
            default:
                return null;
        }
    }

    /**
     * ドリーム 高速船 料金
     *
     * @param context
     * @param port
     * @return
     */
    public static Price getDreamLinerPrice(Context context, Port port) {
        Price price = new Price();
        price.setAdult(getDreamLinerPriceAdult(context, port));
        price.setChild(getDreamLinerPriceChile(context, port));
        price.setHandicapped(null);
        return price;
    }

    private static String getDreamLinerPriceAdult(Context context, Port port) {
        switch (port) {
            case TAKETOMI:
                return context.getString(R.string.dream_taketomi_price_adult);
            case KOHAMA:
                return context.getString(R.string.dream_kohama_price_adult);
            case KUROSHIMA:
                return context.getString(R.string.dream_kuroshima_price_adult);
            case OOHARA:
                return context.getString(R.string.dream_oohara_price_adult);
            default:
                return null;
        }
    }

    private static String getDreamLinerPriceChile(Context context, Port port) {
        switch (port) {
            case TAKETOMI:
                return context.getString(R.string.dream_taketomi_price_child);
            case KOHAMA:
                return context.getString(R.string.dream_kohama_price_child);
            case KUROSHIMA:
                return context.getString(R.string.dream_kuroshima_price_child);
            case OOHARA:
                return context.getString(R.string.dream_oohara_price_child);
            default:
                return null;
        }
    }

    /**
     * ドリーム フェリー 料金
     *
     * @param context
     * @param port
     * @return
     */
    public static Price getDreamFerryPrice(Context context, Port port) {
        Price price = new Price();
        price.setAdult(getDreamFerryPriceAdult(context, port));
        price.setChild(getDreamFerryPriceChile(context, port));
        price.setHandicapped(null);
        return price;
    }

    private static String getDreamFerryPriceAdult(Context context, Port port) {
        switch (port) {
            case TAKETOMI:
                return context.getString(R.string.dream_taketomi_price_adult_ferry);
            case KOHAMA:
                return context.getString(R.string.dream_kohama_price_adult_ferry);
            case KUROSHIMA:
                return context.getString(R.string.dream_kuroshima_price_adult_ferry);
            case OOHARA:
                return context.getString(R.string.dream_oohara_price_adult_ferry);
            case HATOMA_UEHARA:
                return context.getString(R.string.dream_uehara_price_adult_ferry);
            default:
                return null;
        }
    }

    private static String getDreamFerryPriceChile(Context context, Port port) {
        switch (port) {
            case TAKETOMI:
                return context.getString(R.string.dream_taketomi_price_child_ferry);
            case KOHAMA:
                return context.getString(R.string.dream_kohama_price_child_ferry);
            case KUROSHIMA:
                return context.getString(R.string.dream_kuroshima_price_child_ferry);
            case OOHARA:
                return context.getString(R.string.dream_oohara_price_child_ferry);
            case HATOMA_UEHARA:
                return context.getString(R.string.dream_uehara_price_child_ferry);
            default:
                return null;
        }
    }

    public static String getRouteName(Context context, Port port) {
        switch (port) {
            case TAKETOMI:
                return context.getString(R.string.route_taketomi);
            case KOHAMA:
                return context.getString(R.string.route_kohama);
            case KUROSHIMA:
                return context.getString(R.string.route_kuroshima);
            case OOHARA:
                return context.getString(R.string.route_oohara);
            case UEHARA:
                return context.getString(R.string.route_uehara);
            case HATERUMA:
                return context.getString(R.string.route_hateruma);
            case HATOMA:
                return context.getString(R.string.route_hatoma);
            case HATOMA_UEHARA:
                return context.getString(R.string.route_uehara_hatoma);
            case SUPER_DREAM:
                return port.getPort();
            case PREMIUM_DREAM:
                return port.getPort();
            default:
                return null;
        }
    }
}
