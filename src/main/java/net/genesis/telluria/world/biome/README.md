# Adding Biomes to Telluria

Biomes in 1.18+ are weird, **terrablender** helps with that.

To add a biome the following steps must be taken:

## 1
  Add the biome to `TelluriaBiomes`, look at `SALT_MARSH` as an example.

## 2
  Add the biome to `TelluraOverworldBiomes`, Look at `salt_marsh` as an example.

## 3
  Add the biome surface parameters to `TelluriaSurfaceRuleData`, Look at `SALT_MARSH`'s surface as an example.

## 4
  Add the biome to `TelluriaRegions` by replacing a vanilla biome, appending noise values to a vanilla biome, or by adding the biome as new.

## 5
  Register the biome in `telluriaBiomeRegistry` and make sure to use the same name throughout all classes.

###### Your biome should now work properly
  If there are any errors look below for solutions.





# Fixing Biome Issues

## #1 - Incorect namespace/path
  The number 1 issue I have found while making a biome is not using the same namespace or path `such as ResourceLocation(TelluriaMod.MOD_ID, "salt_mrash")` throughout all classes, this is very important, it **will** affect how minecraft registers and references your biome. 

## #2 - Improper Feature Ordering 
  The second issue I have found while making a biome is the feature cycle. The feature cycle determines how Minecraft will place features `such as minecraft:grass_vegitation` in your biome. It is very important that biome features are registered respecting the feature cycle. Each stage in the feature coorespondes to a world generation pass.

  The features are generated in the feature cycle as follows:

    0    [],    RAW_GENERATION          - (used for tiny end island features)

    1    [],    LAKES                   - (used for lave lakes and water lakes)

    2    [],    LOCAL_MODIFICATIONS     - (used for rocks in taiga and icebergs)

    3    [],    UNDERGROUND_STRUCTRUES  - (used for features such as geodes, dungeons, and fossils)

    4    [],    SURFACE_STRUCTURES      - (desert wells and blue ice patches)

    5    [],    STRONGHOLDS             - (not used by any features, and thus should be **ignored**)

    6    [],    UNDERGROUND_ORES        - (used for ores, gravel blobs, clay disks, etc)

    7    [],    UNDERGROUND_DECORATION  - (Used for blobs of infested blocks, nether gravel, and all nether ores)

    8    [],    FLUID_SPRINGS           - (Used for water and lava springs)

    9    [],    VEGETAL_DECORATION      - (Used for trees, grass, cacti, kelp and other ground or underwater vegitation)

    10   []     TOP_LEVEL_MODIFICATION  - (Used exclusively for freezing water surfaces)

  All empty brackets **after** the last bracket used can be deleted, but I recommend keeping all of them to keep track of what they are

  Features must be listed in order even if you are registering them with terrablender.

## #3 - Categories
  A small issue I found was that Biome.Category is depricated and no longer exists, this is because it was depricated in 1.18 and then removed in 1.19. Remove all instances of catergories in bimoes when creating.