package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        // TODO: NotImplemented
        String result = "";
        try {
            PluginInterface plugin = cls.getDeclaredConstructor().newInstance();
            result = plugin.apply(text);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }
}
