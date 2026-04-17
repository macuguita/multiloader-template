package com.macuguita.modtemplate.impl.platform.neoforge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.jspecify.annotations.Nullable;
// sample_content
import net.minecraft.resources.Identifier; // sample_content
import net.minecraft.server.packs.resources.PreparableReloadListener; // sample_content
// sample_content
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge; // sample_content
import net.neoforged.neoforge.event.AddServerReloadListenersEvent; // sample_content

import com.macuguita.modtemplate.impl.platform.CommonAbstraction;

public record NeoCommonAbstraction(List<Consumer<IEventBus>> lateActions) implements CommonAbstraction {
	public static @Nullable IEventBus EVENT_BUS = null;
	public static final NeoCommonAbstraction INSTANCE = new NeoCommonAbstraction(new ArrayList<>());
	// sample_content
	@Override
	public void registerServerReloadListener(Identifier identifier, PreparableReloadListener instance, Identifier... requires) { // sample_content
		NeoForge.EVENT_BUS.addListener(AddServerReloadListenersEvent.class, e -> { // sample_content
			e.addListener(identifier, instance); // sample_content
			// sample_content
			for (var r : requires) { // sample_content
				e.addDependency(r, identifier); // sample_content
			} // sample_content
		}); // sample_content
	} // sample_content

	public void addLateAction(Consumer<IEventBus> consumer) {
		if (EVENT_BUS != null) {
			consumer.accept(EVENT_BUS);
		} else {
			this.lateActions.add(consumer);
		}
	}
}
