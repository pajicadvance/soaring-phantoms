package me.pajic.soaring_phantoms.platform.fabric;

//? fabric {

import me.pajic.soaring_phantoms.SoaringPhantoms;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		SoaringPhantoms.onInitialize();
	}
}
//?}
