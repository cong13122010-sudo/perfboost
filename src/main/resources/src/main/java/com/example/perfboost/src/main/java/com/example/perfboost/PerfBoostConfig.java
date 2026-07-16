package com.example.perfboost;

public final class PerfBoostConfig {
    private PerfBoostConfig() {}

    // Trong pham vi nay, entity luon tick binh thuong (khong throttle) de tranh giat/lag hanh vi
    public static final double SAFE_DISTANCE = 12.0;
    public static final double SAFE_DISTANCE_SQ = SAFE_DISTANCE * SAFE_DISTANCE;

    // Xa nhung van trong tam nhin -> tick moi 2 lan
    public static final int INTERVAL_FAR_VISIBLE = 2;

    // Xa VA ngoai tam nhin -> giam tai manh nhat, tick moi 4 lan
    public static final int INTERVAL_FAR_HIDDEN = 4;

    // dot product > gia tri nay = coi la dang trong tam nhin (~60 do nua goc)
    public static final double FOV_DOT_THRESHOLD = 0.5;

    public static int calculateTickInterval(double distSq, boolean inView) {
        if (distSq <= SAFE_DISTANCE_SQ) return 1;
        return inView ? INTERVAL_FAR_VISIBLE : INTERVAL_FAR_HIDDEN;
    }
}
