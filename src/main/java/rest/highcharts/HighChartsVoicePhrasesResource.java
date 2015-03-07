package rest.highcharts;

//import cz.zoom.callrec.core.callstorage.ResultHandler;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseBO;
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

/**
 * User: juan
 * Date: 6/11/14
 */
@Singleton
@Path("/highcharts/voicephrases")
public class HighChartsVoicePhrasesResource {
  private static final Logger logger = LoggerFactory.getLogger(HighChartsVoicePhrasesResource.class);

  private HighChartsParametersManager parametersManager;

  public static final int DEFAULT_TIME_SLOT_COUNT = 100;
  public static final String REPORTS_PRODUCES_TYPE = "application/json; charset=UTF-8";
//  private SpeechTagManager speechTagManager;

//  @Inject
//  public HighChartsVoicePhrasesResource(SpeechTagManager speechTagManager, HighChartsParametersManager parametersManager) {
//    this.speechTagManager = speechTagManager;
//    this.parametersManager = parametersManager;
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
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              maxTimeSlots, start, end, tagIds, channel, confidence);
//
//      return calculateStatistics(highChartsParameters);
      return Response.ok().build();
    } catch (Exception exc) {
      return Response.serverError().build();
//      throw translateException(exc);
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
    try {
//      HighChartsVoiceTagsParameters highChartsParameters = parametersManager.getVoiceTagsParameters(
//              timeSlotSize, start, end, tagIds, channel, confidence);
//      return calculateStatistics(highChartsParameters);
      return Response.ok().build();
    } catch (Exception exc) {
      return Response.serverError().build();
//      throw translateException(exc);
    }
  }

//  private Response calculateStatistics(@NotNull HighChartsVoiceTagsParameters params) throws RequestFailedException {
//    logger.debug("Generating voice phrases report");
//    long timer = System.currentTimeMillis();
//    try {
//      Collection<SpeechPhraseBO> phrases = getPhrases(speechTagManager.getTagsComplete(params.getTagFilter()));
//      StatisticsAggregator aggregator = new StatisticsAggregator(phrases);
//      speechTagManager.getSpeechPhraseStatistics(aggregator,
//              params.getInterval(),
//              params.getPeriodicity(),
//              params.getConfidence(),
//              params.getConfidenceAgent(),
//              params.getConfidenceCustomer(),
//              params.getTagIds());
//      logger.debug("Got {} series with {} data points", aggregator.getSeries().size(), aggregator.getDataPointCount());
//      return Response.ok(aggregator.getSeries()).build();
//    } finally {
//      logger.debug("Report took {}ms to generate", System.currentTimeMillis() - timer);
//    }
//  }

//  private Collection<SpeechPhraseBO> getPhrases(Collection<SpeechTagBO> tags) {
//    List<SpeechPhraseBO> phrases = new LinkedList<SpeechPhraseBO>();
//    for (SpeechTagBO tag : tags) {
//      phrases.addAll(tag.getPhrases());
//    }
//    return phrases;
//  }
//
//  private static class StatisticsAggregator implements ResultHandler<StatisticPOJO<Integer>> {
//    private Map<Integer, HighChartsDataSeries> series = new LinkedHashMap<Integer, HighChartsDataSeries>();
//    private long dataPointCount = 0;
//
//    /**
//     * Creates a new aggregator with pre-generated series ordered the same way as the provided phrases.
//     * Series will have name set to the phrase name.
//     * Statistics will be ignored, if there's no matching phrase ID from the provided phrases.
//     *
//     * @param phrases the phrases to include in the resulted series
//     */
//    public StatisticsAggregator(@NotNull Collection<SpeechPhraseBO> phrases) {
//      // pre-generate series; linked hash map will ensure ordering consistent with provided phrases ordering
//      for (SpeechPhraseBO phrase : phrases) {
//        HighChartsDataSeries tagSeries = new HighChartsDataSeries();
//        tagSeries.setId(String.valueOf(phrase.getId()));
//        tagSeries.setName(phrase.getPhraseText());
//        tagSeries.setData(new LinkedList<HighChartsDataPoint>());
//        series.put(phrase.getId(), tagSeries);
//      }
//    }
//
//    @Override
//    public void handle(StatisticPOJO<Integer> result) {
//      Integer phraseId = result.getBeanId();
//      HighChartsDataSeries currentSeries = series.get(phraseId);
//      if (currentSeries == null) {
//        // with the correct filters this should not happen, so debug logging is better than trace:
//        logger.debug("Received statistic with unwanted phrase ID {}, wanted phrases are {}", phraseId, series.keySet());
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
