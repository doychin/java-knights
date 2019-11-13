package java_knights.config.se;

import org.eclipse.microprofile.config.ConfigProvider;

public class Config {

    public static void main(String[] args) {
        String who = ConfigProvider.getConfig().getOptionalValue("who", String.class).orElse("world");

        System.out.println("Hello " + who);
    }
}
