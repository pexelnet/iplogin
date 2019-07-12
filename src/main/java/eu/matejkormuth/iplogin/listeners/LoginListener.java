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
package eu.matejkormuth.iplogin.listeners;

import eu.matejkormuth.iplogin.ConfigurationLayout;
import eu.matejkormuth.iplogin.api.services.AuthenticationPlugin;
import eu.matejkormuth.iplogin.api.services.IPAddressProcessor;
import eu.matejkormuth.iplogin.api.services.KeyVerifier;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class LoginListener implements Listener {

    private static final Logger log = LoggerFactory.getLogger(LoginListener.class);

    /**
     * Returns only IP address from socket address.
     */
    private static Function<String, String> HOSTNAME_FIX = s -> s.contains(":") ? s.split(":")[0] : s;

    private final IPAddressProcessor ipProcessor;
    private final KeyVerifier keyVerifier;
    private final AuthenticationPlugin authPluginHook;
    private final Plugin plugin;

    private final Configuration config;

    public LoginListener(Configuration config) {
        this.config = config;

        ipProcessor = Bukkit.getServicesManager().load(IPAddressProcessor.class);
        keyVerifier = Bukkit.getServicesManager().load(KeyVerifier.class);
        authPluginHook = Bukkit.getServicesManager().load(AuthenticationPlugin.class);
        plugin = Bukkit.getPluginManager().getPlugin("iplogin");
    }

    @EventHandler
    private void onLogin(final PlayerLoginEvent event) {
        // Remove port if needed.
        String hostnameFixed = HOSTNAME_FIX.apply(event.getHostname());

        if (ipProcessor.matches(hostnameFixed)) {
            String accessKey = ipProcessor.getAccessKey(hostnameFixed);

            log.info(event.getPlayer() + " tried to log in with key: " + accessKey);

            KeyVerifier.Result result = keyVerifier.authenticate(event.getPlayer(), accessKey);

            if (result == KeyVerifier.Result.LOGIN_SUCESSFULL) {
                log.info(event.getPlayer() + " has logged in using ip login!");
                authPluginHook.forceLogin(event.getPlayer());

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
                        () -> event.getPlayer().sendMessage(ConfigurationLayout.MSG_LOGIN_SUCCESS.format(config)));
            } else {

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
                        () -> event.getPlayer().sendMessage(ConfigurationLayout.MSG_LOGIN_INVALID.format(config)));
            }
        }
    }
}
