package me.pajic.soaringphantoms.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Expanded;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;
import io.wispforest.owo.config.annotation.SectionHeader;

import java.util.List;

@Modmenu(modId = "soaring-phantoms")
@Config(name = "soaring-phantoms-config", wrapperName = "Config")
public class ConfigModel {
    @SectionHeader("altitude-based-spawning")
    public boolean doAltitudeBasedSpawning = true;
    public int spawnStartHeight = 160;
    @RangeConstraint(min = 1, max = 300)
    public int spawnFrequencyBase = 60;
    @RangeConstraint(min = 1, max = 300)
    public int spawnFrequencyRandomOffsetBound = 60;

    @SectionHeader("repel-phantoms")
    public boolean phantomsRepelledByItem = true;
    @Expanded
    public List<String> repellentItems = List.of("minecraft:phantom_membrane");
}
