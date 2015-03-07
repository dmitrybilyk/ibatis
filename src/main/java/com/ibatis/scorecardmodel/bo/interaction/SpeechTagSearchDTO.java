package com.ibatis.scorecardmodel.bo.interaction;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: juan
 * Date: 7/11/12
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */

public class SpeechTagSearchDTO implements Serializable {
  private static final long serialVersionUID = 7126769572283077199L;
  private Integer tagid;
  private String alias;
  private ConfidenceRange confidenceRange;
//  private VoiceTagBO.VoiceTagChannel channel;
  private List<SpeechPhraseBO> phrases = new ArrayList<SpeechPhraseBO>();

  public List<SpeechPhraseBO> getPhrases() {
    return phrases;
  }

  public void setPhrases(List<SpeechPhraseBO> phrases) {
    this.phrases = phrases;
  }

  public void setPhrases(Set<SpeechPhraseBO> phrases) {
    this.phrases.addAll(phrases);
    }

  public ConfidenceRange getConfidenceRange() {
    return confidenceRange;
  }

  public void setConfidenceRange(ConfidenceRange confidenceRange) {
    this.confidenceRange = confidenceRange;
  }

  public String getAlias() {
    return alias;
  }

  public void generateAlias() {
    this.alias = String.valueOf(this.getTagid()) + this.getConfidenceRange().getMin() + this.getConfidenceRange().getMax();
  }

  public Integer getTagid() {
    return tagid;
  }

  public void setTagid(Integer tagid) {
    this.tagid = tagid;
  }

//  public VoiceTagBO.VoiceTagChannel getChannel() {
//    return channel;
//  }
//
//  public void setChannel(VoiceTagBO.VoiceTagChannel channel) {
//    this.channel = channel;
//  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SpeechTagSearchDTO)) {
      return false;
    }

    SpeechTagSearchDTO otherDto = (SpeechTagSearchDTO) o;
    return Objects.equal(this.tagid, otherDto.getTagid()) &&
            Objects.equal(this.confidenceRange.getMin(), otherDto.getConfidenceRange().getMin()) &&
            Objects.equal(this.confidenceRange.getMax(), otherDto.getConfidenceRange().getMax());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(tagid, confidenceRange.getMin(), this.getConfidenceRange().getMax());
  }

}
