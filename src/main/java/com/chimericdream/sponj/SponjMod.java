package com.chimericdream.sponj;

import com.chimericdream.sponj.block.LavaSponjBlock;
import com.chimericdream.sponj.block.SponjBlock;
import com.chimericdream.sponj.block.WetLavaSponjBlock;
import com.chimericdream.sponj.block.WetSponjBlock;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SponjMod implements ModInitializer {
	public static final Logger LOGGER;

	public static final SponjBlock SPONJ;
	public static final LavaSponjBlock LAVA_SPONJ;
	public static final WetSponjBlock WET_SPONJ;
	public static final WetLavaSponjBlock WET_LAVA_SPONJ;

	static {
		LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);

		SPONJ = new SponjBlock();
		LAVA_SPONJ = new LavaSponjBlock();
		WET_SPONJ = new WetSponjBlock();
		WET_LAVA_SPONJ = new WetLavaSponjBlock();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("[sponj] Registering blocks");
		SPONJ.register();
		LAVA_SPONJ.register();
		WET_SPONJ.register();
		WET_LAVA_SPONJ.register();
	}
}
