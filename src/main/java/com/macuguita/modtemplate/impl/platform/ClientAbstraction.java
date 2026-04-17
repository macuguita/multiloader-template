package com.macuguita.modtemplate.impl.platform;

import net.minecraft.util.Util;

public interface ClientAbstraction {

	CommonAbstraction INSTANCE = Util.make(() -> {
		try {
			return (CommonAbstraction) Class.forName(
				"com.macuguita.modtemplate.impl.platform." +
					(CommonAbstraction.IS_FABRIC ? "fabric.FabricClientAbstraction" : "neoforge.NeoClientAbstraction")).getField("INSTANCE").get(null);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	});

	static CommonAbstraction get() {
		return INSTANCE;
	}
}
