/**
 * iplogin - Login to your Minecraft server seamlessly
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.iplogin;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigurationFile implements Configuration {
    private YamlConfiguration configuration;
    private final File file;

    public ConfigurationFile(File file) {
        this.file = file;
        this.configuration = new YamlConfiguration();
        if (file.exists()) {
            this.load();
        } else {
            this.save();
        }
    }

    public String saveToString() {
        return configuration.saveToString();
    }

    public boolean isItemStack(String path) {
        return configuration.isItemStack(path);
    }

    public ConfigurationSection getDefaultSection() {
        return configuration.getDefaultSection();
    }

    public List<Boolean> getBooleanList(String path) {
        return configuration.getBooleanList(path);
    }

    public ItemStack getItemStack(String path) {
        return configuration.getItemStack(path);
    }

    public static YamlConfiguration loadConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public ConfigurationSection getParent() {
        return configuration.getParent();
    }

    public static YamlConfiguration loadConfiguration(Reader reader) {
        return YamlConfiguration.loadConfiguration(reader);
    }

    public String getName() {
        return configuration.getName();
    }

    public void loadFromString(String contents) throws InvalidConfigurationException {
        configuration.loadFromString(contents);
    }

    public boolean isString(String path) {
        return configuration.isString(path);
    }

    public String getString(String path, String def) {
        return configuration.getString(path, def);
    }

    @Deprecated
    public static YamlConfiguration loadConfiguration(InputStream stream) {
        return YamlConfiguration.loadConfiguration(stream);
    }

    public String getCurrentPath() {
        return configuration.getCurrentPath();
    }

    public long getLong(String path, long def) {
        return configuration.getLong(path, def);
    }

    public int getInt(String path) {
        return configuration.getInt(path);
    }

    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        configuration.load(file);
    }

    public Object get(String path, Object def) {
        return configuration.get(path, def);
    }

    public List<?> getList(String path) {
        return configuration.getList(path);
    }

    public Object get(String path) {
        return configuration.get(path);
    }

    public String getString(String path) {
        return configuration.getString(path);
    }

    public void addDefaults(Map<String, Object> defaults) {
        configuration.addDefaults(defaults);
    }

    public double getDouble(String path) {
        return configuration.getDouble(path);
    }

    public Configuration getRoot() {
        return configuration.getRoot();
    }

    public void save() {
        try {
            configuration.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isVector(String path) {
        return configuration.isVector(path);
    }

    public void load(Reader reader) throws IOException, InvalidConfigurationException {
        configuration.load(reader);
    }

    public boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }

    public YamlConfigurationOptions options() {
        return configuration.options();
    }

    public Configuration getDefaults() {
        return configuration.getDefaults();
    }

    public boolean isConfigurationSection(String path) {
        return configuration.isConfigurationSection(path);
    }

    public Color getColor(String path, Color def) {
        return configuration.getColor(path, def);
    }

    public boolean isOfflinePlayer(String path) {
        return configuration.isOfflinePlayer(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return configuration.getBoolean(path, def);
    }

    public ItemStack getItemStack(String path, ItemStack def) {
        return configuration.getItemStack(path, def);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return configuration.getConfigurationSection(path);
    }

    public List<Long> getLongList(String path) {
        return configuration.getLongList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return configuration.getIntegerList(path);
    }

    public List<Double> getDoubleList(String path) {
        return configuration.getDoubleList(path);
    }

    public Vector getVector(String path) {
        return configuration.getVector(path);
    }

    public List<Short> getShortList(String path) {
        return configuration.getShortList(path);
    }

    public double getDouble(String path, double def) {
        return configuration.getDouble(path, def);
    }

    public boolean isDouble(String path) {
        return configuration.isDouble(path);
    }

    public Vector getVector(String path, Vector def) {
        return configuration.getVector(path, def);
    }

    public boolean contains(String path) {
        return configuration.contains(path);
    }

    public List<?> getList(String path, List<?> def) {
        return configuration.getList(path, def);
    }

    public Color getColor(String path) {
        return configuration.getColor(path);
    }

    public Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }

    public static String createPath(ConfigurationSection section, String key, ConfigurationSection relativeTo) {
        return MemorySection.createPath(section, key, relativeTo);
    }

    public List<Byte> getByteList(String path) {
        return configuration.getByteList(path);
    }

    public boolean isInt(String path) {
        return configuration.isInt(path);
    }

    public boolean isColor(String path) {
        return configuration.isColor(path);
    }

    public void addDefault(String path, Object value) {
        configuration.addDefault(path, value);
    }

    public int getInt(String path, int def) {
        return configuration.getInt(path, def);
    }

    public boolean isList(String path) {
        return configuration.isList(path);
    }

    public boolean isBoolean(String path) {
        return configuration.isBoolean(path);
    }

    public Map<String, Object> getValues(boolean deep) {
        return configuration.getValues(deep);
    }

    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        return configuration.getOfflinePlayer(path, def);
    }

    @Deprecated
    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        configuration.load(stream);
    }

    public void set(String path, Object value) {
        configuration.set(path, value);
    }

    public static String createPath(ConfigurationSection section, String key) {
        return MemorySection.createPath(section, key);
    }

    public List<Float> getFloatList(String path) {
        return configuration.getFloatList(path);
    }

    public OfflinePlayer getOfflinePlayer(String path) {
        return configuration.getOfflinePlayer(path);
    }

    public boolean isSet(String path) {
        return configuration.isSet(path);
    }

    public List<Character> getCharacterList(String path) {
        return configuration.getCharacterList(path);
    }

    public long getLong(String path) {
        return configuration.getLong(path);
    }

    public void load() {
        try {
            configuration.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaults(Configuration defaults) {
        configuration.setDefaults(defaults);
    }

    public ConfigurationSection createSection(String path) {
        return configuration.createSection(path);
    }

    public boolean isLong(String path) {
        return configuration.isLong(path);
    }

    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        return configuration.createSection(path, map);
    }

    public List<String> getStringList(String path) {
        return configuration.getStringList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return configuration.getMapList(path);
    }

    public void save(String file) throws IOException {
        configuration.save(file);
    }

    public void addDefaults(Configuration defaults) {
        configuration.addDefaults(defaults);
    }
}
