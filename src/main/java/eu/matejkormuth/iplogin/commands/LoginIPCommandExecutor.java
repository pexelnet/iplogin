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
package eu.matejkormuth.iplogin.commands;

import eu.matejkormuth.iplogin.ConfigurationLayout;
import eu.matejkormuth.iplogin.IPLogin;
import eu.matejkormuth.iplogin.api.services.IPAddressProcessor;
import eu.matejkormuth.iplogin.api.services.KeyGenerator;
import eu.matejkormuth.iplogin.services.DefaultKeyVerifier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class LoginIPCommandExecutor implements CommandExecutor {

    /**
     * Default Minecraft server port.
     */
    public static final int DEFAULT_MINECRAFT_PORT = 25565;

    private final KeyGenerator keyGenerator;
    private final IPAddressProcessor ipProcessor;
    private final IPLogin ipLogin;
    private final Configuration conf;

    public LoginIPCommandExecutor(IPLogin ipLogin) {
        this.ipLogin = ipLogin;

        this.keyGenerator = Bukkit.getServicesManager().load(KeyGenerator.class);
        this.ipProcessor = Bukkit.getServicesManager().load(IPAddressProcessor.class);
        this.conf = ipLogin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigurationLayout.MSG_ONLY_FOR_PLAYERS.format(conf));
            return true;
        }

        // Generate new access key and then save it.
        String newAccessKey = keyGenerator.generate();
        String newIpAddress = ipProcessor.generateIp(newAccessKey);

        if (ConfigurationLayout.IP_PORT.get(conf) != DEFAULT_MINECRAFT_PORT) {
            newIpAddress += ":" + ConfigurationLayout.IP_PORT.get(conf);
        }

        ipLogin.getAccessKeys().set(DefaultKeyVerifier.KEY_CREATOR.apply((Player) sender), newAccessKey);
        ipLogin.getAccessKeys().save();

        // Send information.
        sender.sendMessage(ConfigurationLayout.MSG_KEY_GENERATED_WARN.format(conf));
        sender.sendMessage(ConfigurationLayout.MSG_KEY_GENERATED.format(conf, newAccessKey));
        sender.sendMessage(ConfigurationLayout.MSG_KEY_GENERATED_IP.format(conf, newIpAddress));
        return true;
    }
}
