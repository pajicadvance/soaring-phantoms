package me.pajic.soaring_phantoms;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = "soaring_phantoms", bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue DO_ALTITUDE_BASED_SPAWNING = BUILDER
            .translation("text.config.soaring_phantoms.option.doAltitudeBasedSpawning")
            .define("doAltitudeBasedSpawning", true);

    private static final ModConfigSpec.IntValue SPAWN_START_HEIGHT = BUILDER
            .translation("text.config.soaring_phantoms.option.spawnStartHeight")
            .defineInRange("spawnStartHeight", 160, -64, 320);

    private static final ModConfigSpec.IntValue SPAWN_FREQUENCY_BASE = BUILDER
            .translation("text.config.soaring_phantoms.option.spawnFrequencyBase")
            .defineInRange("spawnFrequencyBase", 10, 1, 300);

    private static final ModConfigSpec.IntValue SPAWN_FREQUENCY_RANDOM_OFFSET_BOUND = BUILDER
            .translation("text.config.soaring_phantoms.option.spawnFrequencyRandomOffsetBound")
            .defineInRange("spawnFrequencyRandomOffsetBound", 10, 1, 300);

    private static final ModConfigSpec.BooleanValue PHANTOMS_REPELLED_BY_ITEM = BUILDER
            .translation("text.config.soaring_phantoms.option.phantomsRepelledByItem")
            .define("phantomsRepelledByItem", true);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> REPELLENT_ITEMS = BUILDER
            .translation("text.config.soaring_phantoms.option.repellentItems")
            .defineListAllowEmpty("repellentItems", List.of("minecraft:phantom_membrane"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean doAltitudeBasedSpawning;
    public static int spawnStartHeight;
    public static int spawnFrequencyBase;
    public static int spawnFrequencyRandomOffsetBound;
    public static boolean phantomsRepelledByItem;
    public static Set<Item> repellentItems;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        updateConfig();
    }

    @SubscribeEvent
    static void onReload(final ModConfigEvent.Reloading event) {
        updateConfig();
    }

    private static void updateConfig() {
        doAltitudeBasedSpawning = DO_ALTITUDE_BASED_SPAWNING.get();
        spawnStartHeight = SPAWN_START_HEIGHT.get();
        spawnFrequencyBase = SPAWN_FREQUENCY_BASE.get();
        spawnFrequencyRandomOffsetBound = SPAWN_FREQUENCY_RANDOM_OFFSET_BOUND.get();
        phantomsRepelledByItem = PHANTOMS_REPELLED_BY_ITEM.get();
        repellentItems = REPELLENT_ITEMS.get().stream()
                .map(itemName -> BuiltInRegistries.ITEM.get(
                        //? if <= 1.21.1
                        ResourceLocation.parse(itemName))
                        //? if > 1.21.1
                        /*ResourceLocation.parse(itemName)).orElseThrow().value()*/
                )
                .collect(Collectors.toSet());
    }
}
