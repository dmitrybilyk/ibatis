package rest.api;

import com.ibatis.scorecardmodel.bo.user.RightBO;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.RequestFailedException;
import com.ibatis.search.SearchBO;
import com.ibatis.search.SearchCondition;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;
import rest.exception.LoginFailedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Convenience class for security operations.
 * <p/>
 * User: jelen
 * Date: 21.8.2009
 * Time: 15:43:36
 */
public class ScorecardSecurityUtils {

  private ScorecardSecurityUtils(){}

  /**
   * The key under which the current user is stored in the session and thread context.
   */
  public static final String CURRENT_USER_KEY = "currentUser";
  /**
   * The key under which the set of delegators is stored in the session and thread context.
   */
  public static final String DELEGATED_USER_KEY = "delegatedUser";

  /**
   * @return the currently logged user, or <code>null</code> if no user is logged in.
   */
  public static UserBO getCurrentUser() {
    return (UserBO) ThreadContext.get(CURRENT_USER_KEY);
  }

  /**
   * Checks if the currently logged user has the supplied right, or a right encompassing the requested right.
   *
   * @param rightName name of the right
   * @return <code>true</code> if currently logged user has the right, <code>false</code> otherwise.
   */
  public static boolean isCurrentUserPermitted(String rightName) {
    return SecurityUtils.getSubject().isPermitted(rightName);
  }

  /**
   * Checks if the currently logged user has the supplied right, or a right encompassing the requested right.
   *
   * @param right a right
   * @return <code>true</code> if currently logged user has the right, <code>false</code> otherwise.
   */
  public static boolean isCurrentUserPermitted(RightBO.Rights right) {
    return SecurityUtils.getSubject().isPermitted(right.toString());
  }

