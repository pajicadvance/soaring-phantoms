package me.pajic.soaring_phantoms.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.pajic.soaring_phantoms.SoaringPhantoms;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

@Version(version = 1)
public class ModConfig extends Config {

    public ModConfig() {
        super(Identifier.fromNamespaceAndPath(SoaringPhantoms.MOD_ID, "config"));
    }

	public ValidatedBoolean doAltitudeBasedSpawning = new ValidatedBoolean(true);
	public ValidatedBoolean passivePhantomsBeforeEnderDragon = new ValidatedBoolean(false);
	public ValidatedBoolean alwaysAggressiveInEnd = new ValidatedBoolean(true);
	public ValidatedInt spawnStartHeight = new ValidatedInt(160, 320, -64);
	public ValidatedInt passiveSpawnStartHeight = new ValidatedInt(96, 320, -64);
	public ValidatedInt spawnFrequencyBase = new ValidatedInt(20, 300, 1);
	public ValidatedInt spawnFrequencyRandomOffsetBound = new ValidatedInt(20, 300, 1);
	public ValidatedBoolean phantomsRepelledByItem = new ValidatedBoolean(true);
	public ValidatedList<Identifier> repellentItems = ValidatedIdentifier.ofRegistry(
			Identifier.withDefaultNamespace("phantom_membrane"),
			BuiltInRegistries.ITEM
	).toList(
			Identifier.withDefaultNamespace("phantom_membrane")
	);
}
