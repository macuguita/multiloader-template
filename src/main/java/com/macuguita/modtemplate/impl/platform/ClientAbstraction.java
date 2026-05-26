package com.macuguita.modtemplate.impl.platform;

import net.minecraft.util.Util;

public interface ClientAbstraction {

	ClientAbstraction INSTANCE = Util.make(() -> {
		try {
			return (ClientAbstraction) Class.forName(
				"com.macuguita.modtemplate.impl.platform." +
					(CommonAbstraction.IS_FABRIC ? "fabric.FabricClientAbstraction" : "neoforge.NeoClientAbstraction")).getField("INSTANCE").get(null);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	});

	static ClientAbstraction get() {
		return INSTANCE;
	}
}
