# Soaring Phantoms

This mod changes how phantom spawning works. Phantoms now spawn only at high altitudes at night, regardless of whether you slept or not. Holding a phantom membrane in your hands prevents all phantom spawns. The goal is to replace the notoriously annoying vanilla spawn mechanics with non-intrusive and reliable spawn mechanics.

Fabric version requires [Fabric API](https://modrinth.com/mod/fabric-api) and [Fzzy Config](modrinth.com/mod/fzzy-config). Use [Mod Menu](https://modrinth.com/mod/modmenu) to change settings in-game.

NeoForge version has no dependencies. Use the Mods screen to change settings in-game.

## Features

### Altitude-based phantom spawning
When enabled, the game attempts to spawn phantoms around the player at night time and when they're above the defined starting Y height. The chance that the spawn attempt will succeed is based on how much higher the player is above the starting Y height.
- Replaces the vanilla insomnia-based phantom spawning mechanic
- Setting the `doInsomnia` game rule to `false` still disables all phantom spawns
- Phantoms still only spawn at night, and still won't spawn if there is a block above the player that blocks light
- The chance of the spawn attempt succeeding is the following: `1 - (startingYHeight / playerYPosition)`

Configuration options:
- Toggle altitude-based spawning on or off
- Set the starting Y height for spawning (default 160)
- Set the frequency at which the spawn check will run, in seconds (default 30 + random number from 0 to 29)

### Passive phantoms before the Ender Dragon
When enabled, phantoms won't attack the player until the player provokes them, and will spawn above the passive spawn start height, which is lower than the normal spawn start height by default. After defeating the Ender Dragon, phantoms will return to being aggressive on spawn. Phantoms which spawn in the End are always aggressive by default.

This feature is **disabled** by default.

Configuration options:
- Toggle the feature on or off
- Set the starting Y height for passive spawning (default 96)
- Control whether phantoms spawned in the End should be always aggressive or not

### Repel phantom spawns with defined items
When enabled, phantoms will be unable to spawn if the player is holding any item defined as a repellent item in their main hand or off-hand.

Configuration options:
- Toggle phantom repelling on or off
- Add new items to the phantom repellent item list (default: `minecraft:phantom_membrane`)
