package pl.akademiaqa.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigurationManager {
    
    private static final Configuration CONFIG = ConfigFactory.create(Configuration.class);
    
    private ConfigurationManager() {
    }
    
    public static Configuration getConfiguration() {
        return CONFIG;
    }
}
