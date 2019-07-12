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
import eu.matejkormuth.iplogin.services.DefaultKeyVerifier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.List;

public class ViewLoginIpCommandExecutor implements CommandExecutor {

    private final IPLogin ipLogin;
    private final Configuration conf;

    public ViewLoginIpCommandExecutor(IPLogin ipLogin) {
        this.ipLogin = ipLogin;
        this.conf = ipLogin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigurationLayout.MSG_ONLY_FOR_PLAYERS.format(conf));
            return true;
        }

        if (!sender.isOp()) {
            sender.sendMessage(ConfigurationLayout.MSG_ONLY_FOR_OPS.format(conf));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /vak <player>");
            return true;
        }

        String playerName = args[0];
        List<Player> players = Bukkit.matchPlayer(playerName);

        if (players.isEmpty()) {
            sender.sendMessage(ConfigurationLayout.MSG_MATCHES_NO_PLAYERS.format(conf, playerName));
            return true;
        }

        Player player = players.get(0);

        if (ipLogin.getAccessKeys().contains(DefaultKeyVerifier.KEY_CREATOR.apply(player))) {
            String hisAccessKey = ipLogin.getAccessKeys().getString(DefaultKeyVerifier.KEY_CREATOR.apply(player));
            String hisName = player.getName();
            sender.sendMessage(ConfigurationLayout.MSG_KEY_OF.format(conf, hisName, hisAccessKey));

        } else {
            String hisName = player.getName();
            sender.sendMessage(ConfigurationLayout.MSG_KEY_NOT_GENERATED.format(conf, hisName));
        }

        return true;
    }
}
