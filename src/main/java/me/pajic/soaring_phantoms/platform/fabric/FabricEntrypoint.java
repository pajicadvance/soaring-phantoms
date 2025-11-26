package me.pajic.soaring_phantoms.platform.fabric;

//? fabric {

import me.pajic.soaring_phantoms.SoaringPhantoms;
import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		SoaringPhantoms.onInitialize();
	}
}
//?}
