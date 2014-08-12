package com.eits.freemr.configuration.upcasting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockMavenProtocolFactory implements URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("mvn")) {
            return new MvnURLStreamHandler();
        }
        return null;
    }

    public class MvnURLStreamHandler extends URLStreamHandler {

        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            return new FeaturesURLConnection(url);
        }
    }

    public class FeaturesURLConnection extends URLConnection {

        protected FeaturesURLConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {

        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (this.url.toString().contains("config")) {
                return new FileInputStream(new File("src/test/resources/freemr-ebms-inbound-gateway-config-3.0.1-SNAPSHOT.jar"));
            } else {
                return new FileInputStream(new File("src/test/resources/test-features.xml"));
            }
        }
    }

}