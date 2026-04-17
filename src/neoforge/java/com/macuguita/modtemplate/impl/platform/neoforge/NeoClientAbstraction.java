package com.macuguita.modtemplate.impl.platform.neoforge;

import com.macuguita.modtemplate.impl.platform.ClientAbstraction;

public record NeoClientAbstraction() implements ClientAbstraction {
	public static final NeoClientAbstraction INSTANCE = new NeoClientAbstraction();
}
