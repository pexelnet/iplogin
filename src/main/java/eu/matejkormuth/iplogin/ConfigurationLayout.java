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

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import java.util.regex.Pattern;

public class ConfigurationLayout<T> {

    // Configuration.
    public static final ConfigurationLayout<Integer> KEY_LENGTH = new ConfigurationLayout<>("key.length");
    public static final CharArrayConf KEY_ALPHABET = new CharArrayConf("key.alphabet");
    public static final ConfigurationLayout<String> IP_FORMAT = new ConfigurationLayout<>("ip.format");
    public static final ConfigurationLayout<Integer> IP_PORT = new ConfigurationLayout<>("ip.port");

    // Translations.
    public static final TranslationConf MSG_ONLY_FOR_PLAYERS = new TranslationConf("messages.only_for_players");
    public static final TranslationConf MSG_ONLY_FOR_OPS = new TranslationConf("messages.only_for_ops");
    public static final TranslationConf MSG_MATCHES_NO_PLAYERS = new TranslationConf("messages.matches_no_players", 1);

    public static final TranslationConf MSG_LOGIN_SUCCESS = new TranslationConf("messages.login_success");
    public static final TranslationConf MSG_LOGIN_INVALID = new TranslationConf("messages.login_invalid");
    public static final TranslationConf MSG_LOGIN_STRIKES = new TranslationConf("messages.login_strikes");

    public static final TranslationConf MSG_KEY_GENERATED = new TranslationConf("messages.key_generated", 1);
    public static final TranslationConf MSG_KEY_GENERATED_IP = new TranslationConf("messages.key_generated_ip", 1);
    public static final TranslationConf MSG_KEY_GENERATED_WARN = new TranslationConf("messages.key_generated_warn");

    public static final TranslationConf MSG_KEY_NOT_GENERATED = new TranslationConf("messages.key_not_generated", 1);
    public static final TranslationConf MSG_KEY_OF = new TranslationConf("messages.key_of", 2);

    private final String key;

    private ConfigurationLayout(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public T get(Configuration configuration) {
        return cast(configuration.get(key));
    }

    @SuppressWarnings("unchecked")
    private static <T> T cast(Object o) {
        return (T) o;
    }

    public static class CharArrayConf extends ConfigurationLayout {

        private CharArrayConf(String key) {
            super(key);
        }

        @Override
        public char[] get(Configuration configuration) {
            return configuration.getString(getKey()).toCharArray();
        }
    }

    public static class TranslationConf extends ConfigurationLayout<String> {

        private static final String FIRST_REGEX = Pattern.quote("{}");
        private final int argumentCount;

        private TranslationConf(String key, int argc) {
            super(key);
            this.argumentCount = argc;
        }

        private TranslationConf(String key) {
            super(key);
            this.argumentCount = 0;
        }

        public String format(Configuration configuration, Object... objs) {
            // Currently only one argument.
            String msg = ChatColor.translateAlternateColorCodes('&', get(configuration));
            if (objs.length == argumentCount) {
                for (int i = 0; i < argumentCount; i++) {
                    msg = msg.replaceFirst(FIRST_REGEX, String.valueOf(objs[i]));
                }
                return msg;
            } else {
                throw new IllegalArgumentException("Invalid amount of 'objs' arguments! Expected " + argumentCount
                        + " but got " + objs.length);
            }
        }
    }
}
