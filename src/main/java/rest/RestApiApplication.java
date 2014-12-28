package rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Configures REST API application - things like which features to use and which packages to scan for resources.
 * For servlet container 2.5 this must be specified in web.xml as following:
 * <pre>{@code
 * <servlet>
 *   <servlet-name>RestApiServlet</servlet-name>
 *   <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
 *   <init-param>
 *     <param-name>javax.ws.rs.Application</param-name>
 *     <param-value>cz.zoom.scorecard.rest.RestApiApplication</param-value>
 *   </init-param>
 * </servlet>
 * }</pre>
 */
@ApplicationPath("/api") // ignored if defined via servlet-mapping in web.xml
public class RestApiApplication extends ResourceConfig {
  public RestApiApplication() {
    register(MultiPartFeature.class);
    register(JacksonFeature.class);
    packages("rest");
  }
}
