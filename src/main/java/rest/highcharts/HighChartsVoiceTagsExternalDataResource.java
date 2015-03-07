//package rest.highcharts;
//
//import cz.zoom.callrec.core.callstorage.ResultHandler;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseChannel;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseConfidence;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.media.MediaManager;
//import cz.zoom.scorecard.business.app.speechrec.SpeechTagManager;
//import cz.zoom.scorecard.business.bo.interaction.StatisticPOJO;
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
//import static cz.zoom.scorecard.rest.api.ApiUtils.assertNotNull;
//import static cz.zoom.scorecard.rest.api.ApiUtils.translateException;
//import static cz.zoom.scorecard.rest.highcharts.HighChartsParametersManager.REPORTS_PRODUCES_TYPE;
//
//@Singleton
//@Path("/highcharts/voicetagskey")
//public final class HighChartsVoiceTagsExternalDataResource {
//  private static final Logger logger = LoggerFactory.getLogger(HighChartsVoiceTagsExternalDataResource.class);
//
//  private HighChartsParametersManager parametersManager;
//  private SpeechTagManager speechTagManager;
//  private MediaManager mediaManager;
//
//  @Inject
//  public HighChartsVoiceTagsExternalDataResource(HighChartsParametersManager parametersManager,
//                                                 SpeechTagManager speechTagManager,
//                                                 MediaManager mediaManager) {
//    this.parametersManager = parametersManager;
//    this.speechTagManager = speechTagManager;
//    this.mediaManager = mediaManager;
//  }
//
//  @GET
//  @Path("/callsratio")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@QueryParam("key") String externalDataKey,
//                                @QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    return getCallsRatio(externalDataKey, null, start, end, tagIds, channel, confidence);
//  }
//
//  @GET
//  @Path("/callsratio/{maxTimeSlots:\\d+}")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@QueryParam("key") String externalDataKey,
//                                @PathParam("maxTimeSlots") int maxTimeSlots,
//                                @QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    assertNotNull(externalDataKey, "key parameter must be specified");
//    try {
//      HighChartsVoiceTagsParameters parameters = parametersManager.getVoiceTagsParameters(
//              maxTimeSlots, start, end, tagIds, channel, confidence);
//      return calculateStatistics(externalDataKey, parameters);
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  @GET
//  @Path("/callsratio/{timeSlotSize}")
//  @Produces(REPORTS_PRODUCES_TYPE)
//  public Response getCallsRatio(@QueryParam("key") String externalDataKey,
//                                @PathParam("timeSlotSize") Period timeSlotSize,
//                                @QueryParam("start") DateTime start,
//                                @QueryParam("end") DateTime end,
//                                @QueryParam("tagId") List<Integer> tagIds,
//                                @QueryParam("channel") SpeechPhraseChannel channel,
//                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    assertNotNull(externalDataKey, "key parameter must be specified");
//    try {
//      HighChartsVoiceTagsParameters parameters = parametersManager.getVoiceTagsParameters(
//              timeSlotSize, start, end, tagIds, channel, confidence);
//      return calculateStatistics(externalDataKey, parameters);
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  private Response calculateStatistics(@NotNull String externalDataKey, @NotNull HighChartsVoiceTagsParameters params) throws RequestFailedException {
//    logger.debug("Generating voice tags report");
//    long timer = System.currentTimeMillis();
//    try {
//      int column = getExternalDataColumn(externalDataKey);
//
//      StatisticsAggregator aggregator = new StatisticsAggregator();
//      speechTagManager.getSpeechTagExternalDataStatistics(aggregator,
//              column,
//              params.getInterval(),
//              params.getPeriodicity(),
//              params.getConfidence(),
//              params.getConfidenceAgent(),
//              params.getConfidenceCustomer(),
//              params.getTagIds());
//
//      logger.debug("Got {} series with {} data points", aggregator.getSeries().size(), aggregator.getDataPointCount());
//      return Response.ok(aggregator.getSeries()).build();
//    } finally {
//      logger.debug("Report took {}ms to generate", System.currentTimeMillis() - timer);
//    }
//  }
//
//  private int getExternalDataColumn(@NotNull String externalDataKey) throws RequestFailedException {
//    Map<String, Integer> extDataMap = mediaManager.getExtDataMap();
//    Integer column = extDataMap.get(externalDataKey);
//    if (column == null) {
//      throw new IllegalArgumentException(String.format(
//              "\"%s\" is not set up as a searchable column in the configuration. Columns already setup are: %s",
//              externalDataKey, extDataMap.keySet()));
//    }
//    return column;
//  }
//
//  private static class StatisticsAggregator implements ResultHandler<StatisticPOJO<String>> {
//    private Map<String, HighChartsDataSeries> series = new LinkedHashMap<String, HighChartsDataSeries>();
//    private long dataPointCount = 0;
//
//    @Override
//    public void handle(StatisticPOJO<String> result) {
//      dataPointCount++;
//
//      String externalDataValue = result.getBeanId();
//      HighChartsDataSeries currentSeries = series.get(externalDataValue);
//      if (currentSeries == null) {
//        currentSeries = new HighChartsDataSeries();
//        currentSeries.setName(externalDataValue);
//        currentSeries.setData(new LinkedList<HighChartsDataPoint>());
//        series.put(externalDataValue, currentSeries);
//      }
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
