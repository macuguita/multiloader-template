package com.macuguita.modtemplate.common;

import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macuguita.modtemplate.impl.platform.CommonAbstraction;

public class ModTemplate implements ModInitializer {
	public static final String MOD_ID = "modtemplate";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello, {}!", CommonAbstraction.IS_FABRIC ? "fabric" : "neoforge");
	}
}
