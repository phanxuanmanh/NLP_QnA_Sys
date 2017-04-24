package vn.hcmuaf.nlp.util;

/*
 * RestTemplateUtil.java
 * 
 * Created on Jan 14, 2016
 * 
 * Copyright (C) 2016 Toennies, All rights reserved.
 */



import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * The Class RestTemplateUtil.
 */
public final class RestTemplateUtil {

    /** The Constant SORT. */
    private static final String SORT = "\\{&sort\\}";


    /** The rest template for get. */
    private static RestTemplate halRestTemplateForGet;

    /** The rest template for post. */
    private static RestTemplate halRestTemplateForPost;

    /** The raw rest template. */
    private static RestTemplate rawRestTemplate;

    /**
     * Instantiates a new rest template util.
     */
    private RestTemplateUtil() {

    }

    /**
     * Gets the rest template for post.
     *
     * @return the rest template for post
     */
    public static final synchronized RestTemplate getRestTemplateForPost() {
        if (halRestTemplateForPost == null) {
            halRestTemplateForPost = new RestTemplate();
            ObjectMapper mapper = getObjectMapperWithHalModule();
            // Converter for mapping object to json
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setObjectMapper(mapper);
            List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
            converters.add(converter);
            // Converter for mapping json to object
            MappingJackson2HttpMessageConverter halConverter =
                    new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
            halConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON, MediaType.APPLICATION_JSON));
            halConverter.setObjectMapper(mapper);
            converters.add(halConverter);

