package com.ibatis.dao.interactions;

import com.ibatis.scorecardmodel.bo.evaluation.ExternalDataBO;
import com.ibatis.scorecardmodel.bo.interaction.CouplePOJO;
import com.ibatis.scorecardmodel.bo.interaction.ExternalDataPOJO;
import com.ibatis.scorecardmodel.bo.interaction.InteractionBO;
import com.ibatis.scorecardmodel.bo.interaction.InteractionViewRestriction;
import com.ibatis.search.RequestFailedException;
import com.ibatis.search.SearchBO;
import com.ibatis.search.SearchCondition;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 27.4.11
 * Time: 11:47
 */
//@Repository
public class IbatisInteractionsDao {

//  private static final Logger logger = LoggerFactory.getLogger(IbatisInteractionsDao.class);
  private static final int COUPLE_LIMIT_MULTIPLIER = 3;


////  @Autowired
//  public IbatisInteractionsDao(ConfigurationProvider configProvider, PoolManager poolManager) throws InitializationException {
//    super(configProvider,poolManager);
//  }

  /**
   * Testing purposes
   *
   * @param sqlMap ibatis
   */
  public IbatisInteractionsDao(SqlMapClient sqlMap) {
//    super(sqlMap);
  }

  /**
   * Testing purposes
   */
  IbatisInteractionsDao() {
    //empty, used for integration tests
  }

//    @Override
    public Integer getInteractionCount(InteractionViewRestriction interactionViewLimit, SqlMapClient iBatis) throws RequestFailedException {
//      checkValidConditionColumn(interactionViewLimit.getBaseSearch());
      Map<String, Object> params = createParams(interactionViewLimit);
      Integer count = null;
      try {
        count = (Integer) iBatis.queryForObject("interactionsCountByFilter", params);
      } catch (SQLException e) {
//        throw new RequestFailedExceptionLogged("Request failed:", e);
      }
      return count;
    }

//  private void checkValidConditionColumn(SearchBO search) throws RequestFailedException {
//    if (search != null && search.getCondition() != null) {
//      for (SearchCondition condition : search.getCondition()) {
//        try {
//          allowedWhereColumns.valueOf(condition.getField());
//        } catch (IllegalArgumentException e) {
////          throw new RequestFailedExceptionLogged("Used column that is not supported for interaction search:" + condition.getField());
//        }
//      }
//    }
//  }



//  public Set<InteractionBO> getInteractionsView(InteractionViewRestriction interactionViewRestriction) throws RequestFailedException {
// // public Set<InteractionBO> getInteractionsView(SearchBO search, Set<UserBO> canEvaluateUsers, Set<SpeechTagSearchDTO> wantedSpeechTags, Set<SpeechTagSearchDTO> unWantedSpeechTags, SearchBO extDataSearch) throws RequestFailedException {
//    List<InteractionBO> interactionList;
//    checkValidConditionColumn(interactionViewRestriction.getBaseSearch());
//    Map parametersMap = createParams(interactionViewRestriction);
//    try {
//      long startQuery = System.currentTimeMillis();
//      interactionList = iBatis.queryForList("interactionsSelectByFilter", parametersMap);
//      logger.debug("INTERACTION QUERY took:"+ (System.currentTimeMillis() - startQuery));
//    } catch (SQLException e) {
//      throw new RequestFailedExceptionLogged("Request failed when obtaining interactions.", e);
//    }
//
//    if (interactionList == null) {
//      return Collections.emptySet();
//    } else {
//      LinkedHashSet<InteractionBO> interactionSet = new LinkedHashSet<InteractionBO>();
//      for (InteractionBO interactionBO : interactionList) {
//        interactionSet.add(interactionBO);
//      }
//      return interactionSet;
//    }
//  }
//
  private Map<String,Object> createParams(InteractionViewRestriction interactionViewRestriction) throws RequestFailedException {
    Map<String,Object> parametersMap = prepareSearchForInteractions(interactionViewRestriction.getBaseSearch());
    if (interactionViewRestriction.getExternalDataBO() != null && !interactionViewRestriction.getExternalDataBO().isEmpty()) {
      SearchBO extDataSearch = new SearchBO();
      for (ExternalDataBO externalDataBO : interactionViewRestriction.getExternalDataBO()) {
        SearchCondition condition = new SearchCondition(ExternalDataPOJO.Fields.getField(externalDataBO.getFastDataColumnNumber()), externalDataBO.getValue());
        condition.setOperator(SearchCondition.Operator.AND);
        if (externalDataBO.getComparatorEnum() != null) {
          condition.setComparator(externalDataBO.getComparatorEnum());
        }
        extDataSearch.addCondition(condition);
      }
      extDataSearch.getLastCondition().setOperator(null);
      parametersMap.put("extDataSearch", extDataSearch);
    }
    if (interactionViewRestriction.getCanViewUsers() != null && !interactionViewRestriction.getCanViewUsers().isEmpty()) {
      parametersMap.put("allowedUsers", interactionViewRestriction.getCanViewUsers().toArray());
    }
    if (interactionViewRestriction.getWantedTags() != null) {
      parametersMap.put("wantedSpeechTags", interactionViewRestriction.getWantedTags().toArray());
    }
    if (interactionViewRestriction.getUnWantedTags() != null) {
      parametersMap.put("unWantedSpeechTags", interactionViewRestriction.getUnWantedTags().toArray());
    }
    return parametersMap;
  }
//
//
//  @Override
//  public List<InteractionBO> getInteractionsComplete(InteractionViewRestriction interactionViewRestriction) throws RequestFailedException {
//    List<InteractionBO> interactionList;
//    checkValidConditionColumn(interactionViewRestriction.getBaseSearch());
//    Map parametersMap = createParams(interactionViewRestriction);
//    try {
//      interactionList = iBatis.queryForList("interactionsSelectByFilterFull", parametersMap);
//    } catch (SQLException e) {
//      throw new RequestFailedExceptionLogged("Request failed when obtaining interactions.", e);
//    }
//
//    if (interactionList == null) {
//      return Collections.emptyList();
//    } else {
//      return interactionList;
//    }
//  }

