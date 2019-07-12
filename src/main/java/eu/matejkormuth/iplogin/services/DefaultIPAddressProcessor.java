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

import eu.matejkormuth.iplogin.api.services.IPAddressProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultIPAddressProcessor implements IPAddressProcessor {

    private static final Logger log = LoggerFactory.getLogger(DefaultIPAddressProcessor.class);

    private static final String CONFIGURATION_CHAR = "#";
    private static final String REGEX_CAPTURE = "([#]+)";

    private final Pattern format;
    private final String ipFormat;

    public DefaultIPAddressProcessor(char[] alphabet, String ipFormat) {
        String actualRegexCapture = REGEX_CAPTURE.replace("#", new String(alphabet));
        String actualRegex = ipFormat.replace(".", "\\.").replace(CONFIGURATION_CHAR, actualRegexCapture);

        this.format = Pattern.compile(actualRegex);
        this.ipFormat = ipFormat;

        log.info("Compiled ip address regex: " + this.format.toString());
    }

    @Override
    public boolean matches(String ipAddress) {
        return format.matcher(ipAddress).matches();
    }

    @Override
    public String getAccessKey(String ipAddress) {
        Matcher m = format.matcher(ipAddress);
        if (m.matches()) {
            return m.group(1);
        } else {
            throw new IllegalArgumentException("Specified IP address does not match regex!");
        }
    }

    @Override
    public String generateIp(String accessKey) {
        return ipFormat.replace(CONFIGURATION_CHAR, accessKey);
    }
}
