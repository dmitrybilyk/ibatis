package com.ibatis.dao.interactions;

import com.ibatis.scorecardmodel.bo.interaction.CouplePOJO;
import com.ibatis.scorecardmodel.bo.interaction.InteractionBO;
import com.ibatis.scorecardmodel.bo.interaction.InteractionViewRestriction;
import com.ibatis.search.RequestFailedException;
import com.ibatis.search.SearchBO;
import org.joda.time.Interval;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 27.4.11
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public interface InteractionsDao {

  /** Where condition supports searching only by columns mentioned in allowedWhereColumns.
   *
   */
  enum allowedWhereColumns {
    COUPLE_ORIGINAL_CALLED_NR,
    COUPLE_CALLING_NR,
    COUPLE_LENGTH,
    COUPLE_START_TS,
    COUPLE_TIME_OF_DAY,
    COUPLE_DAY_OF_WEEK,
    COUPLE_TYPE,
    COUPLE_SID,
    COUPLE_CALLING_AGENT,
    COUPLE_CALLED_AGENT,
    COUPLE_DESCRIPTION
  }

  Integer getInteractionCount(InteractionViewRestriction interactionViewRestriction) throws RequestFailedException;


  Set<InteractionBO> getInteractionsView(InteractionViewRestriction interactionViewRestriction) throws RequestFailedException;

  List<InteractionBO> getInteractionsComplete(InteractionViewRestriction interactionViewRestriction) throws RequestFailedException;



  /**
   * Retrieve the associated Interaction to the Couple SID
   *
   * @param search
   * @return InteractionBO
   * @throws RequestFailedException
   */
  InteractionBO getInteraction(SearchBO search) throws  RequestFailedException;

  /**Loads interaction that have same callid. This means it returns couples that belong to same session.
   * Typical for transfered calls
   *
   * @param searchBO object with conditions to be used in the query (typically couple sid)
   * @return List of found interactions.
   * @throws java.sql.SQLException
   */
  public List <InteractionBO> getRelatedInteractions(SearchBO searchBO) throws SQLException;

  /**
   * Searches for couples where the couple sid matches the parameter
   * @param sid the couple sid of the couple to search
   * @return the couple pojo which matches the criteria
   * @throws RequestFailedException
   */
  CouplePOJO getSingleCouplePojo(String sid) throws RequestFailedException;

  /**
   * Checks if the user specified has any of the couple sids specified in any training where he's the evaluated user
   * @param evaluatedUserId The evaluated used in the training. For QM API, it will be the current user when he's going
   *                        to listen to the interaction
   * @param evaluationStatuses evaluation status for training in which the user is allowed to listen to the interaction
   * @param coupleSids the couple sids to check if they belong to the evaluation
   * @return a list of couple sids, from the original couplesids parameter, that were found on the training for the user
   * specified
   * @throws RequestFailedException
   */
  List<String> getExistingCouplesFromTrainingInProgressForUser(Integer evaluatedUserId, List<String> evaluationStatuses, List<String> coupleSids) throws RequestFailedException;

  /**
   * Interval of the known calls such that all known calls have started in this interval.
   * That is the start of the interval is the start of the first known call,
   * and the end of the interval is the start of the last known call.
   *
   * @return interval of time which all calls belong to, or an empty interval from-now-to-now, if no calls were made
   */
  Interval getCallsInterval() throws RequestFailedException, RequestFailedException;

}
