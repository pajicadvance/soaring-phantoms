package me.pajic.soaring_phantoms;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

@Version(version = 1)
public class ModConfig extends Config {
    public ModConfig() {
        super(ResourceLocation.fromNamespaceAndPath("soaring_phantoms", "config"));
    }

    public ValidatedBoolean doAltitudeBasedSpawning = new ValidatedBoolean(true);
    public ValidatedInt spawnStartHeight = new ValidatedInt(160, 320, -64);
    public ValidatedInt spawnFrequencyBase = new ValidatedInt(10, 300, 1);
    public ValidatedInt spawnFrequencyRandomOffsetBound = new ValidatedInt(10, 300, 1);
    public ValidatedBoolean phantomsRepelledByItem = new ValidatedBoolean(true);
    public ValidatedList<ResourceLocation> repellentItems = ValidatedIdentifier.ofRegistry(
            ResourceLocation.withDefaultNamespace("phantom_membrane"),
            BuiltInRegistries.ITEM
    ).toList(
            ResourceLocation.withDefaultNamespace("phantom_membrane")
    );
}
