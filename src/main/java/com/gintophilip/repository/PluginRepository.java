package com.gintophilip.repository;

import com.gintophilip.core.greeting.contract.GreetingPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginRepository {

    private static final Map<String, GreetingPlugin> greetingPluginsMap = new HashMap<>();
    private static final String DEFAULT_LANGUAGE = "English";
    private static final String PLUGIN_DIRECTORY = "/home/bytealizer/Documents/Projects/PluginPlatform/plugins"; // <-- Set a real path here

    public static GreetingPlugin get(String language) {
        return greetingPluginsMap.getOrDefault(language, greetingPluginsMap.get(DEFAULT_LANGUAGE));
    }

    public void addPlugin(GreetingPlugin plugin) {
        greetingPluginsMap.put(plugin.getLanguage(), plugin);
    }

    public void loadPlugins() {
        loadDefaultPlugin();
        File pluginDir = new File(PLUGIN_DIRECTORY);

        if (!pluginDir.exists() || !pluginDir.isDirectory()) {
            System.out.println("[PluginRepository] Plugin directory not found: " + pluginDir.getAbsolutePath());
            return;
        }

        File[] jarFiles = pluginDir.listFiles(new JarFileFilter());
        if (jarFiles == null || jarFiles.length == 0) {
            System.out.println("[PluginRepository] No plugin JARs found in: " + pluginDir.getAbsolutePath());
            return;
        }

        for (File jarFile : jarFiles) {
            loadPluginFromJar(jarFile);
        }

        if (greetingPluginsMap.isEmpty()) {
            System.out.println("[PluginRepository] No valid GreetingPlugins loaded.");
        }
    }

    private void loadDefaultPlugin() {
        String className = "com.gintophilip.defaultgreeting.EnglishGreeting";
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!GreetingPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(className + " does not implement GreetingPlugin");
        }

        GreetingPlugin plugin = null;
        try {
            plugin = (GreetingPlugin) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        addPlugin(plugin);

    }

    private void loadPluginFromJar(File jarFile) {
        try {
            URL jarUrl = jarFile.toURI().toURL();
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl}, this.getClass().getClassLoader())) {
                ServiceLoader<GreetingPlugin> serviceLoader =
                        ServiceLoader.load(GreetingPlugin.class, classLoader);

                for (GreetingPlugin plugin : serviceLoader) {
                    addPlugin(plugin);
                    System.out.println("[PluginRepository] Loaded plugin: " + plugin.getClass().getName());
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("[PluginRepository] Invalid plugin URL: " + jarFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("[PluginRepository] Failed to load plugin from: " + jarFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private static class JarFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name != null && name.toLowerCase().endsWith(".jar");
        }
    }
}
