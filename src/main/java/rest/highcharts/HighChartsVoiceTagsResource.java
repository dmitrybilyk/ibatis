package rest.highcharts;

//import cz.zoom.callrec.core.callstorage.ResultHandler;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseChannel;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseConfidence;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechTagBO;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.speechrec.SpeechTagManager;
//import cz.zoom.scorecard.business.bo.interaction.StatisticPOJO;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.*;

//import static cz.zoom.scorecard.rest.api.ApiUtils.translateException;
//import static cz.zoom.scorecard.rest.highcharts.HighChartsParametersManager.REPORTS_PRODUCES_TYPE;

@Singleton
@Path("/highcharts/voicetags")
public final class HighChartsVoiceTagsResource {
  private static final Logger logger = LoggerFactory.getLogger(HighChartsVoiceTagsResource.class);

  private HighChartsParametersManager parametersManager;
    public static final String REPORTS_PRODUCES_TYPE = "application/json; charset=UTF-8";
//  private SpeechTagManager speechTagManager;
//
//  @Inject
//  public HighChartsVoiceTagsResource(HighChartsParametersManager parametersManager, SpeechTagManager speechTagManager) {
//    this.parametersManager = parametersManager;
//    this.speechTagManager = speechTagManager;
//  }

  @GET
  @Path("/callsratio")
  @Produces(REPORTS_PRODUCES_TYPE)
  public Response getCallsRatio(@QueryParam("start") DateTime start,
                                @QueryParam("end") DateTime end,
                                @QueryParam("tagId") List<Integer> tagIds,
                                @QueryParam("channel") SpeechPhraseChannel channel,
                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
    return getCallsRatio(null, start, end, tagIds, channel, confidence);
  }

  @GET
  @Path("/callsratio/{maxTimeSlots:\\d+}")
  @Produces(REPORTS_PRODUCES_TYPE)
  public Response getCallsRatio(@PathParam("maxTimeSlots") int maxTimeSlots,
                                @QueryParam("start") DateTime start,
                                @QueryParam("end") DateTime end,
                                @QueryParam("tagId") List<Integer> tagIds,
                                @QueryParam("channel") SpeechPhraseChannel channel,
                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
    try {
//      HighChartsVoiceTagsParameters parameters = parametersManager.getVoiceTagsParameters(
//              maxTimeSlots, start, end, tagIds, channel, confidence);
//      return calculateStatistics(parameters);
        return Response.ok().build();
    } catch (Exception exc) {
//      throw translateException(exc);
        return Response.serverError().build();
    }
  }

  @GET
  @Path("/callsratio/{timeSlotSize}")
  @Produces(REPORTS_PRODUCES_TYPE)
  public Response getCallsRatio(@PathParam("timeSlotSize") Period timeSlotSize,
                                @QueryParam("start") DateTime start,
                                @QueryParam("end") DateTime end,
                                @QueryParam("tagId") List<Integer> tagIds,
                                @QueryParam("channel") SpeechPhraseChannel channel,
                                @QueryParam("confidence") SpeechPhraseConfidence confidence) {
//    try {
     return Response.ok().build();
//      HighChartsVoiceTagsParameters parameters = parametersManager.getVoiceTagsParameters(
//              timeSlotSize, start, end, tagIds, channel, confidence);
//      return calculateStatistics(parameters);
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
  }

//  private Response calculateStatistics(@NotNull HighChartsVoiceTagsParameters params) throws RequestFailedException {
//    logger.debug("Generating voice tags report");
//    long timer = System.currentTimeMillis();
//    try {
//      Collection<SpeechTagBO> tags = speechTagManager.getTagsComplete(params.getTagFilter());
//
//      StatisticsAggregator aggregator = new StatisticsAggregator(tags);
//
//      speechTagManager.getSpeechTagStatistics(aggregator,
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

//  private static class StatisticsAggregator implements ResultHandler<StatisticPOJO<Integer>> {
//    private Map<Integer, HighChartsDataSeries> series = new LinkedHashMap<Integer, HighChartsDataSeries>();
//    private long dataPointCount = 0;
//
//    /**
//     * Creates a new aggregator with pre-generated series ordered the same way as the provided tags.
//     * Series will have name set to the tag name.
//     * Statistics will be ignored, if there's no matching tag ID from the provided tags.
//     *
//     * @param tags the tags to include in the resulted series
//     */
//    public StatisticsAggregator(@NotNull Collection<SpeechTagBO> tags) {
//      // pre-generate series; linked hash map will ensure ordering consistent with provided tags ordering
//      for (SpeechTagBO tag : tags) {
//        HighChartsDataSeries tagSeries = new HighChartsDataSeries();
//        tagSeries.setId(String.valueOf(tag.getId()));
//        tagSeries.setName(tag.getTagName());
//        tagSeries.setData(new LinkedList<HighChartsDataPoint>());
//        series.put(tag.getId(), tagSeries);
//      }
//    }
//
//    @Override
//    public void handle(StatisticPOJO<Integer> result) {
//      Integer tagId = result.getBeanId();
//      HighChartsDataSeries currentSeries = series.get(tagId);
//      if (currentSeries == null) {
//        // with the correct filters this should not happen, so debug logging is better than trace:
//        logger.debug("Received statistic with unwanted tag ID {}, wanted tags are {}", tagId, series.keySet());
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
}
