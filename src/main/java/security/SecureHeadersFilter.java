/**
 * @author Rastislav Budinsky
 * @file SecureHeadersFilter.java
 * @brief This file is copied and adjust from https://github.com/kiegroup/appformer/ repository. Thanks for that!
 */
package security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecureHeadersFilter implements Filter {

    public static final String LOCATION = "Location";
    public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    public static final String X_FRAME_OPTIONS = "X-FRAME-OPTIONS";
    public static final String X_XSS_OPTIONS = "X-XSS-Protection";

    private static SecureHeadersConfig config;

    public static void applyHeaders(final ServletRequest request,
                                    final HttpServletResponse response) {
        if (config != null) {
            addLocation(response);
            addFrameOptions(response);
            addXSSOptions(response);

            if (request.getScheme().equals("https")) {
                addStrictTransportSecurity(response);
            }
        }
    }

    private static void addStrictTransportSecurity(HttpServletResponse response) {
        if (config.hasMaxAge() && empty(response.getHeader(STRICT_TRANSPORT_SECURITY))) {
            response.addHeader(STRICT_TRANSPORT_SECURITY,
                               config.getMaxAge());
        }
    }

    private static void addFrameOptions(HttpServletResponse response) {
        if (config.hasFrameOptions() && empty(response.getHeader(X_FRAME_OPTIONS))) {
            response.addHeader(X_FRAME_OPTIONS,
                               config.getFrameOptions());
        }
    }

    private static void addLocation(HttpServletResponse response) {
        if (config.hasLocation() && empty(response.getHeader(LOCATION))) {
            response.addHeader(LOCATION,
                               config.getLocation());
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        }
    }

    private static void addXSSOptions(HttpServletResponse response) {
        if (config.hasXSSOptions() && empty(response.getHeader(X_XSS_OPTIONS))) {
            response.addHeader(X_XSS_OPTIONS,
                               config.getXssOptions());
        }
    }

    private static boolean empty(final String content) {
        return content == null || content.trim().isEmpty();
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        config = new SecureHeadersConfig(filterConfig);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        applyHeaders(request,
                     response);

        chain.doFilter(request,
                       response);
    }
}
