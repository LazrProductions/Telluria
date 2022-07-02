
# Adding Features to Telluria

Features can be a bit confusing at first, but they are not that hard to understand.

Adding features can be done in a few steps.

## 1 Registering the Configured Feature
Features must first be registered inside of `TelluriaConfiguredFeatures`, use a public static final of type `Holder<ConfiguredFeature>` to store the configured feature to be used. There are quite a few of vanilla feature options available, to look into them `ctrl+click` `PlacedFeature or Configured Feature` to look through vanilla features. If your feature should behave uniquely, a custom feature class must be made in `net.genesis.telluria.world.feature.util.custom` see `CattailsFeature` as an example.

## 2 Registering the Placed Feature
After creating a configured feature inside of `TelluriaConfiguredFeatures` you can now register a placed feature, to place the feature in the world. To do so, Use a public static final of type `Holder<PlacedFeature>` and make sure to register the feature with a unique identifier.

## 3 Placing the feature
Features can be placed in the world in a number of ways, for testing you can use the command `/place feature "feature_name"`. To spawn your feature in existing biomes, or where-ever, use an `eventBus` to inject the feature into the biome registries. See `https://www.youtube.com/watch?v=oCu1tF5s8ug` for a tutorial on generating features in vanilla biomes. To spawn your feature in custom biomes you can add the feature within the biome declaration. See the `saltMarsh` biome as an example. **Make add the feature respective to the feature order cycle.**


## All done!
At this point your features *should* be generating properly! If you are having issues look below for possible solutions.  





# Fixing Feature Issues

## #1 - Improper Feature Ordering 
  The most common issue I have found while adding a feature to a custom biome is the feature cycle. The feature cycle determines how Minecraft will place features `such as minecraft:grass_vegitation` in your biome. It is very important that biome features are registered respecting the feature cycle. Each stage in the feature coorespondes to a world generation pass.

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

## #2 Duplicate Identifiers
More often than not, if crashes are occuring during world generation, than you may have a duplicate feature or identifier for a feature. Make sure that all `ConfiguredFeature` and `PlacedFeature` have unique indentifiers `such as bulrush_placed`.
