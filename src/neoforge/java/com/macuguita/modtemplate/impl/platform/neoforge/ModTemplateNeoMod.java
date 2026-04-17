package com.macuguita.modtemplate.impl.platform.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("modtemplate")
public class ModTemplateNeoMod {

	public ModTemplateNeoMod(IEventBus modBus) {
		NeoCommonAbstraction.EVENT_BUS = modBus;
		for (var a : NeoCommonAbstraction.INSTANCE.lateActions()) {
			a.accept(modBus);
		}
		NeoCommonAbstraction.INSTANCE.lateActions().clear();
	}
}
