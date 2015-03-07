//package rest.highcharts;
//
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechPhraseConfidence;
//import cz.zoom.callrec.core.callstorage.pojo.speech.SpeechTagBO;
//import cz.zoom.callrec.core.callstorage.util.SearchBO;
//import cz.zoom.callrec.core.callstorage.util.SearchBuilder;
//import org.joda.time.Interval;
//import org.joda.time.Period;
//
//import java.util.List;
//
//public class HighChartsVoiceTagsParameters {
//  private final Interval interval;
//  private final Period periodicity;
//  private final SpeechPhraseConfidence confidence;
//  private final SpeechPhraseConfidence confidenceAgent;
//  private final SpeechPhraseConfidence confidenceCustomer;
//  private final List<Integer> tagIds;
//
//  public HighChartsVoiceTagsParameters(Interval interval,
//                                       Period periodicity,
//                                       SpeechPhraseConfidence confidence,
//                                       SpeechPhraseConfidence confidenceAgent,
//                                       SpeechPhraseConfidence confidenceCustomer,
//                                       List<Integer> tagIds) {
//    this.interval = interval;
//    this.periodicity = periodicity;
//    this.confidence = confidence;
//    this.confidenceAgent = confidenceAgent;
//    this.confidenceCustomer = confidenceCustomer;
//    this.tagIds = tagIds;
//  }
//
//  public Interval getInterval() {
//    return interval;
//  }
//
//  public Period getPeriodicity() {
//    return periodicity;
//  }
//
//  public SpeechPhraseConfidence getConfidence() {
//    return confidence;
//  }
//
//  public SpeechPhraseConfidence getConfidenceAgent() {
//    return confidenceAgent;
//  }
//
//  public SpeechPhraseConfidence getConfidenceCustomer() {
//    return confidenceCustomer;
//  }
//
//  public List<Integer> getTagIds() {
//    return tagIds;
//  }
//
//  public SearchBO getTagFilter() {
//    return !tagIds.isEmpty()
//            ? new SearchBuilder(SearchBuilder.create(SpeechTagBO.Fields.SPEECH_TAG_ID, tagIds.toArray())).toSearch()
//            : new SearchBO();
//  }
//
//  @Override
//  public String toString() {
//    return String.format("HighChartsVoiceTagsParameter{%s periods within interval %s, " +
//                    "confidence >= %s, agentConfidence >= %s, customerConfidence >= %s, tags: %s}",
//            interval, periodicity, confidence, confidenceAgent, confidenceCustomer, tagIds);
//  }
//}
