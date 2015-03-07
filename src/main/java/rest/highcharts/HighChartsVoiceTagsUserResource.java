package rest.highcharts;

import com.google.common.base.Joiner;
import com.ibatis.scorecardmodel.bo.interaction.StatisticPOJO;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchBO;
import com.ibatis.search.SearchBuilder;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.*;


/**
 * User: juan
 * Date: 6/11/14
 */
@Singleton
@Path("/highcharts/voicetagsuser")
public class HighChartsVoiceTagsUserResource {
  private static final Logger logger = LoggerFactory.getLogger(HighChartsVoiceTagsUserResource.class);

  private HighChartsParametersManager parametersManager;
    public static final int DEFAULT_TIME_SLOT_COUNT = 100;
    public static final String REPORTS_PRODUCES_TYPE = "application/json; charset=UTF-8";
//  private SpeechTagManager speechTagManager;
//  private UserManager userManager;

//  @Inject
//  public HighChartsVoiceTagsUserResource(HighChartsParametersManager parametersManager,
//                                         SpeechTagManager speechTagManager,
//                                         UserManager userManager) {
//    this.parametersManager = parametersManager;
////    this.speechTagManager = speechTagManager;
////    this.userManager = userManager;
//  }

  @GET
  @Path("/callsratio")
  @Produces(REPORTS_PRODUCES_TYPE)
  public Response getCallsRatio(@QueryParam("start") DateTime start,
                                @QueryParam("end") DateTime end,
                                @QueryParam("groupId") List<Integer> groupIds,
                                @QueryParam("tagId") List<Integer> tagIds,
                                @QueryParam("channel") SpeechPhraseChannel channel,
                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
    return getCallsRatio(null, start, end, groupIds, tagIds, channel, confidence);
  }

  @GET
  @Path("/callsratio/{maxTimeSlots:\\d+}")
  @Produces(REPORTS_PRODUCES_TYPE)
  public Response getCallsRatio(@PathParam("maxTimeSlots") int maxTimeSlots,
                                @QueryParam("start") DateTime start,
                                @QueryParam("end") DateTime end,
                                @QueryParam("groupId") List<Integer> groupIds,
                                @QueryParam("tagId") List<Integer> tagIds,
                                @QueryParam("channel") SpeechPhraseChannel channel,
                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
    try {
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              maxTimeSlots, start, end, tagIds, channel, confidence);
//      return calculateStatistics(highChartsParameters, groupIds);
        return Response.ok().build();
    } catch (Exception exc) {
        return Response.serverError().build();
    }
  }

  @GET
  @Path("/callsratio/{timeSlotSize}")
  @Produces(REPORTS_PRODUCES_TYPE)
  public Response getCallsRatio(@PathParam("timeSlotSize") Period timeSlotSize,
                                @QueryParam("start") DateTime start,
                                @QueryParam("end") DateTime end,
                                @QueryParam("groupId") List<Integer> groupIds,
                                @QueryParam("tagId") List<Integer> tagIds,
                                @QueryParam("channel") SpeechPhraseChannel channel,
                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
    try {
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              timeSlotSize, start, end, tagIds, channel, confidence);
//      return calculateStatistics(highChartsParameters, groupIds);
        return Response.ok().build();
    } catch (Exception exc) {
        return Response.serverError().build();
//      throw translateException(exc);
    }
  }

//  private Response calculateStatistics(@NotNull HighChartsVoiceTagsParameters params, @NotNull List<Integer> groupIds)
  private Response calculateStatistics(@NotNull List<Integer> groupIds)
          throws RequestFailedException, NotAllowedException {
    Assert.notEmpty(groupIds, "at least one groupId must be specified");

    logger.debug("Generating voice tag user group report");
    long timer = System.currentTimeMillis();
    try {
      SearchBO userFilter = new SearchBuilder()
              .orderBy(UserBO.Fields.SURNAME)
              .orderBy(UserBO.Fields.USER_NAME)
              .toSearch();
      Collection<UserBO> users = new ArrayList<UserBO>();

//              userManager.getUsersInGroupView(groupIds, userFilter, false);
//      StatisticsAggregator aggregator = new StatisticsAggregator(users);
//      if (!users.isEmpty()) { // don't calculate statistics if there are no users in the specified groups
//        speechTagManager.getSpeechTagUserStatistics(aggregator,
//                params.getInterval(),
//                params.getPeriodicity(),
//                params.getConfidence(),
//                params.getConfidenceAgent(),
//                params.getConfidenceCustomer(),
//                params.getTagIds(),
//                groupIds);
//      }
//      logger.debug("Got {} series with {} data points", aggregator.getSeries().size(), aggregator.getDataPointCount());
      return Response.ok(users).build();
    } finally {
      logger.debug("Report took {}ms to generate", System.currentTimeMillis() - timer);
    }
  }

//  private static class StatisticsAggregator implements ResultHandler<StatisticPOJO<Integer>> {
  private static class StatisticsAggregator {
    private Map<Integer, HighChartsDataSeries> series = new LinkedHashMap<Integer, HighChartsDataSeries>();
    private long dataPointCount = 0;

    /**
     * Creates a new aggregator with pre-generated series ordered the same way as the provided users.
     * Series will have name set to the user name.
     * Statistics will be ignored, if there's no matching user ID from the provided users.
     *
     * @param users the users to include in the resulted series
     */
    public StatisticsAggregator(@NotNull Collection<UserBO> users) {
      // pre-generate series; linked hash map will ensure ordering consistent with provided tags ordering
      Joiner nameJoiner = Joiner.on(", ").skipNulls();
      for (UserBO user : users) {
        HighChartsDataSeries groupSeries = new HighChartsDataSeries();
        groupSeries.setId(String.valueOf(user.getId()));
        groupSeries.setName(nameJoiner.join(user.getSurname(), user.getName()));
        groupSeries.setData(new LinkedList<HighChartsDataPoint>());
        series.put(user.getId(), groupSeries);
      }
    }

//    @Override
    public void handle(StatisticPOJO<Integer> result) {
      Integer userId = result.getBeanId();
      HighChartsDataSeries currentSeries = series.get(userId);
      if (currentSeries == null) {
        // with the correct filters this should not happen, so debug logging is better than trace:
        logger.debug("Received statistic with unwanted user ID {}, wanted user IDs are {}", userId, series.keySet());
        return;
      }
      dataPointCount++;
      HighChartsDataPoint dataPoint = new HighChartsDataPoint();
      dataPoint.setX(result.getTimeSlot().getTime());
      dataPoint.setY(result.getValue());
      currentSeries.getData().add(dataPoint);
    }

    public long getDataPointCount() {
      return dataPointCount;
    }

    public Collection<HighChartsDataSeries> getSeries() {
      return series.values();
    }
  }

}
