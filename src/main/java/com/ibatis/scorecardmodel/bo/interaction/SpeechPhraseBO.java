package com.ibatis.scorecardmodel.bo.interaction;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.search.SearchOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Jamel Gasmi
 * Date: 10/28/11
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */

@XmlRootElement(name = "speechPhrase")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpeechPhraseBO extends BaseBean {
  private static final long serialVersionUID = -1L;

  public enum Fields {
    SPEECH_PHRASE_ID("phraseid"), TEXT("phraseText"), CONFIDENCE("confidence"), INTERACTIONS("interactionCount"), ENABLED("enabled"), STATE("state"), LAST_COUNT_DATE("lastCountDate");
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values()) {
        if (field.fieldName.equals(fieldName)) {
          return field;
        }
      }
      return null;
    }
  }

  private Integer phraseid;
  private String phraseText;
  private SpeechTagBO.SpeechState state;
  private Double confidence;
  private Integer interactionCount;
  private Date lastCountDate;
  private Boolean enabled;
  private TrackableLinkedHashSet<SpeechTagBO> speechTags = new TrackableLinkedHashSet<SpeechTagBO>();

  public SpeechPhraseBO() {
    phraseText = "";
    state = SpeechTagBO.SpeechState.READY;
    enabled = true;
  }

  /**
   * ********************
   * Getters and Setters *
   * *********************
   */

  @Override
  public Integer getId() {
    return getPhraseid();
  }

  @Override
  public void setId(Integer value) {
    setPhraseid(value);
  }

  public Integer getPhraseid() {
    return phraseid;
  }

  //used by ibatis
  public void setPhraseid(Integer phraseId) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.phraseid = phraseId;
  }

  public String getPhraseText() {
    return phraseText;
  }

  public void setPhraseText(String phraseText) {
    String local = phraseText == null ? "" : phraseText.trim();
    markChanged(this.phraseText, local);
    this.phraseText = local;
  }

  public Date getLastCountDate() {
    return lastCountDate == null ? null : new Date(lastCountDate.getTime());
  }

  public void setLastCountDate(Date lastCountDate) {
    Date local = (lastCountDate == null ? null : new Date(lastCountDate.getTime()));
    markChanged(this.lastCountDate, local);
    this.lastCountDate = local;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getState() {
    return state == null ? null : state.getState();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setState(String state) {
    setStateEnum(SpeechTagBO.SpeechState.valueOf(state));
  }

  public SpeechTagBO.SpeechState getStateEnum() {
    return state;
  }

  public void setStateEnum(SpeechTagBO.SpeechState state) {
    markChanged(this.state, state);
    this.state = state;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    markChanged(this.enabled, enabled);
    this.enabled = enabled;
  }

  public Double getConfidence() {
    return confidence;
  }

  public void setConfidence(Double confidence) {
    markChanged(this.confidence, confidence);
    this.confidence = confidence;
  }

  public Integer getInteractionCount() {
    return interactionCount;
  }

  public void setInteractionCount(Integer interactionCount) {
    markChanged(this.interactionCount, interactionCount);
    this.interactionCount = interactionCount;
  }

  public TrackableLinkedHashSet<SpeechTagBO> getSpeechTags() {
    return speechTags;
  }

  public void setSpeechTags(TrackableLinkedHashSet<SpeechTagBO> speechTags) {
    this.speechTags = speechTags;
  }

  public void addSpeechTag(SpeechTagBO speechTag) {
    this.speechTags.add(speechTag);
  }

  public void removeSpeechTag(SpeechTagBO speechTag) {
    this.speechTags.remove(speechTag);
  }

  @Override
  public void lock() {
    super.lock();
    speechTags.lock();
  }

  public boolean addTag(SpeechTagBO tag) {
    markChanged(true);
    return this.speechTags.add(tag);
  }

  public boolean removeTag(SpeechTagBO tag) {
    markChanged(true);
    return this.speechTags.remove(tag);
  }

  @Override
  public int hashCode() {
    return ((getPhraseid() == null) ? 0 : getPhraseid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof SpeechPhraseBO)) {
      return false;
    }
    SpeechPhraseBO other = (SpeechPhraseBO) obj;
    if ((getPhraseid() == null) || (other.getPhraseid() == null)) {
      return false;
    }
    return getPhraseid().equals(other.getPhraseid());
  }

  /**
   * Constructs a <code>String</code> with all attributes
   * in name = value format.
   *
   * @return a <code>String</code> representation
   *         of this object.
   */
  @Override
  public String toString() {
    final String TAB = "\n    ";
    StringBuilder retValue = new StringBuilder();

    retValue.append("\nSpeechPhraseBO ( \n").append(TAB)
            .append("phraseid = ").append(this.phraseid).append(TAB)
            .append("phraseText = ").append(this.phraseText).append(TAB)
            .append("interactionCount = ").append(this.interactionCount).append(TAB)
            .append("confidence = ").append(this.confidence).append(TAB)
            .append("state = ").append(this.state).append(TAB)
            .append("speechTags = ").append(this.speechTags).append(TAB)
            .append("lastCountDate = ").append(this.lastCountDate).append(TAB)
            .append(" )\n");

    return retValue.toString();
  }

  @Override
  public BaseBean newInstance() {
    return new SpeechPhraseBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.TEXT, SearchOrder.Direction.ASC));
    return ordering;
  }

  /**
   *
   * @return same object but not locked. without tags
   */
  public SpeechPhraseBO getCopy(){
    SpeechPhraseBO phraseBO = new SpeechPhraseBO();
    phraseBO.setPhraseid(phraseid);
    phraseBO.setPhraseText(phraseText);
    phraseBO.setStateEnum(state);
    phraseBO.setConfidence(confidence);
    phraseBO.setInteractionCount(interactionCount);
    phraseBO.setLastCountDate(lastCountDate);
    phraseBO.setEnabled(enabled);
    return phraseBO;
  }

  public SpeechPhraseBO clone() {
    SpeechPhraseBO clone = getCopy();
    clone.setToBeDeleted(isToBeDeleted());
    clone.setAssigned(isAssigned());
    if (isLocked()) {
      clone.lock();
      if (hasChanged()) {
        clone.markChanged(true);
      }
    }
    return clone;
  }
}