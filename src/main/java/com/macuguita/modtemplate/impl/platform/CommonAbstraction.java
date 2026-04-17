package com.macuguita.modtemplate.impl.platform;

import dev.yumi.mc.core.api.YumiMods;
// sample_content
import net.minecraft.resources.Identifier; // sample_content
import net.minecraft.server.packs.resources.PreparableReloadListener; // sample_content
import net.minecraft.util.Util;

public interface CommonAbstraction {
	boolean IS_FABRIC = YumiMods.get().isModLoaded("fabricloader") && !YumiMods.get().isModLoaded("connector");

	CommonAbstraction INSTANCE = Util.make(() -> {
		try {
			return (CommonAbstraction) Class.forName(
				"com.macuguita.modtemplate.impl.platform." +
					(CommonAbstraction.IS_FABRIC ? "fabric.FabricCommonAbstraction" : "neoforge.NeoCommonAbstraction")).getField("INSTANCE").get(null);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	});

	static CommonAbstraction get() {
		return INSTANCE;
	}
	// sample_content
	void registerServerReloadListener(Identifier identifier, PreparableReloadListener instance, Identifier... requires); // sample_content
}
