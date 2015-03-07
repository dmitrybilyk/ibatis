//package rest.highcharts;
//
//import com.google.common.collect.ImmutableMap;
//import cz.zoom.callrec.core.callstorage.ResultHandler;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseChannel;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseConfidence;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.speechrec.SpeechTagManager;
//import cz.zoom.scorecard.business.bo.charts.CallDurationGroup;
//import cz.zoom.scorecard.business.bo.interaction.StatisticPOJO;
//import cz.zoom.scorecard.business.utils.Utils;
//import org.jetbrains.annotations.NotNull;
//import org.joda.time.DateTime;
//import org.joda.time.Period;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import javax.ws.rs.*;
//import javax.ws.rs.core.Response;
//import java.util.*;
//
//import static cz.zoom.scorecard.business.bo.charts.CallDurationGroup.*;
//import static cz.zoom.scorecard.rest.api.ApiUtils.translateException;
//import static cz.zoom.scorecard.rest.highcharts.HighChartsParametersManager.REPORTS_PRODUCES_TYPE;
//
///**
// * User: juan
// * Date: 16/07/14
// */
//@Singleton
//@Path("/highcharts/voicetagscallduration")
//public class HighChartsVoiceTagsCallDurationResource {
//  private static final Logger logger = LoggerFactory.getLogger(HighChartsVoiceTagsCallDurationResource.class);
//
//  public static final int SHORT_CALL_THRESHOLD = 0;
//  public static final int DEFAULT_MEDIUM_CALL_THRESHOLD = 30;
//  public static final int DEFAULT_LONG_CALL_THRESHOLD = 150;
//
//  private SpeechTagManager speechTagManager;
//  private HighChartsParametersManager parametersManager;
//
//  @Inject
//  public HighChartsVoiceTagsCallDurationResource(SpeechTagManager speechTagManager, HighChartsParametersManager parametersManager) {
//    this.speechTagManager = speechTagManager;
//    this.parametersManager = parametersManager;
//  }
//
//  @GET
//  @Path("/callsratio")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("shortCallThreshold") int shortCallThreshold,
//                                @QueryParam("longCallThreshold") int longCallThreshold,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    return getCallsRatio(null, start, end, shortCallThreshold, longCallThreshold, tagIds, channel, confidence);
//  }
//
//  @GET
//  @Path("/callsratio{maxTimeSlots:\\d+}")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@PathParam("maxTimeSlots") int maxTimeSlots,
//                                @QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("shortCallThreshold") int shortCallThreshold,
//                                @QueryParam("longCallThreshold") int longCallThreshold,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    try {
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              maxTimeSlots, start, end, tagIds, channel, confidence);
//      return calculateStatistics(highChartsParameters, shortCallThreshold, longCallThreshold);
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
//                                @QueryParam("shortCallThreshold") int shortCallThreshold,
//                                @QueryParam("longCallThreshold") int longCallThreshold,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    try {
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              timeSlotSize, start, end, tagIds, channel, confidence);
//      return calculateStatistics(highChartsParameters, shortCallThreshold, longCallThreshold);
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  private Response calculateStatistics(@NotNull HighChartsVoiceTagsParameters params,
//                                       int mediumCallThreshold,
//                                       int longCallThreshold) throws RequestFailedException {
//    logger.debug("Generating voice tag call duration report");
//    long timer = System.currentTimeMillis();
//
//    try {
//      Map<Integer, String> thresholds = getThresholds(mediumCallThreshold, longCallThreshold);
//
//      StatisticsAggregator aggregator = new StatisticsAggregator(thresholds);
//      speechTagManager.getSpeechTagCallDurationStatistics(aggregator,
//              params.getInterval(),
//              params.getPeriodicity(),
//              params.getConfidence(),
//              params.getConfidenceAgent(),
//              params.getConfidenceCustomer(),
//              params.getTagIds(),
//              new LinkedList<Integer>(thresholds.keySet()));
//      logger.debug("Got {} series with {} data points", aggregator.getSeries().size(), aggregator.getDataPointCount());
//      return Response.ok(aggregator.getSeries()).build();
//    } finally {
//      logger.debug("Report took {}ms to generate", System.currentTimeMillis() - timer);
//    }
//  }
//
//  private Map<Integer, String> getThresholds(int mediumCallThreshold, int longCallThreshold) {
//    if (mediumCallThreshold <= SHORT_CALL_THRESHOLD) {
//      mediumCallThreshold = SHORT_CALL_THRESHOLD + DEFAULT_MEDIUM_CALL_THRESHOLD;
//    }
//    if (longCallThreshold <= mediumCallThreshold) {
//      longCallThreshold = mediumCallThreshold + DEFAULT_LONG_CALL_THRESHOLD;
//    }
//
//    ResourceBundle enumConstants = Utils.getEnumConstants();
//    String prefix = CallDurationGroup.class.getSimpleName() + ".";
//    return ImmutableMap.<Integer, String>builder()
//            .put(longCallThreshold, enumConstants.getString(prefix + LONG))
//            .put(mediumCallThreshold, enumConstants.getString(prefix + MEDIUM))
//            .put(SHORT_CALL_THRESHOLD, enumConstants.getString(prefix + SHORT))
//            .build();
//  }
//
//  private static class StatisticsAggregator implements ResultHandler<StatisticPOJO<Integer>> {
//    private Map<Integer, HighChartsDataSeries> series = new LinkedHashMap<Integer, HighChartsDataSeries>();
//    private long dataPointCount = 0;
//
//    public StatisticsAggregator(Map<Integer, String> thresholds) {
//      for (Map.Entry<Integer, String> threshold : thresholds.entrySet()) {
//        HighChartsDataSeries dataSeries = new HighChartsDataSeries();
//        dataSeries.setId(String.valueOf(threshold.getKey()));
//        dataSeries.setName(threshold.getValue());
//        dataSeries.setData(new LinkedList<HighChartsDataPoint>());
//        series.put(threshold.getKey(), dataSeries);
//      }
//    }
//
//    @Override
//    public void handle(StatisticPOJO<Integer> result) {
//      Integer threshold = result.getBeanId();
//      HighChartsDataSeries currentSeries = series.get(threshold);
//      if (currentSeries == null) {
//        // with the correct filters this should not happen, so debug logging is better than trace:
//        logger.debug("Received statistic with unwanted call duration threshold {}, wanted thresholds are {}",
//                threshold, series.keySet());
//        return;
//      }
//      dataPointCount++;
//
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
//}