  /**
   * Checks if the currently logged user has at least one of the supplied rights, or a right encompassing one of the rights.
   *
   * @param rights a list of right
   * @return <code>true</code> if currently logged user some of the rights, <code>false</code> otherwise.
   */
  public static boolean isCurrentUserPermittedSome(RightBO.Rights... rights) {
    for (RightBO.Rights right : rights) {
      if (SecurityUtils.getSubject().isPermitted(right.toString())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the currently logged user has ALL of the supplied rights, or rights encompassing the requested rights.
   *
   * @param rights a set of rights
   * @return <code>true</code> if currently logged user has all the rights, <code>false</code> otherwise.
   */
  public static boolean isCurrentUserPermittedAll(RightBO.Rights... rights) {
    String[] rightStrings = new String[rights.length];
    for (int i = 0; i < rights.length; i++) {
      rightStrings[i] = rights[i].toString();
    }
    return SecurityUtils.getSubject().isPermittedAll(rightStrings);
  }

  public static UserBO login(String username, char[] password) throws LoginFailedException, RequestFailedException {
    return new UserBO();
//    return login(username, password, ScorecardImpl.getInstance().getUserManager());
  }

  public static UserBO login(String username, char[] password, UserManager userManager) throws LoginFailedException, RequestFailedException {
    AuthenticationToken token = new UsernamePasswordToken(username, password);
    Subject subj;
    try {
      subj = SecurityUtils.getSubject();
      subj.login(token);
    } catch (UnknownAccountException uae) {
      LoginFailedException loginFailedException = new LoginFailedException();
      loginFailedException.setLockStatus(LoginFailedException.Status.USERNAME_NOT_FOUND);
      throw loginFailedException;
    } catch (IncorrectCredentialsException ice) {
      LoginFailedException loginFailedException = new LoginFailedException();
      loginFailedException.setLockStatus(LoginFailedException.Status.PASSWORD_INCORRECT);
      throw loginFailedException;
    } catch (ExpiredCredentialsException ece) {
      LoginFailedException loginFailedException = new LoginFailedException();
      loginFailedException.setLockStatus(LoginFailedException.Status.PASSWORD_EXPIRED);
      throw loginFailedException;
    } catch (LockedAccountException lae) {
      LoginFailedException loginFailedException = new LoginFailedException();
      loginFailedException.setLockStatus(LoginFailedException.Status.LOCKED);
      throw loginFailedException;
    } catch (DisabledAccountException dae) {
      LoginFailedException loginFailedException = new LoginFailedException();
      loginFailedException.setLockStatus(LoginFailedException.Status.ACOUNT_DISABLED);
      throw loginFailedException;
    } catch (AuthenticationException ae) {
      LoginFailedException loginFailedException;
      if (ae.getCause() instanceof LoginFailedException) {
        loginFailedException = (LoginFailedException) ae.getCause();
      } else {
        loginFailedException = new LoginFailedException("Failed to log in with username: " + username, ae);
      }
      throw loginFailedException;
    }
    return onLoginSuccess(subj, username, userManager);
  }

  public static UserBO onLoginSuccess(@NotNull Subject subj, @NotNull String username, @NotNull UserManager userManager)
          throws RequestFailedException, LoginFailedException {
    Assert.notNull(subj, "subject must not be null");
    Assert.notNull(username, "username must not be null");
    Assert.notNull(userManager, "userManager must not be null");

    SearchBO filter = new SearchBO();
    SearchCondition cond = new SearchCondition(UserBO.Fields.LOGIN, username);
    cond.setOperator(SearchCondition.Operator.AND);
    filter.addConditionAtFirstPosition(cond);
    cond = new SearchCondition(UserBO.Fields.STATUS, UserBO.Status.ACTIVE);
    filter.addCondition(cond);
    Set<UserBO> userList = userManager.getUsersComplete(filter);

    if (userList.size() != 1) {
      subj.logout();
      LoginFailedException loginFailedException = new LoginFailedException("Cannot find unique user with username \"" + username + "\"");
      loginFailedException.printStackTrace();
      throw loginFailedException;
    } else {
      UserBO user = userList.iterator().next();
      setCurrentUser(user);
      // set the userbo attribute in the user's session. needed for universal player -> qm api communication
      subj.getSession(false).setAttribute(CURRENT_USER_KEY, user);
      return user;
    }
  }

  public static void setCurrentUser(UserBO user) {
    ThreadContext.remove(CURRENT_USER_KEY);
    ThreadContext.put(CURRENT_USER_KEY, user);
  }

  public static void logout() {
    if (ThreadContext.get(CURRENT_USER_KEY) != null) {
      ThreadContext.remove(CURRENT_USER_KEY);
      ThreadContext.getSubject().logout();
    }
  }

  //TODO: Use a better cache!!!!
  public static void invalidateCache() {
    SecurityManager manager = (SecurityManager) ThreadContext.getSecurityManager();
  }

  public static boolean isCurrentUserEvaluator(@NotNull UserBO evaluatorBO) {
    return evaluatorBO.equals(getCurrentUser()) || getDelegators().contains(evaluatorBO);
  }

  @SuppressWarnings("unchecked")
  @NotNull
  public static Set<UserBO> getDelegators() {
    Set<UserBO> delegators = (Set<UserBO>) ThreadContext.get(ScorecardSecurityUtils.DELEGATED_USER_KEY);
    return delegators != null ? delegators : Collections.<UserBO>emptySet();
  }

  private static boolean canCurrentUserLookupInteractionForAll(List<UserBO> evaluators) {
    boolean can = !evaluators.isEmpty();
    for (UserBO userBO: evaluators) {
      can &= canCurrentUserEvaluate(userBO) || getCurrentUser().canEvaluateAnyCcGroup(userBO.getBelongsToCcGroups());
    }
    return can;
  }

  public static boolean canCurrentUserEvaluate(UserBO evaluatorBO) {
    return isCurrentUserEvaluator(evaluatorBO) && isCurrentUserPermitted(RightBO.Rights.EVAL_AGENTS) ||
        isCurrentUserPermitted(RightBO.Rights.SELF_EVALUATE) && evaluatorBO.equals(getCurrentUser());
  }

  public static boolean canCurrentUserLookupInteractions(List<UserBO> evaluatedUsers) {
    if (getCurrentUser().hasRight(RightBO.Rights.INTERACTIONS_FULL_VIEW)) {
      // This user can get anything he likes.
      return true;
    }

    if (evaluatedUsers == null) {
      evaluatedUsers = new ArrayList<UserBO>();
    }
    if (evaluatedUsers.isEmpty()) {
      // This user doesn't have full view permission and tries to get unrestricted search.
      return false;
    }

    if (getCurrentUser().hasRight(RightBO.Rights.INTERACTIONS_GROUP_VIEW)) {
      return canCurrentUserLookupInteractionForAll(evaluatedUsers);
    }

    if (getCurrentUser().hasRight(RightBO.Rights.INTERACTIONS_AGENT_VIEW)) {
      return evaluatedUsers.size() == 1 && evaluatedUsers.get(0).equals(getCurrentUser());
    }

    // This user doesn't have any permission. What does he want? Cookies?
    return false;
  }
}
