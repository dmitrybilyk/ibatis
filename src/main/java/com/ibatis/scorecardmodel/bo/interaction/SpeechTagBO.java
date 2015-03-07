package com.ibatis.scorecardmodel.bo.interaction;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Jamel Gasmi
 * Date: 10/28/11
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpeechTagBO extends BaseBean {
    private static final long serialVersionUID = -1L;

    public enum Fields {
        SPEECH_TAG_ID("tagid"), ICON("tagIcon"), NAME("tagName"), SEARCH("searchid"), STATE("state");
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

    private Integer tagid;
    private String tagName;
    private Integer searchid = 0;
    private SpeechState state;
    private String tagIcon;
    private TrackableLinkedHashSet<SpeechPhraseBO> phrases;

    public enum SpeechState {
        READY("READY"), PREPARING("INDEXING"), UPDATING("UPDATING");

        public String state;

        SpeechState(String state) {
            this.state = state;
        }

        String getState() {
            return state;
        }
    }

    public SpeechTagBO() {
      this.phrases = new TrackableLinkedHashSet<SpeechPhraseBO>();
    }

    public SpeechTagBO(SpeechTagBO pattern) {
      this();
      tagid = pattern.getTagid();
      tagName = pattern.getTagName();
      searchid = pattern.getSearchid();
      state = pattern.getStateEnum();
      tagIcon = pattern.getTagIcon();
      phrases.cloneAll((TrackableLinkedHashSet<SpeechPhraseBO>)pattern.getPhrases());
      setToBeDeleted(pattern.isToBeDeleted());
      setAssigned(pattern.isAssigned());
      if (pattern.isLocked()) {
        lock();
        if (pattern.hasChanged()) {
          markChanged(true);
        }
      }
    }

    public SpeechTagBO(String name, String icon, SpeechState state) {
        this();
        tagName = name;
        this.state = state;
        tagIcon = icon;
    }

    @Override
    public void lock() {
        super.lock();
        phrases.lock();
    }

    @Override
    public boolean hasAnythingChanged() {
        return super.hasAnythingChanged() || phrases.hasAnythingChanged();
    }

    public Set<SpeechPhraseBO> getPhrases() {
        return phrases;
    }

    public boolean hasPhrase(SpeechPhraseBO phrase) {
        return phrases.contains(phrase);
    }

    public boolean addPhrase(SpeechPhraseBO phrase) {
        return this.phrases.add(phrase);
    }

    public boolean removePhrase(SpeechPhraseBO phrase) {
        return this.phrases.remove(phrase);
    }

  public boolean removePhraseByText(SpeechPhraseBO phrase) {
    SpeechPhraseBO toRemove = null;
    for (SpeechPhraseBO speechPhraseBO : phrases) {
      if (speechPhraseBO.getPhraseText().equals(phrase.getPhraseText())) {
        toRemove = speechPhraseBO;
        break;
      }
    }
    return this.phrases.remove(toRemove);
  }

    public void addPhrases(Set<SpeechPhraseBO> phrases) {
        this.phrases.addAll(phrases);
    }

    public void removePhrases(Set<SpeechPhraseBO> phrases) {
        this.phrases.removeAll(phrases);
    }

    // userd by ibatis
    @SuppressWarnings("unused")
    private void setPhraseList(List<SpeechPhraseBO> phrases) {
        if (phrases == null) {
            return;
        }

        for (SpeechPhraseBO phraseBO : phrases) {
            if (!phraseBO.getPhraseText().equals("")) {
                addPhrase(phraseBO);
            }
        }
    }

    public List <Integer> getPhrasesIds(){
      ArrayList<Integer> ids = new ArrayList<Integer>();
      for (SpeechPhraseBO phrase : phrases) {
        ids.add(phrase.getPhraseid());
      }
      return ids;
    }

    // userd by ibatis
    @SuppressWarnings("unused")
    private Set<SpeechPhraseBO> getPhraseList() {
        return new HashSet<SpeechPhraseBO>() {
            private static final long serialVersionUID = -1L;

            public boolean add(SpeechPhraseBO e) {
                if (!e.getPhraseText().equals("")) {
                    return addPhrase(e);
                } else {
                    return false;
                }
            }
        };
    }

    /**
     * ********************
     * Getters and Setters *
     * *********************
     */

    @Override
    public Integer getId() {
        return getTagid();
    }

    @Override
    public void setId(Integer value) {
        setTagid(value);
    }

    public Integer getTagid() {
        return tagid;
    }

    //used by ibatis
    public void setTagid(Integer id) {
        if (isLocked()) {
          throw new UnsupportedOperationException("You cannot set a primary key");
        }
        this.tagid = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagText) {
        String local = tagText == null ? "" : tagText.trim();
        markChanged(this.tagName, local);
        this.tagName = local;
    }

    public String getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(String tagIcon) {
        String local = tagIcon == null ? "" : tagIcon.trim();
        markChanged(this.tagIcon, local);
        this.tagIcon = local;
    }

    public Integer getSearchid() {
        return searchid;
    }

    public void setSearchid(Integer searchid) {
        this.searchid = searchid;
    }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getState() {
    return state == null ? null : state.getState();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setState(String state) {
    setStateEnum(SpeechState.valueOf(state));
  }

    public SpeechState getStateEnum() {
        return state;
    }

    public void setStateEnum(SpeechState state) {
        markChanged(this.state, state);
        this.state = state;
    }

  @Override
    public int hashCode() {
        return ((getTagid() == null) ? 0 : getTagid().hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
          return true;
        }
        if (!(obj instanceof SpeechTagBO)) {
          return false;
        }
        SpeechTagBO other = (SpeechTagBO) obj;
        if (getTagid() == null || other.getTagid() == null) {
          return false;
        }
        return getTagid().equals(other.getTagid());
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation
     *         of this object.
     */
    public String toString() {
        final String TAB = "\n    ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("\nSpeechTagBO ( \n").append(TAB)
                .append("tagid = ").append(this.tagid).append(TAB)
                .append("tagText = ").append(this.tagName).append(TAB)
                .append("state = ").append(this.state).append(TAB)
                .append("tagIcon = ").append(this.tagIcon).append(TAB)
                .append("searchid = ").append(this.searchid).append(TAB)
                .append("phrases = ").append(this.phrases).append(TAB)
                .append(" )\n");

        return retValue.toString();
    }

    @Override
    public BaseBean newInstance() {
        return new SpeechTagBO();
    }

    @Override
    public List<SearchOrder> defaultSortOrder() {
        List<SearchOrder> ordering = new ArrayList<SearchOrder>();
        ordering.add(new SearchOrder(Fields.NAME, SearchOrder.Direction.ASC));
        return ordering;
    }

  /** returns same values, but objects are not locked
   *
   * @return
   */
  public SpeechTagBO getCopy(){
    SpeechTagBO copy = new SpeechTagBO();
    copy.setTagid(tagid);
    copy.setTagName(tagName);
    copy.setSearchid(searchid);
    copy.setStateEnum(state);
    copy.setTagIcon(tagIcon);
    ArrayList<SpeechPhraseBO> phrasesCopy = new ArrayList<SpeechPhraseBO>();
    for (SpeechPhraseBO phrase : phrases) {
      phrasesCopy.add(phrase.getCopy());
    }
    copy.setPhraseList(phrasesCopy);
    return copy;
  }

}