  private Map<String, Object> prepareSearchForInteractions(SearchBO search) throws RequestFailedException {
    Map<String, Object> parametersMap = new HashMap<String, Object>();
    parametersMap.put("searchBO", search);
    return parametersMap;
  }
//
//
//  @Override
//  public InteractionBO getInteraction(SearchBO search) throws RequestFailedException {
//      checkValidConditionColumn(search);
//       Map<String, Object> parameterMap = new HashMap<String, Object>();
//       parameterMap.put("searchBO", search);
//       InteractionBO interaction = null;
//       try {
//           interaction = (InteractionBO) iBatis.queryForObject("interactionsSelectByFilterFull", parameterMap);
//       } catch (SQLException e) {
//           throw new RequestFailedExceptionLogged(IbatisInteractionsDao.class.getName() + ".getInteraction(InteractionBO interaction):" + e.getMessage(), e);
//       }
//       return interaction;
//  }
//
//  public List<InteractionBO> getRelatedInteractions(SearchBO searchBO) throws SQLException {
//    Map<String, Object> parameterMap = new HashMap<String, Object>();
//    parameterMap.put("searchBO", searchBO);
//
//    return iBatis.queryForList("interactionsRelatedSelect", parameterMap);
//  }
//
//  public CouplePOJO getSingleCouplePojo(String sid) throws RequestFailedException, RequestFailedExceptionLogged {
//    try {
//      return (CouplePOJO) iBatis.queryForObject("loadCoupleBySidCompleteForInteractions", sid);
//    } catch (SQLException e) {
//      throw new RequestFailedExceptionLogged(IbatisInteractionsDao.class.getName() + ".getSingleCouplePojo:" + e.getMessage(), e);
//    }
//  }
//
//  public List<String> getExistingCouplesFromTrainingInProgressForUser(Integer evaluatedUserId, List<String> evaluationStatuses, List<String> coupleSids) throws RequestFailedException {
//    Map<String, Object> conditions = new HashMap<String, Object>();
//    conditions.put("evaluatedUser", evaluatedUserId);
//    conditions.put("coupleSids", coupleSids);
//    conditions.put("evaluationStatuses", evaluationStatuses);
//
//    try {
//      return iBatis.queryForList("existingCouplesFromTrainingInProgressForUser", conditions);
//    } catch (SQLException e) {
//      throw new RequestFailedException("IbatisEvaluationsDao:getExistingCouplesFromTrainingInProgressForUser:", e);
//    }
//  }
//
//  @Override
//  @SuppressWarnings("unchecked")
//  public Interval getCallsInterval() throws RequestFailedException {
//    try {
//      Map<String, DateTime> intervalFields = (Map<String, DateTime>) iBatis.queryForObject("getCallsInterval");
//      return new Interval(intervalFields.get("start"), intervalFields.get("end"));
//    } catch (SQLException e) {
//      throw new RequestFailedException("Failed retrieving call interval", e);
//    }
//  }
//
//  private class RequestFailedExceptionLogged extends Throwable {
//    public RequestFailedExceptionLogged(String s, SQLException e) {
//    }
//  }
}
