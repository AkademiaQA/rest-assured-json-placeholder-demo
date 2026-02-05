package pl.akademiaqa.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config.properties"
})
public interface Configuration extends Config {

    @Key("base.uri")
    @DefaultValue("https://jsonplaceholder.typicode.com")
    String baseUri();

    @Key("base.path")
    @DefaultValue("/")
    String basePath();

    @Key("timeout")
    @DefaultValue("10000")
    int timeout();

    @Key("environment")
    @DefaultValue("test")
    String environment();
}
