package me.pajic.soaring_phantoms;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.soaring_phantoms.config.ModConfig;

@SuppressWarnings("LoggingSimilarMessage")
public class SoaringPhantoms {

	public static final String MOD_ID = /*$ mod_id*/ "soaring_phantoms";
	public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);

	public static void onInitialize() {
	}
}
