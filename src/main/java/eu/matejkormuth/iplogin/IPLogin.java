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

import eu.matejkormuth.iplogin.api.services.AuthenticationPlugin;
import eu.matejkormuth.iplogin.api.services.IPAddressProcessor;
import eu.matejkormuth.iplogin.api.services.KeyGenerator;
import eu.matejkormuth.iplogin.api.services.KeyVerifier;
import eu.matejkormuth.iplogin.commands.LoginIPCommandExecutor;
import eu.matejkormuth.iplogin.commands.ViewLoginIpCommandExecutor;
import eu.matejkormuth.iplogin.listeners.LoginListener;
import eu.matejkormuth.iplogin.services.DefaultIPAddressProcessor;
import eu.matejkormuth.iplogin.services.DefaultKeyGenerator;
import eu.matejkormuth.iplogin.services.DefaultKeyVerifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class IPLogin extends JavaPlugin {

    private static final Logger log = LoggerFactory.getLogger(IPLogin.class);

    /**
     * Access keys yaml configuration file.
     */
    private ConfigurationFile accessKeys = new ConfigurationFile(
            new File(this.getDataFolder().getAbsolutePath() + "/accesskeys.yml"));

    @Override
    public void onEnable() {
        log.info("Loading " + getDescription().getFullName() + " v" + getDescription().getVersion() +
                " by " + getDescription().getAuthors().get(0));
        log.info("Check my other plugins: https://www.spigotmc.org/resources/authors/dobrakmato.16389/");

        // Get and/or create configuration file.
        saveDefaultConfig();

        log.info("Registering all services...");
        registerServices();

        // Register command executors.
        log.info("Registering commands.");
        getCommand("loginip").setExecutor(new LoginIPCommandExecutor(this));
        getCommand("viewloginip").setExecutor(new ViewLoginIpCommandExecutor(this));

        // Register listeners.
        getServer().getPluginManager().registerEvents(new LoginListener(getConfig()), this);
    }

    private void registerServices() {
        FileConfiguration config = getConfig();

        // Register access key provider.
        KeyGenerator keyGenerator = new DefaultKeyGenerator(
                ConfigurationLayout.KEY_LENGTH.get(config),
                ConfigurationLayout.KEY_ALPHABET.get(config));
        getServer().getServicesManager().register(KeyGenerator.class, keyGenerator, this, ServicePriority.Normal);

        // Register ip parser provider.
        IPAddressProcessor ipProcessor = new DefaultIPAddressProcessor(
                ConfigurationLayout.KEY_ALPHABET.get(config),
                ConfigurationLayout.IP_FORMAT.get(config));
        getServer().getServicesManager().register(IPAddressProcessor.class, ipProcessor, this, ServicePriority.Normal);

        // Register key authenticator.
        KeyVerifier ipParser = new DefaultKeyVerifier(accessKeys);
        getServer().getServicesManager().register(KeyVerifier.class, ipParser, this, ServicePriority.Normal);

        // Register auth plugin hooks.
        AuthenticationPlugin hook = AuthenticatorFactory.create();
        log.info("Using " + hook.getClass().getName() + " as AuthPluginHook...");
        getServer().getServicesManager().register(AuthenticationPlugin.class, hook, this, ServicePriority.Normal);
    }

    public ConfigurationFile getAccessKeys() {
        return accessKeys;
    }

    @Override
    public void onDisable() {
        // Unregister services.
        getServer().getServicesManager().unregisterAll(this);
    }
}
