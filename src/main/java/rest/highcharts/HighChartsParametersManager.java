package rest.highcharts;

import com.google.common.collect.ImmutableList;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseChannel;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseConfidence;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.interaction.InteractionManager;
//import cz.zoom.scorecard.business.configuration.ConfigurationProvider;
//import cz.zoom.scorecard.rest.api.JodaTimeRoundingUtils;
//import cz.zoom.util.configuration.config.XmlPathConvertor;
//import cz.zoom.util.configuration.config.mapping.Configuration;
//import cz.zoom.util.configuration.config.mapping.SpecifiedConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

//import static cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseChannel.*;
//import static cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseConfidence.MAYBE;

@Named
public class HighChartsParametersManager {
  private static final Logger logger = LoggerFactory.getLogger(HighChartsParametersManager.class);

  private static final String CONFIGURATION_SPECIFIED = "scorecard_webui";
  private static final String CONFIGURATION_GWT_TIMEZONE = "Gdwh/GtimeZone/Vid/value";

  public static final int DEFAULT_TIME_SLOT_COUNT = 100;
  public static final String REPORTS_PRODUCES_TYPE = "application/json; charset=UTF-8";

//  private InteractionManager interactionManager;
//  private Configuration scorecardConfiguration;
//  private JodaTimeRoundingUtils roundingUtils = new JodaTimeRoundingUtils();

  /**
   * Supported periods for automatically detected best periodicity. Must be listed in descending order.
   *
//   * @see #getBestPeriodicity(org.joda.time.Interval, int)
   */
  private List<Period> defaultPeriodicityList = ImmutableList.of(
          Period.years(1), Period.months(1), Period.weeks(1), Period.days(1),
          Period.hours(8), Period.hours(2), Period.hours(1), Period.minutes(30), Period.minutes(15));
  private DateTimeZone dwhTimeZone = DateTimeZone.getDefault();

//  @Inject
//  public HighChartsParametersManager(InteractionManager interactionManager,
//                                     @Named(ConfigurationProvider.QUALIFIER_SCORECARD) Configuration scorecardConfiguration) {
//    this.interactionManager = interactionManager;
//    this.scorecardConfiguration = scorecardConfiguration;
//  }

  @PostConstruct
  public void configure() {
//    Assert.notNull(scorecardConfiguration, "configuration must be provided");

//    SpecifiedConfiguration specifiedConfiguration = scorecardConfiguration.getSpecifiedConfiguration(CONFIGURATION_SPECIFIED);
    try {
//      String dwhTimeZoneId = (String) XmlPathConvertor.getValue(specifiedConfiguration, CONFIGURATION_GWT_TIMEZONE);
//      dwhTimeZone = DateTimeZone.forID(dwhTimeZoneId); // null => default (server time zone)
//      logger.debug("DWH time zone is to {} due to configuration parameter value: {}", dwhTimeZone, dwhTimeZoneId);
    } catch (Exception exc) {
      logger.error("Error reading configuration path {} - using default DWH time zone: {}",
              CONFIGURATION_GWT_TIMEZONE, dwhTimeZone, exc);
    }
  }

//  @NotNull
//  public HighChartsVoiceTagsParameters getVoiceTagsParameters(int maxTimeSlots,
//                                                              @Nullable DateTime start, @Nullable DateTime end,
//                                                              @NotNull List<Integer> tagIds,
//                                                              @Nullable SpeechPhraseChannel channel,
//                                                              @Nullable SpeechPhraseConfidence confidence)
//          throws RequestFailedException {
//    Interval interval = getIntervalWithDefaults(start, end);
//    Period periodicity = getBestPeriodicity(interval, maxTimeSlots);
//    interval = roundingUtils.truncateInterval(interval, periodicity);
//    confidence = confidence != null ? confidence : MAYBE;
//    channel = channel != null ? channel : ANY;
//    HighChartsVoiceTagsParameters parameters = new HighChartsVoiceTagsParameters(
//            interval, periodicity, confidence,
//            getAgentConfidence(channel, confidence),
//            getCustomerConfidence(channel, confidence),
//            tagIds);
//    logger.debug("Input converted to parameters: {}", parameters);
//    return parameters;
//  }

//  @NotNull
//  public HighChartsVoiceTagsParameters getVoiceTagsParameters(@Nullable Period periodicity,
//                                                              @Nullable DateTime start, @Nullable DateTime end,
//                                                              @NotNull List<Integer> tagIds,
//                                                              @Nullable SpeechPhraseChannel channel,
//                                                              @Nullable SpeechPhraseConfidence confidence)
//          throws RequestFailedException {
//    if (periodicity == null) {
//      return getVoiceTagsParameters(DEFAULT_TIME_SLOT_COUNT, start, end, tagIds, channel, confidence);
//    }
//
//    Interval interval = getIntervalWithDefaults(start, end);
//    interval = roundingUtils.truncateInterval(interval, periodicity);
//    confidence = confidence != null ? confidence : MAYBE;
//    channel = channel != null ? channel : ANY;
//    HighChartsVoiceTagsParameters parameters = new HighChartsVoiceTagsParameters(
//            interval, periodicity, confidence,
//            getAgentConfidence(channel, confidence),
//            getCustomerConfidence(channel, confidence),
//            tagIds);
//    logger.debug("Input converted to parameters: {}", parameters);
//    return parameters;
//  }
//
//  @NotNull
//  private Interval getIntervalWithDefaults(@Nullable DateTime start, @Nullable DateTime end)
//          throws RequestFailedException {
//    Interval interval = interactionManager.getCallsInterval();
//    interval = interval.withChronology(interval.getChronology().withZone(dwhTimeZone)); // apply DWH time zone
//    logger.debug("Interval will be bounded by first/last call: {}", interval);
//    if (start != null && interval.contains(start)) {
//      interval = interval.withStart(start);
//    }
//    if (end != null && interval.contains(end)) {
//      interval = interval.withEnd(end);
//    }
//    logger.debug("Interval with defaults applied: {}", interval);
//    return interval;
//  }
//
//  @NotNull
//  private Period getBestPeriodicity(@NotNull Interval interval, int maxTimeSlots) {
//    Assert.isTrue(maxTimeSlots > 0, "maxTimeSlots must be a positive number");
//    Period result = null;
//    for (Period timeSlotSize : defaultPeriodicityList) {
//      if (result != null &&
//              interval.getStart().withPeriodAdded(timeSlotSize, maxTimeSlots)
//                      .isBefore(interval.getEnd())) {
//        break; // too small to be able to fill the whole interval with just max allowed number of slots
//      }
//      result = timeSlotSize;
//    }
//    logger.debug("Chosen time slot size for maximum of {} time slots: {}", maxTimeSlots, result);
//    return result;
//  }
//
//  /**
//   * Require that this tag was mentioned in agent channel with this confidence - see SC-6546
//   */
//  @Nullable
//  private SpeechPhraseConfidence getAgentConfidence(@NotNull SpeechPhraseChannel channel,
//                                                    @NotNull SpeechPhraseConfidence confidence) {
//    return channel == AGENT || channel == BOTH ? confidence : null;
//  }
//
//  /**
//   * Require that this tag was mentioned in customer channel with this confidence - see SC-6546
//   */
//  @Nullable
//  private SpeechPhraseConfidence getCustomerConfidence(@NotNull SpeechPhraseChannel channel,
//                                                       @NotNull SpeechPhraseConfidence confidence) {
//    return channel == CUSTOMER || channel == BOTH ? confidence : null;
//  }
}
