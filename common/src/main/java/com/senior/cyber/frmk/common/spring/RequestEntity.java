package com.senior.cyber.frmk.common.spring;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

public class RequestEntity<T> extends HttpEntity<T> implements Serializable {

    @Nullable
    private final HttpMethod method;

    @Nullable
    private final URI url;

    @Nullable
    private final Type type;

    public RequestEntity(HttpMethod method, URI url) {
        this(null, null, method, url, null);
    }

    public RequestEntity(@Nullable T body, HttpMethod method, URI url) {
        this(body, null, method, url, null);
    }

    public RequestEntity(@Nullable T body, HttpMethod method, URI url, Type type) {
        this(body, null, method, url, type);
    }

    public RequestEntity(MultiValueMap<String, String> headers, HttpMethod method, URI url) {
        this(null, headers, method, url, null);
    }

    public RequestEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers,
                         @Nullable HttpMethod method, URI url) {

        this(body, headers, method, url, null);
    }

    public RequestEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers,
                         @Nullable HttpMethod method, @Nullable URI url, @Nullable Type type) {

        super(body, headers);
        this.method = method;
        this.url = url;
        this.type = type;
    }

    @Nullable
    public HttpMethod getMethod() {
        return this.method;
    }

    public URI getUrl() {
        if (this.url == null) {
            throw new UnsupportedOperationException(
                    "The RequestEntity was created with a URI template and variables, " +
                            "and there is not enough information on how to correctly expand and " +
                            "encode the URI template. This will be done by the RestTemplate instead " +
                            "with help from the UriTemplateHandler it is configured with.");
        }
        return this.url;
    }

    @Nullable
    public Type getType() {
        if (this.type == null) {
            T body = getBody();
            if (body != null) {
                return body.getClass();
            }
        }
        return this.type;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other)) {
            return false;
        }
        RequestEntity<?> otherEntity = (RequestEntity<?>) other;
        return (ObjectUtils.nullSafeEquals(this.method, otherEntity.method) &&
                ObjectUtils.nullSafeEquals(this.url, otherEntity.url));
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.method);
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.url);
        return hashCode;
    }

    @Override
    public String toString() {
        return format(getMethod(), getUrl().toString(), getBody(), getHeaders());
    }

    static <T> String format(@Nullable HttpMethod httpMethod, String url, @Nullable T body, HttpHeaders headers) {
        StringBuilder builder = new StringBuilder("<");
        builder.append(httpMethod);
        builder.append(' ');
        builder.append(url);
        builder.append(',');
        if (body != null) {
            builder.append(body);
            builder.append(',');
        }
        builder.append(headers);
        builder.append('>');
        return builder.toString();
    }

    public static BodyBuilder method(HttpMethod method, URI url) {
        return new DefaultBodyBuilder(method, url);
    }

    public static BodyBuilder method(HttpMethod method, String uriTemplate, Object... uriVariables) {
        return new DefaultBodyBuilder(method, uriTemplate, uriVariables);
    }

    public static BodyBuilder method(HttpMethod method, String uriTemplate, Map<String, ?> uriVariables) {
        return new DefaultBodyBuilder(method, uriTemplate, uriVariables);
    }

    public static HeadersBuilder<?> get(URI url) {
        return method(HttpMethod.GET, url);
    }

    public static HeadersBuilder<?> get(String uriTemplate, Object... uriVariables) {
        return method(HttpMethod.GET, uriTemplate, uriVariables);
    }

    public static HeadersBuilder<?> head(URI url) {
        return method(HttpMethod.HEAD, url);
    }

    public static HeadersBuilder<?> head(String uriTemplate, Object... uriVariables) {
        return method(HttpMethod.HEAD, uriTemplate, uriVariables);
    }

    public static BodyBuilder post(URI url) {
        return method(HttpMethod.POST, url);
    }

    public static BodyBuilder post(String uriTemplate, Object... uriVariables) {
        return method(HttpMethod.POST, uriTemplate, uriVariables);
    }

    public static BodyBuilder put(URI url) {
        return method(HttpMethod.PUT, url);
    }

    public static BodyBuilder put(String uriTemplate, Object... uriVariables) {
        return method(HttpMethod.PUT, uriTemplate, uriVariables);
    }

    public static BodyBuilder patch(URI url) {
        return method(HttpMethod.PATCH, url);
    }

    public static BodyBuilder patch(String uriTemplate, Object... uriVariables) {
        return method(HttpMethod.PATCH, uriTemplate, uriVariables);
    }

    public static HeadersBuilder<?> delete(URI url) {
        return method(HttpMethod.DELETE, url);
    }

    public static HeadersBuilder<?> delete(String uriTemplate, Object... uriVariables) {
        return method(HttpMethod.DELETE, uriTemplate, uriVariables);
    }

    public static HeadersBuilder<?> options(URI url) {
        return method(HttpMethod.OPTIONS, url);
    }

    public static HeadersBuilder<?> options(String uriTemplate, Object... uriVariables) {
        return method(HttpMethod.OPTIONS, uriTemplate, uriVariables);
    }

    public interface HeadersBuilder<B extends HeadersBuilder<B>> {

        B header(String headerName, String... headerValues);

        B headers(@Nullable HttpHeaders headers);

        B headers(Consumer<HttpHeaders> headersConsumer);

        B accept(MediaType... acceptableMediaTypes);

        B acceptCharset(Charset... acceptableCharsets);

        B ifModifiedSince(ZonedDateTime ifModifiedSince);

        B ifModifiedSince(Instant ifModifiedSince);

        B ifModifiedSince(long ifModifiedSince);

        B ifNoneMatch(String... ifNoneMatches);

        RequestEntity<Void> build();
    }

    public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

        BodyBuilder contentLength(long contentLength);

        BodyBuilder contentType(MediaType contentType);

        <T> RequestEntity<T> body(T body);

        <T> RequestEntity<T> body(T body, Type type);
    }

    private static class DefaultBodyBuilder implements BodyBuilder {

        private final HttpMethod method;

        private final HttpHeaders headers = new HttpHeaders();

        @Nullable
        private final URI uri;

        @Nullable
        private final String uriTemplate;

        @Nullable
        private final Object[] uriVarsArray;

        @Nullable
        private final Map<String, ?> uriVarsMap;

        DefaultBodyBuilder(HttpMethod method, URI url) {
            this.method = method;
            this.uri = url;
            this.uriTemplate = null;
            this.uriVarsArray = null;
            this.uriVarsMap = null;
        }

        DefaultBodyBuilder(HttpMethod method, String uriTemplate, Object... uriVars) {
            this.method = method;
            this.uri = null;
            this.uriTemplate = uriTemplate;
            this.uriVarsArray = uriVars;
            this.uriVarsMap = null;
        }

        DefaultBodyBuilder(HttpMethod method, String uriTemplate, Map<String, ?> uriVars) {
            this.method = method;
            this.uri = null;
            this.uriTemplate = uriTemplate;
            this.uriVarsArray = null;
            this.uriVarsMap = uriVars;
        }

        @Override
        public BodyBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(@Nullable HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(Consumer<HttpHeaders> headersConsumer) {
            headersConsumer.accept(this.headers);
            return this;
        }

        @Override
        public BodyBuilder accept(MediaType... acceptableMediaTypes) {
            this.headers.setAccept(Arrays.asList(acceptableMediaTypes));
            return this;
        }

        @Override
        public BodyBuilder acceptCharset(Charset... acceptableCharsets) {
            this.headers.setAcceptCharset(Arrays.asList(acceptableCharsets));
            return this;
        }

        @Override
        public BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public BodyBuilder ifModifiedSince(ZonedDateTime ifModifiedSince) {
            this.headers.setIfModifiedSince(ifModifiedSince);
            return this;
        }

        @Override
        public BodyBuilder ifModifiedSince(Instant ifModifiedSince) {
            this.headers.setIfModifiedSince(ifModifiedSince);
            return this;
        }

        @Override
        public BodyBuilder ifModifiedSince(long ifModifiedSince) {
            this.headers.setIfModifiedSince(ifModifiedSince);
            return this;
        }

        @Override
        public BodyBuilder ifNoneMatch(String... ifNoneMatches) {
            this.headers.setIfNoneMatch(Arrays.asList(ifNoneMatches));
            return this;
        }

        @Override
        public RequestEntity<Void> build() {
            return buildInternal(null, null);
        }

        @Override
        public <T> RequestEntity<T> body(T body) {
            return buildInternal(body, null);
        }

        @Override
        public <T> RequestEntity<T> body(T body, Type type) {
            return buildInternal(body, type);
        }

        private <T> RequestEntity<T> buildInternal(@Nullable T body, @Nullable Type type) {
            if (this.uri != null) {
                return new RequestEntity<>(body, this.headers, this.method, this.uri, type);
            } else if (this.uriTemplate != null) {
                return new UriTemplateRequestEntity<>(body, this.headers, this.method, type,
                        this.uriTemplate, this.uriVarsArray, this.uriVarsMap);
            } else {
                throw new IllegalStateException("Neither URI nor URI template");
            }
        }
    }

    public static class UriTemplateRequestEntity<T> extends RequestEntity<T> {

        private final String uriTemplate;

        @Nullable
        private final Object[] uriVarsArray;

        @Nullable
        private final Map<String, ?> uriVarsMap;

        UriTemplateRequestEntity(
                @Nullable T body, @Nullable MultiValueMap<String, String> headers,
                @Nullable HttpMethod method, @Nullable Type type, String uriTemplate,
                @Nullable Object[] uriVarsArray, @Nullable Map<String, ?> uriVarsMap) {

            super(body, headers, method, null, type);
            this.uriTemplate = uriTemplate;
            this.uriVarsArray = uriVarsArray;
            this.uriVarsMap = uriVarsMap;
        }

        public String getUriTemplate() {
            return this.uriTemplate;
        }

        @Nullable
        public Object[] getVars() {
            return this.uriVarsArray;
        }

        @Nullable
        public Map<String, ?> getVarsMap() {
            return this.uriVarsMap;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!super.equals(other)) {
                return false;
            }
            UriTemplateRequestEntity<?> otherEntity = (UriTemplateRequestEntity<?>) other;
            return (ObjectUtils.nullSafeEquals(this.uriTemplate, otherEntity.uriTemplate) &&
                    ObjectUtils.nullSafeEquals(this.uriVarsArray, otherEntity.uriVarsArray) &&
                    ObjectUtils.nullSafeEquals(this.uriVarsMap, otherEntity.uriVarsMap));
        }

        @Override
        public int hashCode() {
            return (29 * super.hashCode() + ObjectUtils.nullSafeHashCode(this.uriTemplate));
        }

        @Override
        public String toString() {
            return format(getMethod(), getUriTemplate(), getBody(), getHeaders());
        }
    }

}
