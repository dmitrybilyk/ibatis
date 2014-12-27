package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.evaluation.CriteriaBO;
import com.ibatis.scorecardmodel.bo.question.QuestformBO;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 27.4.11
 * Time: 14:18
 */
@XmlRootElement(name = "couple")
@XmlAccessorType(XmlAccessType.FIELD)
public class InteractionBO extends BaseBean {
  public static final String UNKNOWN_DIRECTION = "UNKNOWN";
  @XmlElement(name = "coupleId")
  private Integer interactionId;
  private String coupleSid;
  private Integer length;
  private Date startDate;
  private Date stopDate;
  private Integer cftypes;
  private Integer evaluationId;
  private Double score;
  private QuestformBO.ScoringSystem scoringSystem;
  private String callingNumber;
  private String calledNumber;
  private String calledAgent;
  private String callingAgent;
  private TrackableLinkedHashSet<UserBO> agentSet;
  private Set<String> clientSet;
  private TrackableLinkedHashSet<MediaFileBO> mediaFiles;
  private CriteriaBO.CallDirection direction;
  private Double confidence; //counted from searched values
  private Integer couplesCount;

  public InteractionBO() {
    mediaFiles = new TrackableLinkedHashSet<MediaFileBO>();
    agentSet = new TrackableLinkedHashSet<UserBO>();
    clientSet = new HashSet<String>();
  }

  public enum Fields {
    COUPLE_LENGTH("length"),
    COUPLE_SID("coupleSid"),
    CLIENT_SET("clientSet"),
    AGENT_SET("agentSet"),
    INTERACTION_ID("interactionId"),
    START_DATE("startDate"),
    STOP_DATE("stopDate"),
    CALLING_NUMBER("callingNumber"),
    CALLED_NUMBER("calledNumber"),
    CALLING_AGENT("callingAgent"),
    CALLED_AGENT("calledAgent"),
    EVALUATION_ID("evaluationId"),
    SCORE("score"),
    SCORING_SYSTEM("scoringSystem"),
    CALL_DIRECTION("direction"),
    CONFIDENCE("confidence"),
    COUPLES_COUNT("couplesCount"),
    SPEECH_TAGS("speechTags"),
    SCORING_SYSTEM_ENUM("scoringSystemEnum"),
    MEDIA_FILES("mediaFiles");

    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }

  public enum CallType {
    NORMAL, BARGE, CONF, RECONN, PARK, UNPARK
  }

  public enum Type {
    AUDIO, VIDEO;
  }

  public enum SavedSearch {
    CUSTOM, MANAGE;
  }

  public Set<String> getClientSet() {
    return this.clientSet;
  }

  public void setClientSet(Set<String> clientSet) {
    if (clientSet != null && clientSet != this.clientSet) {
      this.clientSet = clientSet;
      this.markChanged(true);
    }
  }

  public void addClient(String client) {
    this.clientSet.add(client);
  }

  public void removeClient(String client) {
    this.clientSet.remove(client);
  }

  public Set<UserBO> getAgentSet() {
    return agentSet;
  }

  public void setAgentSet(TrackableLinkedHashSet<UserBO> agentSet) {
    this.agentSet = agentSet;
  }

  public void addAgent(UserBO agent) {
    if (agent == null) {
      throw new IllegalArgumentException("cannot add null agent to interactionbo.agentset");
    }
    this.agentSet.add(agent);
  }

  public void removeAgent(UserBO agent) {
    this.agentSet.remove(agent);
  }

  @Override
  public Integer getId() {
    return interactionId;
  }

  @Override
  public void setId(Integer value) {
    setInteractionId(value);
  }

