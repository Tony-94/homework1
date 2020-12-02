package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        // TODO: NotImplemented
        List<Class<? extends PluginInterface>> plugins = new ArrayList<>();

        File pluginDirectory = new File(System.getProperty("user.dir") + "/" + pluginDirName);
        File[] jarFiles = pluginDirectory.listFiles((dir, name) -> name.endsWith("." + PLUGIN_EXT));

        if (jarFiles != null && jarFiles.length > 0) {
            ArrayList<String> classes = new ArrayList<>();
            ArrayList<URL> urls = new ArrayList<>(jarFiles.length);

            for (File file : jarFiles) {
                try {
                    JarFile jar = new JarFile(file);
                    jar.stream().forEach(jarEntry -> {
                        if (jarEntry.getName().endsWith(".class")) {
                            classes.add(jarEntry.getName());
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    URL url = file.toURI().toURL();
                    urls.add(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
            classes.forEach(className -> {
                try {
                    Class clazz = urlClassLoader.loadClass(className.replaceAll("/", ".").replace(".class", ""));
                    Class[] interfaces = clazz.getInterfaces();
                    for (Class i : interfaces) {
                        if (i.equals(PluginInterface.class)) {
                            plugins.add(clazz);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        return plugins;
    }
}
