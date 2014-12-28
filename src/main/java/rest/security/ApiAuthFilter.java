//package rest.security;
//
//import com.ibatis.scorecardmodel.bo.user.UserBO;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.ThreadContext;
//import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import rest.api.ApiConstants;
//import rest.api.ScorecardSecurityUtils;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Set;
//
///**
// * Shiro Authentication Filter that automatically logs in users
// * that provide special {@link ApiConstants#SESSIONID_HEADER} header which matches an existing active session.
// * <p/>
// * Alternatively supports basic username/password authentication,
// * supporting both Basic auth scheme (using Shiro support out of the box),
// * and custom auth token that is essentially the same,
// * but with token being in a different header and a different format.
// *
// * This class is not instantiated by Spring, but by Shiro. It can't be a Spring component
// */
//public class ApiAuthFilter extends BasicHttpAuthenticationFilter {
//  private static final Logger logger = LoggerFactory.getLogger(ApiAuthFilter.class);
//  private UserManager userManager;
//
//  public ApiAuthFilter() {
//    super();
//    // todo: SC-6805 ApiAuthFilter is instantiated by shiro, and no injection is possible
//    userManager = new UserManager();
//  }
//
//  @Override
//  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//    boolean loggedIn = super.isAccessAllowed(request, response, mappedValue);
//    if (loggedIn) {
//      // ensure the user is loaded:
//      Subject subject = getSubject(request, response);
//      loggedIn = onLoginSuccess(subject.getPrincipal().toString(), subject, response);
//    }
//    return loggedIn || isActiveSession(request); // allow active sessions through without login
//  }
//
//  private boolean isActiveSession(ServletRequest request) {
//    String sessionId = WebUtils.toHttp(request).getHeader(ApiConstants.SESSIONID_HEADER);
//
//    if (sessionId == null) {
//      // support for Logi authentication - it cannot send variable argument headers
//      sessionId = WebUtils.toHttp(request).getParameter(ApiConstants.SESSIONID_HEADER);
//    }
//
//    if (sessionId == null) {
//      logger.debug("No " + ApiConstants.SESSIONID_HEADER + " header or parameter provided to authenticate");
//      return false;
//    }
//
//    logger.debug("Authenticating sessionId: {}", sessionId);
//
////    Object activeSessions = WebUtils.toHttp(request).getSession().getServletContext().getAttribute(ScorecardServletContextListener.ACTIVE_SESSIONS);
////    if (activeSessions instanceof Set && ((Set) activeSessions).contains(sessionId)) {
////      return authenticateActiveSession(sessionId);
////    } else {
////      logger.info(ApiConstants.SESSIONID_HEADER + " header or parameter does not refer to an active session: {}", sessionId);
////      return false;
////    }
//    return false;
//  }
//
//  private boolean authenticateActiveSession(String sessionId) {
//    Subject requestSubject = new Subject.Builder().sessionId(sessionId).buildSubject();
//
//    Session session = requestSubject.getSession(false);
//    if (session == null) {
//      logger.error("Expected session to be active, but there's no Shiro session associated with sessionId: {}", sessionId);
//      return false;
//    }
//
//    UserBO loggedUser = (UserBO) session.getAttribute(ScorecardSecurityUtils.CURRENT_USER_KEY);
//    if (loggedUser == null) {
//      logger.error("No user associated with session: {}", session.getId());
//      return false;
//    }
//
//    ThreadContext.bind(requestSubject);
//    ScorecardSecurityUtils.setCurrentUser(loggedUser);
//
//    logger.info("Authenticated user \"{}\" for sessionId: {}", loggedUser.getName(), sessionId);
//
//    return true;
//  }
//
//  @Override
//  protected String getAuthzHeader(ServletRequest request) {
//    String authHeader = super.getAuthzHeader(request);
//    if (authHeader == null) {
//      authHeader = request.getParameter(AUTHORIZATION_HEADER); // support systems (e.g., IVR) that cannot add headers
//    }
//    return authHeader;
//  }
//
//  @Override
//  protected boolean onLoginSuccess(
//          AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
//    return onLoginSuccess(token.getPrincipal().toString(), subject, response);
//  }
//
//  private boolean onLoginSuccess(String username, Subject subject, ServletResponse response) {
//    try {
////      ScorecardSecurityUtils.onLoginSuccess(subject, username, userManager);
//      return true;
//    } catch (Exception exc) {
//      return onLoginFailure(exc, response);
//    }
//  }
//
//  @Override
//  protected boolean onLoginFailure(
//          AuthenticationToken token, AuthenticationException exc, ServletRequest request, ServletResponse response) {
//    return onLoginFailure(exc, response);
//  }
//
//  private boolean onLoginFailure(Exception exc, ServletResponse response) {
//    HttpServletResponse httpResponse = WebUtils.toHttp(response);
//    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    try {
//      httpResponse.getWriter().print(exc.getMessage());
//    } catch (IOException e) {
//      logger.error("Error writing original exception to ServletResponse due to IOException: {}", exc, e);
//    }
//    return false;
//  }
//}
