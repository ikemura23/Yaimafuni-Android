package com.ikmr.banbara23.yaeyama_liner_checker.model;

import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable;

/**
 * ステータス詳細を表示するルートモデル
 * <p>
 * RxJavaのzipで使うために作成した
 */
public class StatusDetailRoot {
    private PortStatus mPortStatus;
    private DetailLinerInfo mDetailLinerInfo;
    private TimeTable mTimeTable;

    public StatusDetailRoot(PortStatus portStatus, DetailLinerInfo detailLinerInfo, TimeTable timeTable) {
        mPortStatus = portStatus;
        mDetailLinerInfo = detailLinerInfo;
        mTimeTable = timeTable;
    }

    public PortStatus getPortStatus() {
        return mPortStatus;
    }

    public DetailLinerInfo getDetailLinerInfo() {
        return mDetailLinerInfo;
    }

    public TimeTable getTimeTable() {
        return mTimeTable;
    }
}
