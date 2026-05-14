package com.macuguita.modtemplate.impl.platform.neoforge;

import com.macuguita.modtemplate.common.ModTemplate;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ModTemplate.MOD_ID)
public class ModTemplateNeoMod {

	public ModTemplateNeoMod(IEventBus modBus) {
		NeoCommonAbstraction.EVENT_BUS = modBus;
		for (var a : NeoCommonAbstraction.INSTANCE.lateActions()) {
			a.accept(modBus);
		}
		NeoCommonAbstraction.INSTANCE.lateActions().clear();
	}
}
