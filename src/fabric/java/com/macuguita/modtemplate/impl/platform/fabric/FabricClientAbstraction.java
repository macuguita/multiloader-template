package com.macuguita.modtemplate.impl.platform.fabric;

import com.macuguita.modtemplate.impl.platform.ClientAbstraction;

public record FabricClientAbstraction() implements ClientAbstraction {
	public static final FabricClientAbstraction INSTANCE = new FabricClientAbstraction();
}
