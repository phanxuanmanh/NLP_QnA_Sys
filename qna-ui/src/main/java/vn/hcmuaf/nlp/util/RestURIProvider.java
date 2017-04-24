/*
 * RestURIProvider.java
 * 
 * Created on Mar 21, 2016
 * 
 * Copyright (C) 2016 Toennies, All rights reserved.
 */

package vn.hcmuaf.nlp.util;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

/**
 * The Class RestURIProvider.
 */
public class RestURIProvider {

    /** The Constant HTTP. */
    public static final String HTTP = "http";

    /** The Constant HTTPS. */
    public static final String HTTPS = "https";

    /** The Constant SLASH. */
    public static final String SLASH = "/";

    /** The base uri. */
    private URI baseURI;

    /**
     * Instantiates a new abstract rest client.
     *
     * @param scheme the scheme
     * @param host the host
     * @param port the port
     * @param basePath the base path
     * @throws URISyntaxException the URI syntax exception
     */
    public RestURIProvider(String scheme, String host, Integer port, String basePath) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(host);
        if (port != null) {
            uriBuilder.setPort(port);
        }
        uriBuilder.setPath(basePath);
        // baseURI is like http://<host>:<port>/basePath
        baseURI = uriBuilder.build();
    }

    /**
     * Instantiates a new abstract rest client.
     *
     * @param host the host
     * @param port the port
     * @param basePath the base path
     * @throws URISyntaxException the URI syntax exception
     */
    public RestURIProvider(String host, Integer port, String basePath) throws URISyntaxException {
        this(HTTPS, host, port, basePath);
    }

    /**
     * Instantiates a new rest uri provider.
     *
     * @param baseURI the base uri
     * @throws URISyntaxException the URI syntax exception
     */
    public RestURIProvider(URI baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Gets the uri.
     *
     * @param parameters the parameters
     * @param paths the paths
     * @return the uri
     * @throws URISyntaxException the URI syntax exception
     */
    public final URI getURI(List<NameValuePair> parameters, String... paths) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(baseURI);
        // expand path
        String currentPath = uriBuilder.getPath();
        for (String path : paths) {
            if (path != null) {
                if (!path.startsWith(SLASH)) {
                    // avoid missing a slash before path
                    currentPath = currentPath.concat(SLASH);
                }
                currentPath = currentPath.concat(path);
            }
        }
        uriBuilder.setPath(currentPath);
        // set parameters
        if (parameters != null) {
            uriBuilder.setParameters(parameters);
        }
        return uriBuilder.build();
    }

    /**
     * Gets the uri.
     *
     * @param paths the paths
     * @return the uri
     * @throws URISyntaxException the URI syntax exception
     */
    public final URI getURI(String... paths) throws URISyntaxException {
        // expand path with no parameters
        return getURI(null, paths);
    }

}
