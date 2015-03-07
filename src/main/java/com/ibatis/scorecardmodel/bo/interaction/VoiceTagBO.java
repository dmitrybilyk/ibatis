package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.BaseBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "voiceTag")
@XmlAccessorType(XmlAccessType.FIELD)
public class VoiceTagBO extends BaseBean {

  public enum Fields {

    VOICE_TAG_ID("voicetagid"), SPEECH_PHRASE("phrase"), FROM_TIME("fromtime"), TO_TIME("totime"),
    CONFIDENCE("confidence"), CHANNEL("channel");
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

  private Integer voicetagid;
  private Integer cfileId;
  private Double confidence;
  private Integer fromtime;
  private Integer totime;
  private String channel;
  private SpeechPhraseBO phrase;
  private Integer phraseId;

  public enum VoiceTagChannel {
    L("L"), R("R");
    public String channel;

    VoiceTagChannel(String channel) {
      this.channel = channel;
    }

    String getChannel() {
      return channel;
    }
  }

  public Double getConfidence() {
    return confidence;
  }

  public void setConfidence(Double confidence) {
    markChanged(this.confidence, confidence);
    this.confidence = confidence;

  }

  public Integer getFromtime() {

    return fromtime;
  }

  public void setFromtime(Integer fromtime) {
    markChanged(this.fromtime, fromtime);
    this.fromtime = fromtime;
  }

  public Integer getTotime() {
    return totime;
  }

  public void setTotime(Integer totime) {
    markChanged(this.totime, totime);
    this.totime = totime;
  }

  public Integer getVoicetagid() {
    return voicetagid;
  }

  public void setVoicetagid(Integer voicetagid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }

    this.voicetagid = voicetagid;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    markChanged(this.channel, channel);
    this.channel = channel;
  }

  public SpeechPhraseBO getPhrase() {
    return phrase;
  }

  public void setPhrase(SpeechPhraseBO phrase) {
    this.phrase = phrase;
    if (phrase != null) {
      phraseId = phrase.getId();
    }
  }


  public Integer getPhraseId() {
    if (this.phrase == null) {
      return phraseId;
    }
    return this.phrase.getPhraseid();
  }

  public void setPhraseId(Integer phraseId) {
    this.phraseId = phraseId;
  }

  /* used by ibatis */

  public Integer getCfileId() {
    return cfileId;
  }

  public void setCfileId(Integer cfileId) {
    this.cfileId = cfileId;
  }

  @Override
  public void setId(Integer value) {
    setVoicetagid(value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof VoiceTagBO)) {
      return false;
    }
    VoiceTagBO other = (VoiceTagBO) obj;
    return getVoicetagid().equals(other.getVoicetagid());
  }

  @Override
  public int hashCode() {
    return ((getVoicetagid() == null) ? 0 : getVoicetagid().hashCode());
  }



  public boolean equalsAllParamsWithoutId(Object o) {
    if (this == o) return true;
    if (!(o instanceof VoiceTagBO)) return false;
    if (!super.equals(o)) return false;

    VoiceTagBO that = (VoiceTagBO) o;

    if (cfileId != null ? !cfileId.equals(that.cfileId) : that.cfileId != null) return false;
    if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
    if (confidence != null ? !confidence.equals(that.confidence) : that.confidence != null) return false;
    if (fromtime != null ? !fromtime.equals(that.fromtime) : that.fromtime != null) return false;
    if (phrase != null ? !phrase.equals(that.phrase) : that.phrase != null) return false;
    if (phraseId != null ? !phraseId.equals(that.phraseId) : that.phraseId != null) return false;
    if (totime != null ? !totime.equals(that.totime) : that.totime != null) return false;

    return true;
  }

  @Override
  public String toString() {
    final String TAB = "\n    ";
    StringBuilder retValue = new StringBuilder();

    retValue.append("\nVoiceTagBO ( \n").append(TAB)
            .append("voicetagid = ").append(this.voicetagid).append(TAB)
            .append("confidence = ").append(this.confidence).append(TAB)
            .append("fromtime = ").append(this.fromtime).append(TAB)
            .append("totime = ").append(this.totime).append(TAB)
            .append("channel = ").append(this.channel).append(TAB)
            .append(" )\n");

    return retValue.toString();
  }
}
