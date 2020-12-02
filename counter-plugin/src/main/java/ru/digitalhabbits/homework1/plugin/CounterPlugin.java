package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
        int lines = (int) text.lines().count();

        int words = 0;
        Pattern wordPattern = Pattern.compile("(\\b[a-zA-Z][a-zA-Z.0-9-]*\\b)");
        Matcher wordMatcher = wordPattern.matcher(text);
        while (wordMatcher.find()) {
            words++;
        }

        int letters = text.length();

        return new StringBuilder()
                .append(lines)
                .append(";")
                .append(words)
                .append(";")
                .append(letters).toString();
    }
}
