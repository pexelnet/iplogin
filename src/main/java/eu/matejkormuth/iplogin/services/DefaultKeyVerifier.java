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
package eu.matejkormuth.iplogin.services;

import eu.matejkormuth.iplogin.api.services.KeyVerifier;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class DefaultKeyVerifier implements KeyVerifier {

    public static final Function<Player, String> KEY_CREATOR = player -> player.getUniqueId().toString();

    private final Configuration accessKeys;

    public DefaultKeyVerifier(Configuration accessKeys) {
        this.accessKeys = accessKeys;
    }

    @Override
    public Result authenticate(Player player, String accessKey) {
        String configurationKey = KEY_CREATOR.apply(player);

        if (accessKeys.contains(configurationKey)) {
            if (accessKey.equals(accessKeys.getString(configurationKey))) {
                return Result.LOGIN_SUCESSFULL;
            } else {
                return Result.INFORMATION_INVALID;
            }
        } else {
            return Result.KEY_DOES_NOT_EXISTS;
        }
    }
}
