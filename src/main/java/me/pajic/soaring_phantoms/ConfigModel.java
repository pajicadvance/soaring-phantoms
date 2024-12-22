package me.pajic.soaring_phantoms;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

import java.util.List;

@Modmenu(modId = "soaring_phantoms")
@Config(name = "soaring_phantoms", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {

    @SectionHeader("altitude-based-spawning")
    public boolean doAltitudeBasedSpawning = true;
    @RangeConstraint(min = -64, max = 320) public int spawnStartHeight = 160;
    @RangeConstraint(min = 1, max = 300) public int spawnFrequencyBase = 10;
    @RangeConstraint(min = 1, max = 300) public int spawnFrequencyRandomOffsetBound = 10;

    @SectionHeader("repel-phantoms")
    public boolean phantomsRepelledByItem = true;
    @Expanded public List<String> repellentItems = List.of("minecraft:phantom_membrane");
}
