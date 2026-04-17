package com.macuguita.modtemplate.impl.platform.fabric;

import net.minecraft.resources.Identifier; // sample_content
import net.minecraft.server.packs.PackType; // sample_content
import net.minecraft.server.packs.resources.PreparableReloadListener; // sample_content
// sample_content
import net.fabricmc.fabric.api.resource.v1.ResourceLoader; // sample_content
// sample_content
import com.macuguita.modtemplate.impl.platform.CommonAbstraction;

public record FabricCommonAbstraction() implements CommonAbstraction {
	public static final FabricCommonAbstraction INSTANCE = new FabricCommonAbstraction();
	// sample_content
	@Override// sample_content
	public void registerServerReloadListener(Identifier identifier, PreparableReloadListener instance, Identifier... requires) {// sample_content
		var loader = ResourceLoader.get(PackType.SERVER_DATA);// sample_content
		loader.registerReloadListener(identifier, instance);// sample_content
		// sample_content
		for (var r : requires) {// sample_content
			loader.addListenerOrdering(r, identifier);// sample_content
		}// sample_content
	}// sample_content
}
