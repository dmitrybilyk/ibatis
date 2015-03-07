package rest.api;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.ibatis.scorecardmodel.bo.user.RightBO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import rest.exception.AuthorizationRequiredException;
import rest.exception.NoSuchEntityException;
import rest.exception.NotAllowedException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

public final class ApiUtils {
  private static final Logger logger = LoggerFactory.getLogger(ApiUtils.class);
  private static final String ERROR_MESSAGE_KEY_HEADER = "messageToClient";

  private ApiUtils() {
    /* assertion error as defined in the book effective java.
      change to some other thing if appropriate */
    throw new AssertionError();
  }

  /**
   * used to match a format with the type of file used in the database
   * given mp3  returns AUDIO
   * given wav  returns AUDIO
   * given recd returns RECD
   *
   * @param resourceFormat the resource format to return media type for
   * @return the media type for the specified resource format
   * @throws IllegalArgumentException if unknown format is specified
   */
  @NotNull
  public static String getMediaFileType(@NotNull ApiConstants.ResourceFormatEnum resourceFormat) {
    Assert.notNull(resourceFormat, "resourceFormat must not be null");
    switch (resourceFormat) {
      case audio:
      case mp3:
      case wav:
        return EnumFileNameType.AUDIO.toString();
      case recd:
        return EnumFileNameType.RECD.toString();
      default:
        throw new IllegalArgumentException("Invalid format");
    }
  }

  /**
   * Checks the provided value is not null. Unlike Spring {@link Assert#notNull(Object, String)},
   * it throws a {@link WebApplicationException}, if the value is null.
   *
   * @param value        the value to check, must not be null
   * @param errorMessage the error message to report (and log), if the value is null
   * @throws WebApplicationException with {@link Status#BAD_REQUEST} and the {@code errorMessage}, if the value is null
   */
  public static void assertNotNull(@Nullable Object value, @NotNull String errorMessage) {
    if (value == null) {
      logger.debug("Invalid argument: {}", errorMessage);
      throw new WebApplicationException(
              Response.status(Status.BAD_REQUEST)
                      .type(MediaType.TEXT_PLAIN_TYPE)
                      .entity(errorMessage)
                      .build());
    }
  }

  /**
   * Translates provided exception to a {@link WebApplicationException}
   * that contains an appropriate {@link Response} error code and the exception message as text/plain content.
   *
   * @param exc the exception to handle
   * @return WebApplicationException with an appropriate status (error code) and error message response
   */
  @NotNull
  public static WebApplicationException translateException(@NotNull Exception exc) {
    logger.debug("Translating exception", exc);

    WebApplicationException webAppExc;

    if (exc instanceof WebApplicationException) {
      webAppExc = (WebApplicationException) exc; // no translation
    } else if (exc instanceof IllegalArgumentException) {
      webAppExc = translateException(exc, Status.BAD_REQUEST);
    } else if (exc instanceof NoSuchEntityException) {
      webAppExc = translateException(exc, Status.NOT_FOUND);
    } else if (exc instanceof NotAllowedException) {
      webAppExc = translateException(exc, Status.FORBIDDEN);
    } else if (exc instanceof AuthorizationRequiredException) {
      webAppExc = translateException(exc, Status.FORBIDDEN);
    } else {
      webAppExc = translateException(exc, Status.INTERNAL_SERVER_ERROR);
    }

    logger.debug("Generated error response: {}", webAppExc.getResponse());

    return webAppExc;
  }

  @NotNull
  private static WebApplicationException translateException(@NotNull Exception exc, @NotNull Status status) {
    return new WebApplicationException(exc,
            Response.status(status)
                    .header(ERROR_MESSAGE_KEY_HEADER, exc.getMessage())
                    .entity(Joiner.on("\nCaused by: ").join(getCauseMessages(exc)) + "\nSee server log for details.")
                    .type(MediaType.TEXT_PLAIN)
                    .build()
    );
  }

  private static List<String> getCauseMessages(Exception exc) {
    return Lists.transform(Throwables.getCausalChain(exc), new Function<Throwable, String>() {
      @Override
      public String apply(Throwable input) {
        return input.getClass().getSimpleName() + ": " + input.getMessage();
      }
    });
  }

  /**
   *Checks if user has MANAGE_QUESTIONNAIRES permission.
   * @throws AuthorizationRequiredException in case user doesn't have permission
   */
  public static void checkQuestionnairesPermission() throws AuthorizationRequiredException {
    if (!ScorecardSecurityUtils.isCurrentUserPermittedAll(RightBO.Rights.MANAGE_QUESTIONNAIRES)) {
      throw new AuthorizationRequiredException("Current user is not authorized for this operation");
    }
  }

}