  //used by ibatis
  public void setInteractionId(Integer interactionId) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.interactionId = interactionId;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    markChanged(length, this.length);
    this.length = length;
  }

  public String getCoupleSid() {
    return coupleSid;
  }

  public void setCoupleSid(String coupleSid) {
    markChanged(coupleSid, this.coupleSid);
    this.coupleSid = coupleSid;
  }

  public Integer getInteractionId() {
    return this.interactionId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    markChanged(startDate, this.startDate);
    this.startDate = startDate;
  }

  public Date getStopDate() {
    return stopDate;
  }

  public void setStopDate(Date stopDate) {
    markChanged(stopDate, this.stopDate);
    this.stopDate = stopDate;
  }

  public Integer getCftypes() {
    return cftypes;
  }

  public void setCftypes(Integer cftypes) {
    this.cftypes = cftypes;
  }

  public Integer getEvaluationId() {
    return evaluationId;
  }

  public void setEvaluationId(Integer evaluationId) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.evaluationId = evaluationId;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    markChanged(score, this.score);
    this.score = score;
  }

  public QuestformBO.ScoringSystem getScoringSystemEnum() {
    return scoringSystem;
  }

  public String getScoringSystem() {
    return scoringSystem == null ? "" : scoringSystem.name();
  }

  public void setScoringSystemEnum(QuestformBO.ScoringSystem scoringSystem) {
    markChanged(scoringSystem, this.scoringSystem);
    this.scoringSystem = scoringSystem;
  }

  private void setScoringSystem(String scoringSystem) {
    if (scoringSystem != null) {
      setScoringSystemEnum(QuestformBO.ScoringSystem.valueOf(scoringSystem));
    }
  }

  public Set<MediaFileBO> getMediaFiles() {
    return mediaFiles;
  }

  public boolean addMediaFile(MediaFileBO mediaFile) {
    return this.mediaFiles.add(mediaFile);
  }

  public boolean removeMediaFile(MediaFileBO mediaFile) {
    return this.mediaFiles.remove(mediaFile);
  }

  public void addMediaFiles(Collection<MediaFileBO> mediaFiles) {
    this.mediaFiles.addAll(mediaFiles);
  }

  public void removeMediaFiles(Collection<MediaFileBO> mediaFiles) {
    this.mediaFiles.removeAll(mediaFiles);
  }

  @Override
  public void lock() {
    super.lock();
    agentSet.lock();
    mediaFiles.lock();
  }

  @Override
  public boolean hasAnythingChanged() {
    return super.hasAnythingChanged() || agentSet.hasAnythingChanged() || mediaFiles.hasAnythingChanged();
  }

  @Override
  public BaseBean newInstance() {
    return new InteractionBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.START_DATE, SearchOrder.Direction.DESC));
    return ordering;
  }


  @Override
  public int hashCode() {
    return ((getInteractionId() == null) ? 0 : getInteractionId().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof InteractionBO))
      return false;
    InteractionBO other = (InteractionBO) obj;
    if (getInteractionId() == null || other.getInteractionId() == null)
      return false;
    return getInteractionId().equals(other.getInteractionId());
  }

  //todo to string
  public String getCallingNumber() {
    return callingNumber;
  }

  public void setCallingNumber(String callingNumber) {
    this.callingNumber = callingNumber;
  }

  public String getCalledNumber() {
    return calledNumber;
  }

  public void setCalledNumber(String calledNumber) {
    this.calledNumber = calledNumber;
  }

  public String getCalledAgent() {
    return calledAgent;
  }

  public void setCalledAgent(String calledAgent) {
    this.calledAgent = calledAgent;
  }

  public String getCallingAgent() {
    return callingAgent;
  }

  public void setCallingAgent(String callingAgent) {
    this.callingAgent = callingAgent;
  }


  public CriteriaBO.CallDirection getDirection() {
    return direction;
  }


  public void setDirectionEnum(CriteriaBO.CallDirection direction) {
    this.direction = direction;
  }

  public void setDirection(String direction) {
    if (UNKNOWN_DIRECTION.equalsIgnoreCase(direction)) {
      this.direction = CriteriaBO.CallDirection.ALL;
    } else {
      this.direction = CriteriaBO.CallDirection.getField(direction);
    }
  }

//  public List<VoiceTagBO> getVoiceTags() {
//    if (mediaFiles == null || mediaFiles.isEmpty()) {
//      return Collections.emptyList();
//    }

//    List<VoiceTagBO> voiceTagBOs = new ArrayList<VoiceTagBO>();
//    for (MediaFileBO mediaFile : mediaFiles) {
//      if (mediaFile.getVoicetags() != null) {
//        voiceTagBOs.addAll(mediaFile.getVoicetags());
//      }
//    }
//    return voiceTagBOs;
//  }

//  public List<SpeechTagBO> getSpeechTags() {
//    if (mediaFiles == null || mediaFiles.isEmpty()) {
//      return Collections.emptyList();
//    }

//    List<SpeechTagBO> speechTags = new ArrayList<SpeechTagBO>();
//    for (MediaFileBO mediaFile : mediaFiles) {
//      if (mediaFile.getVoicetags() != null) {
//        for (VoiceTagBO voiceTag : mediaFile.getVoicetags()) {
//          if (voiceTag.getPhrase() != null) {
//            speechTags.addAll(voiceTag.getPhrase().getSpeechTags());
//          }
//        }
//      }
//    }
//    return speechTags;
//  }


  public Double getConfidence() {
    return confidence;
  }

  public void setConfidence(Double confidence) {
    this.confidence = confidence;
  }

  public Integer getCouplesCount() {
    return couplesCount;
  }

  public void setCouplesCount(Integer couplesCount) {
    this.couplesCount = couplesCount;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("InteractionBO{")
            .append(", interactionBoId=").append(interactionId)
            .append(", length=").append(length)
            .append(", coupleSid=").append(coupleSid)
            .append(", startDate=").append(startDate)
            .append(", evaluationId=").append(evaluationId)
            .append(", score=").append(score)
            .append(", scoringSystem=").append(scoringSystem)
            .append('}').toString();
  }

}
