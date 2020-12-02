package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
        Map<String, Integer> dictionary = new TreeMap<>();
        Pattern wordPattern = Pattern.compile("(\\b[a-zA-Z][a-zA-Z.0-9-]*\\b)");
        Matcher wordMatcher = wordPattern.matcher(text.toLowerCase());
        String tempWord;
        while (wordMatcher.find()) {
            tempWord = wordMatcher.group();
            if (!dictionary.containsKey(tempWord)) {
                dictionary.put(tempWord, 1);
            } else {
                dictionary.put(tempWord, dictionary.get(tempWord) + 1);
            }
        }
        return dictionary.keySet().stream().map(key -> key + " " + dictionary.get(key)).collect(Collectors.joining("\n"));
    }
}