            halRestTemplateForPost.setMessageConverters(converters);
        }
        return halRestTemplateForPost;
    }

    /**
     * Gets the rest template for get.
     *
     * @return the rest template for get
     */
    public static final synchronized RestTemplate getRestTemplateForGet() {
        if (halRestTemplateForGet == null) {
            // use only one converter for hal
            ObjectMapper mapper = getObjectMapperWithHalModule();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
            converter.setObjectMapper(mapper);
            List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
            converters.add(converter);
            halRestTemplateForGet = new RestTemplate(converters);
        }
        return halRestTemplateForGet;
    }

    /**
     * Gets the object mapper with hal module.
     *
     * @return the object mapper with hal module
     */
    public static final ObjectMapper getObjectMapperWithHalModule() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    /**
     * Raw rest template.
     *
     * @return the rest template
     */
    public static final RestTemplate rawRestTemplate() {
        if (rawRestTemplate == null) {
            rawRestTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            List<HttpMessageConverter<?>> messageConverters = rawRestTemplate.getMessageConverters();
            for (HttpMessageConverter<?> con : messageConverters) {
                if (con instanceof MappingJackson2HttpMessageConverter) {
                    ((MappingJackson2HttpMessageConverter) con).setObjectMapper(mapper);
                }
            }
        }
        return rawRestTemplate;
    }

    /**
     * Gets the all pages data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param type the type
     * @return the all pages data
     * @throws RestClientException the rest client exception
     */
    public static final <T> List<T> getAllPagesData(URI uri, Class<T> type) throws RestClientException {
        return getAllPagesData(uri, HttpMethod.GET, null, type);
    }

    /**
     * Post for all pages data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param payload the payload
     * @param type the type
     * @return the list
     * @throws RestClientException the rest client exception
     */
    public static final <T> List<T> postForAllPagesData(URI uri, Object payload, Class<T> type) throws RestClientException {
        return getAllPagesData(uri, HttpMethod.POST, payload, type);
    }

    /**
     * Gets the all pages data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param method the method
     * @param payload the payload
     * @param type the type
     * @return the all pages data
     * @throws RestClientException the rest client exception
     */
    public static final <T> List<T> getAllPagesData(URI uri, HttpMethod method, Object payload, Class<T> type)
            throws RestClientException {
        try {
            List<T> allData = new ArrayList<T>();
            // Get first page
            PagedResources<T> page = getData(uri, method, payload, type);
            if (page != null) {
                allData.addAll(page.getContent());
                // get all remaining pages
                Link nextLink = page.getNextLink();
                while (nextLink != null) {
                    String href = nextLink.getHref();
                    href = href.replaceAll(SORT, "");
                    // It must return data since we use it's link to get data
                    page = getData(href, method, payload, type);
                    if (page == null) {
                        break;
                    }
                    allData.addAll(page.getContent());
                    nextLink = page.getNextLink();
                }
            }
            return allData;
        } catch (RestClientException e) {
            throw e;
        }
    }

    /**
     * Gets the data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param method the method
     * @param payload the payload
     * @param type the type
     * @return the data
     * @throws RestClientException the rest client exception
     */
    private static final <T> PagedResources<T> getData(URI uri, HttpMethod method, Object payload, Class<T> type) throws RestClientException {
        switch (method) {
            case POST:
                return postData(uri, payload, type);
            default:
                return getData(uri, type);
        }
    }

    /**
     * Gets the data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param method the method
     * @param payload the payload
     * @param type the type
     * @return the data
     * @throws RestClientException the rest client exception
     */
    private static final <T> PagedResources<T> getData(String uri, HttpMethod method, Object payload, Class<T> type) throws RestClientException {
        switch (method) {
            case POST:
                return postData(uri, payload, type);
            default:
                return getData(uri, type);
        }
    }

    /**
     * Gets the data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param type the type
     * @return the data
     * @throws RestClientException the rest client exception
     */
    public static final <T> PagedResources<T> getData(URI uri, Class<T> type) throws RestClientException {
        RestTemplate restTemplate = getRestTemplateForGet();
        try {
            ResponseEntity<PagedResources<T>> responseEntities =
                    restTemplate.exchange(uri, HttpMethod.GET, null,
                            new MyParameterizedTypeReference<PagedResources<T>>(type) {
                            });
            return responseEntities.getBody();
        } catch (RestClientException e) {
            throw e;
        }
    }

    /**
     * Gets the data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param type the type
     * @return the data
     * @throws RestClientException the rest client exception
     */
    private static final <T> PagedResources<T> getData(String uri, Class<T> type) throws RestClientException {
        RestTemplate restTemplate = getRestTemplateForGet();
        try {
            ResponseEntity<PagedResources<T>> responseEntities =
                    restTemplate.exchange(uri, HttpMethod.GET, null,
                            new MyParameterizedTypeReference<PagedResources<T>>(type) {
                            });
            return responseEntities.getBody();
        } catch (Exception e) {
           throw e;
        }
    }

    /**
     * Post data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param payload the payload
     * @param type the type
     * @return the paged resources
     * @throws RestClientException the rest client exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static final <T> PagedResources<T> postData(String uri, Object payload, Class<T> type)
            throws RestClientException {
        RestTemplate restTemplate = getRestTemplateForPost();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity(payload, headers);
        ResponseEntity<PagedResources<T>> responseEntities =
                restTemplate.exchange(uri, HttpMethod.POST, entity,
                        new MyParameterizedTypeReference<PagedResources<T>>(type) {
                        });
        return responseEntities.getBody();
    }

    /**
     * Post data.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param payload the payload
     * @param type the type
     * @return the paged resources
     * @throws RestClientException the rest client exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final <T> PagedResources<T> postData(URI uri, Object payload, Class<T> type)
            throws RestClientException {
        RestTemplate restTemplate = getRestTemplateForPost();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity(payload, headers);
        ResponseEntity<PagedResources<T>> responseEntities =
                restTemplate.exchange(uri, HttpMethod.POST, entity,
                        new MyParameterizedTypeReference<PagedResources<T>>(type) {
                        });
        return responseEntities.getBody();
    }

    /**
     * Gets the for object.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param type the type
     * @return the for object
     * @throws RestClientException the rest client exception
     */
    public static final <T> T getForObject(URI uri, Class<T> type) throws RestClientException {
        try {
            RestTemplate restTemplate = getRestTemplateForGet();
            return restTemplate.getForObject(uri, type);
        } catch (RestClientException ex) {
            throw ex;
        }
    }

    /**
     * Gets the for object with raw restemplate.
     *
     * @param <T> the generic type
     * @param uri the uri
     * @param type the type
     * @return the for object with raw restemplate
     * @throws RestClientException the rest client exception
     */
    public static final <T> T getForObjectWithRawRestemplate(URI uri, Class<T> type) throws RestClientException {
        try {
            RestTemplate restTemplate = rawRestTemplate();
            return restTemplate.getForObject(uri, type);
        } catch (RestClientException ex) {
            throw ex;
        }
    }

    /**
     * Post for location.
     *
     * @param uri the uri
     * @param request the request
     * @return the uri
     * @throws RestClientException the rest client exception
     */
    public static final URI postForLocation(URI uri, Object request) throws RestClientException {
        RestTemplate restTemplate = getRestTemplateForPost();
        return restTemplate.postForLocation(uri, request);
    }

    /**
     * Delete entity.
     *
     * @param uri the uri of entity to delete
     * @throws RestClientException the rest client exception
     */
    public static final void delete(URI uri) throws RestClientException {
        RestTemplate restTemplate = getRestTemplateForGet();
        restTemplate.delete(uri);
    }
}
