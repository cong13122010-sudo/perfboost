package com.example.perfboost;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfBoostMod implements ModInitializer {
    public static final String MOD_ID = "perfboost";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[PerfBoost] Da kich hoat toi uu hieu suat.");
    }
}
