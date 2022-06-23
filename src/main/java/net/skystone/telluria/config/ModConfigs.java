package net.skystone.telluria.config;

import net.skystone.telluria.TelluriaMod;




public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    ///// State Config Values here
    
    //public static int EXAMPLECONFIGSETTING; //can be string, double, or int

    /////
    
    ///// IMPORTANT
    ///// when adding/removing config variables, delete the config file in /run/configs

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(TelluriaMod.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        //configs.addKeyValuePair(new Pair<>("telluria.example", 0), "comemnt usually state the type here");
    }

    private static void assignConfigs() {
        //EXAMPLECONFIGSETTING = CONFIG.getOrDefault("telluria.example", 0);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}