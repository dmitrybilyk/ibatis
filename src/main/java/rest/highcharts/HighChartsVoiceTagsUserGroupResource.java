//package rest.highcharts;
//
//import cz.zoom.callrec.core.callstorage.ResultHandler;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseChannel;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseConfidence;
//import cz.zoom.callrec.core.callstorage.util.SearchBO;
//import cz.zoom.callrec.core.callstorage.util.SearchBuilder;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.speechrec.SpeechTagManager;
//import cz.zoom.scorecard.business.app.user.UserManager;
//import cz.zoom.scorecard.business.bo.interaction.StatisticPOJO;
//import cz.zoom.scorecard.business.bo.user.CcGroupBO;
//import org.jetbrains.annotations.NotNull;
//import org.joda.time.DateTime;
//import org.joda.time.Period;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Assert;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import javax.ws.rs.*;
//import javax.ws.rs.core.Response;
//import java.util.*;
//
//import static cz.zoom.scorecard.rest.api.ApiUtils.translateException;
//import static cz.zoom.scorecard.rest.highcharts.HighChartsParametersManager.REPORTS_PRODUCES_TYPE;
//
///**
// * User: juan
// * Date: 6/11/14
// */
//@Singleton
//@Path("/highcharts/voicetagsgroup")
//public class HighChartsVoiceTagsUserGroupResource {
//  private static final Logger logger = LoggerFactory.getLogger(HighChartsVoiceTagsUserGroupResource.class);
//
//  private HighChartsParametersManager parametersManager;
//  private SpeechTagManager speechTagManager;
//  private UserManager userManager;
//
//  @Inject
//  public HighChartsVoiceTagsUserGroupResource(HighChartsParametersManager parametersManager,
//                                              SpeechTagManager speechTagManager,
//                                              UserManager userManager) {
//    this.parametersManager = parametersManager;
//    this.speechTagManager = speechTagManager;
//    this.userManager = userManager;
//  }
//
//  @GET
//  @Path("/callsratio")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("groupId") List<Integer> groupIds,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    return getCallsRatio(null, start, end, groupIds, tagIds, channel, confidence);
//  }
//
//  @GET
//  @Path("/callsratio/{maxTimeSlots:\\d+}")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@PathParam("maxTimeSlots") int maxTimeSlots,
//                                @QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("groupId") List<Integer> groupIds,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    try {
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              maxTimeSlots, start, end, tagIds, channel, confidence);
//      return calculateStatistics(highChartsParameters, groupIds);
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  @GET
//  @Path("/callsratio/{timeSlotSize}")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@PathParam("timeSlotSize") Period timeSlotSize,
//                                @QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("groupId") List<Integer> groupIds,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    try {
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              timeSlotSize, start, end, tagIds, channel, confidence);
//      return calculateStatistics(highChartsParameters, groupIds);
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  private Response calculateStatistics(@NotNull HighChartsVoiceTagsParameters params, @NotNull List<Integer> groupIds) throws RequestFailedException {
//    logger.debug("Generating voice tag user group report");
//    long timer = System.currentTimeMillis();
//    try {
//      Collection<CcGroupBO> groups = userManager.getGroupsBasic(getGroupsSearch(groupIds));
//      Assert.notEmpty(groups, "Parameters must match at least one group");
//      StatisticsAggregator aggregator = new StatisticsAggregator(groups);
//      speechTagManager.getSpeechTagUserGroupStatistics(aggregator,
//              params.getInterval(),
//              params.getPeriodicity(),
//              params.getConfidence(),
//              params.getConfidenceAgent(),
//              params.getConfidenceCustomer(),
//              params.getTagIds(),
//              groupIds);
//      logger.debug("Got {} series with {} data points", aggregator.getSeries().size(), aggregator.getDataPointCount());
//      return Response.ok(aggregator.getSeries()).build();
//    } finally {
//      logger.debug("Report took {}ms to generate", System.currentTimeMillis() - timer);
//    }
//  }
//
//  private SearchBO getGroupsSearch(List<Integer> groupIds) {
//    SearchBuilder groupFilter = new SearchBuilder();
//    if (!groupIds.isEmpty()) {
//      groupFilter.and(SearchBuilder.create(CcGroupBO.Fields.CCGROUP_ID, groupIds.toArray()));
//    }
//    return groupFilter.toSearch();
//  }
//
//  private static class StatisticsAggregator implements ResultHandler<StatisticPOJO<Integer>> {
//    private Map<Integer, HighChartsDataSeries> series = new LinkedHashMap<Integer, HighChartsDataSeries>();
//    private long dataPointCount = 0;
//
//    /**
//     * Creates a new aggregator with pre-generated series ordered the same way as the provided groups.
//     * Series will have name set to the group name.
//     * Statistics will be ignored, if there's no matching group ID from the provided groups.
//     *
//     * @param groups the groups to include in the resulted series
//     */
//    public StatisticsAggregator(@NotNull Collection<CcGroupBO> groups) {
//      // pre-generate series; linked hash map will ensure ordering consistent with provided tags ordering
//      for (CcGroupBO group : groups) {
//        HighChartsDataSeries groupSeries = new HighChartsDataSeries();
//        groupSeries.setId(String.valueOf(group.getId()));
//        groupSeries.setName(group.getName());
//        groupSeries.setData(new LinkedList<HighChartsDataPoint>());
//        series.put(group.getId(), groupSeries);
//      }
//    }
//
//    @Override
//    public void handle(StatisticPOJO<Integer> result) {
//      Integer groupId = result.getBeanId();
//      HighChartsDataSeries currentSeries = series.get(groupId);
//      if (currentSeries == null) {
//        // with the correct filters this should not happen, so debug logging is better than trace:
//        logger.debug("Received statistic with unwanted group ID {}, wanted group IDs are {}", groupId, series.keySet());
//        return;
//      }
//      dataPointCount++;
//      HighChartsDataPoint dataPoint = new HighChartsDataPoint();
//      dataPoint.setX(result.getTimeSlot().getTime());
//      dataPoint.setY(result.getValue());
//      currentSeries.getData().add(dataPoint);
//    }
//
//    public long getDataPointCount() {
//      return dataPointCount;
//    }
//
//    public Collection<HighChartsDataSeries> getSeries() {
//      return series.values();
//    }
//  }
//
//}
