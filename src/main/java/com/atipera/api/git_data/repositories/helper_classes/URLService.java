package com.atipera.api.git_data.repositories.helper_classes;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class URLService {

    public static URL createURL(String path) {
        try {
            return new URL(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readURL(URL url, Charset encoding){
        try (InputStream in = url.openStream()) {
            return IOUtils.toString(in, encoding);
        } catch (Exception e) {}
        return null;
    }
}
