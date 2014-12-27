package com.ibatis.scorecardmodel.bo.interaction;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Couple (also called Segment) is a single piece of continuous audio record,
 * usually with two channels (hence the name Couple).
 * This business object also contains various addition information.
 */
public class CouplePOJO {

  public static final String COUPLE_STATE_FINISHED = "FINISHED";

  private int id = 0;
  private int callId = 0;
  private Date startDate = null;
  private Date stopDate = null;
  private int length = 0;
  private int cFileTypes = 0;
  private int cFileCount = 0;
  private Date expired = null;
  private String callingIp = null;
  private String calledIp = null;
  private String callingNumber = null;
  private String originalCalledNumber = null;
  private String finalCalledNumber = null;
  private String callingPartyName = null;
  private String calledPartyName = null;
  private String coupleType = null;
  private String problemStatus = null;
  private String description = null;

  private String bMethod = null;
  private String bLocation = null;
  private List<FilePOJO> cFileList = new ArrayList<FilePOJO>();

  private SynchroStatus synchroFlag;
  private MixerStatus mixerFlag;
  private DeleteStatus deleteFlag;
  private RestoreStatus restoreFlag;
  private ArchiveStatus archiveFlag;
  private Set<ScorecardUseStatus> scorecardFlags = EnumSet.noneOf(ScorecardUseStatus.class);
  private String coupleState;
  private boolean randomSid;

  private Integer timeOfDay;
  private Integer dayOfWeek;

  public static final String NUMBER = "NUMBER";
  public static final String DATE = "DATE";
  private String callingAgent;
  private String calledAgent;
  private String callingDomain;
  private String calledDomain;
  private String originalDomain;
  private EnumCoupleDirection coupleDirection;
  private Date created;

  public enum Fields {
    COUPLE_ID(NUMBER), COUPLE_CALL_ID(NUMBER), COUPLE_START_TS(DATE), COUPLE_STOP_TS(DATE), COUPLE_LENGTH(NUMBER),
    COUPLE_CALLING_IP, COUPLE_CALLED_IP, COUPLE_CALLING_NR, COUPLE_ORIGINAL_CALLED_NR, COUPLE_FINAL_CALLED_NR,
    COUPLE_CALLING_PARTY_NAME, COUPLE_CALLED_PARTY_NAME, COUPLE_TYPE, COUPLE_PROBLEM_STATUS,
    COUPLE_DESCRIPTION, COUPLE_SYNCHRONIZED, COUPLE_MIXED, COUPLE_DELETED, COUPLE_RESTORED,
    COUPLE_ARCHIVED, COUPLE_SCORECARD_USE, COUPLE_B_METHOD, COUPLE_B_LOCATION, COUPLE_SID,
    COUPLE_CFTYPES(NUMBER), COUPLE_CFCNT(NUMBER), COUPLE_PROTECTED, COUPLE_STATE, COUPLE_START_TS_TIME(DATE), COUPLE_STOP_TS_TIME(DATE), RANDOM,
    COUPLE_DAY_OF_WEEK(NUMBER), COUPLE_TIME_OF_DAY(NUMBER),COUPLE_CALLING_AGENT,COUPLE_CALLED_AGENT;


    private boolean numeric = false;
    private boolean date = false;

    Fields(String type) {
      if (type.equals(NUMBER)) {
        numeric = true;
      } else if (type.equals(DATE)) {
        date = true;
      }

    }

    public boolean isDate() {
      return date;
    }

    Fields() {
      this.numeric = false;
    }

    public boolean isNumeric() {
      return numeric;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values()) {
        if (field.toString().equalsIgnoreCase(fieldName)) {
          return field;
        }
      }
      return null;
    }
  }


  public enum SynchroStatus {
    USED("S"), NOT_USED("N"), ONLY_EXTERNAL_DATA("E"), FAILED("F"), NULL(null);
    private String value;

    SynchroStatus(String value) {
      this.value = value;
    }

    public String getMark() {
      return value;
    }

    public String getOldFlag() {
      if (value.equals("S")) {
        return "s";
      } else if (value.equals("N") || value.equals("E")) {
        return "S";
      }
      return null;
    }

    public static SynchroStatus getField(String fieldName) {
      for (SynchroStatus field : SynchroStatus.values()) {
        if (field.getMark().equals(fieldName)) {
          return field;
        }
      }
      return null;
    }
  }

  public enum MixerStatus {
    MIXED("M"), FAILED("F");
    private String value;

    MixerStatus(String value) {
      this.value = value;
    }

    public String getMark() {
      return value;
    }

    public static MixerStatus getField(String fieldName) {
      for (MixerStatus field : MixerStatus.values()) {
        if (field.getMark().equals(fieldName)) {
          return field;
        }
      }
      return null;
    }
    public String getOldFlag() {
       if (value.equals("F")) {
        return "m";
      } else if (value.equals("M")) {
        return "M";
      }
      return null;
    }
  }

  public enum DeleteStatus {
    DELETED("D"), FAILED("F");
    private String value;

    DeleteStatus(String value) {
      this.value = value;
    }

    public String getMark() {
      return value;
    }

    public static DeleteStatus getField(String fieldName) {
      for (DeleteStatus field : DeleteStatus.values()) {
        if (field.getMark().equals(fieldName)) {
          return field;
        }
      }
      return null;
    }

     public String getOldFlag() {
       if (value.equals("D")) {
        return "D";
      }
      return null;
    }
  }

  public enum RestoreStatus {
    RESTORED("R"), FAILED("F"), RESTORE_REQUEST("Q");
    private String value;

    RestoreStatus(String value) {
      this.value = value;
    }

    public String getMark() {
      return value;
    }

    public static RestoreStatus getField(String fieldName) {
      for (RestoreStatus field : RestoreStatus.values()) {
        if (field.getMark().equals(fieldName)) {
          return field;
        }
      }
      return null;
    }

     public String getOldFlag() {
       if (value.equals("R")) {
        return "R";
      } else if(value.equals("Q")){
         return "Q";
       }
      return null;
    }
  }

  public enum ArchiveStatus {
    ARCHIVED("A"), FAILED("F");
    private String value;

    ArchiveStatus(String value) {
      this.value = value;
    }

    public String getMark() {
      return value;
    }

    public static ArchiveStatus getField(String fieldName) {
      for (ArchiveStatus field : ArchiveStatus.values()) {
        if (field.getMark().equals(fieldName)) {
          return field;
        }
      }
      return null;
    }
     public String getOldFlag() {
       if (value.equals("A")) {
        return "A";
      } else if (value.equals("F")) {
        return "a";
      }
      return null;
    }
  }


  //List objektu ExternalDataPOJO
  private List<ExternalDataPOJO> externalDataList = null;


  // Cenda 23.10.2006 CHANGES: synchronization ID
  private String sid = null;
  private String flag = null;
  //key for external data

  //key for external data -used whe columns are displayed
  private String extdataKey = null;
  //value for externaldata - used when columns are displayed
  private String extdataValue = null;

  private boolean protectedCouple = false;

  public CouplePOJO() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCalledIp() {
    return calledIp;
  }

  public void setCalledIp(String calledIp) {
    this.calledIp = calledIp;
  }

  public String getCalledPartyName() {
    return calledPartyName;
  }

  public void setCalledPartyName(String calledPartyName) {
    this.calledPartyName = calledPartyName;
  }

  public int getCallId() {
    return callId;
  }

  public void setCallId(int callId) {
    this.callId = callId;
  }

  public String getCallingIp() {
    return callingIp;
  }

  public void setCallingIp(String callingIp) {
    this.callingIp = callingIp;
  }

  public String getCallingNumber() {
    return callingNumber;
  }

  public void setCallingNumber(String callingNumber) {
    this.callingNumber = callingNumber;
  }

  public String getCallingPartyName() {
    return callingPartyName;
  }

  public void setCallingPartyName(String callingPartyName) {
    this.callingPartyName = callingPartyName;
  }

  public String getCoupleType() {
    return coupleType;
  }

  public void setCoupleType(String coupleType) {
    this.coupleType = coupleType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFinalCalledNumber() {
    return finalCalledNumber;
  }

  public void setFinalCalledNumber(String finalCalledNumber) {
    this.finalCalledNumber = finalCalledNumber;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getOriginalCalledNumber() {
    return originalCalledNumber;
  }

  public void setOriginalCalledNumber(String originalCalledNumber) {
    this.originalCalledNumber = originalCalledNumber;
  }

  public String getProblemStatus() {
    return problemStatus;
  }

  public void setProblemStatus(String problemStatus) {
    this.problemStatus = problemStatus;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setStopDate(Date stopDate) {
    this.stopDate = stopDate;
  }

  public Date getStopDate() {
    return stopDate;
  }

  /**
   * Returns connected all flags.
   *
   * @return empty string if no bflag
   */
  @NotNull
  public String getbFlag() {
    StringBuilder sb = new StringBuilder();
    sb.append(synchroFlag == null ? "" : synchroFlag.getOldFlag()).
            append(getScorecardFlagsBFlag()).
            append(deleteFlag == null ? "" : deleteFlag.getOldFlag()).
            append(restoreFlag == null ? "" : restoreFlag.getOldFlag()).
            append(archiveFlag == null ? "" : archiveFlag.getOldFlag()).
            append(mixerFlag == null ? "" : mixerFlag.getOldFlag());
    if (sb.length() < 1) {
      return "";
    }
    return sb.toString();
  }

  private String getScorecardFlagsBFlag() {
    String scorecardFlag = "";
    Set<ScorecardUseStatus> scorecardFlags = getScorecardFlagsEnum();
    for (ScorecardUseStatus flag : scorecardFlags) {
      scorecardFlag += StringUtils.defaultString(flag.getOldFlag()); // most of the time we're appending empty string
    }
    return scorecardFlag;
  }
  //called from audio.jsp!!!!

  public int getLengthMinute() {
    return this.length / 60;
  }

  public String getbLocation() {
    return bLocation;
  }

  public void setbLocation(String bLocation) {
    this.bLocation = bLocation;
  }

  public String getbMethod() {
    if (StringUtils.isNotEmpty(bMethod)) {
    	return bMethod;
    } else {
    	bMethod = null;
    	return bMethod;
    }
  }

  public void setbMethod(String bMethod) {
    this.bMethod = bMethod;
  }

  public int getcFileTypes() {
    return cFileTypes;
  }

  public void setcFileTypes(int cFileTypes) {
    this.cFileTypes = cFileTypes;
  }

  public int getcFileCount() {
    return cFileCount;
  }

  public void setcFileCount(int cFileCount) {
    this.cFileCount = cFileCount;
  }

  public Date getExpired() {
    return expired;
  }

  public void setExpired(Date expired) {
    this.expired = expired;
  }

  public List<ExternalDataPOJO> getExternalDataList() {
    return externalDataList == null ? new ArrayList<ExternalDataPOJO>() : externalDataList;
  }

  public void setExternalDataList(List<ExternalDataPOJO> externalDataList) {
    this.externalDataList = externalDataList;
  }

  public List<FilePOJO> getCFileList() {
    return cFileList;
  }

  public void setCFileList(final List<FilePOJO> cFileList) {
    this.cFileList = cFileList;
  }


  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }


  @SuppressWarnings({"unused"})
  public String getExtdataKey() {
    return extdataKey;
  }


  @SuppressWarnings({"unused"})
  public String getExtdataValue() {
    return extdataValue;
  }


  public boolean isProtectedCouple() {
    return protectedCouple;
  }

  public void setProtectedCouple(boolean protectedCouple) {
    this.protectedCouple = protectedCouple;
  }

  public ArchiveStatus getArchiveFlagEnum() {
    return archiveFlag;
  }

  public void setArchiveFlagEnum(ArchiveStatus archiveFlag) {
    this.archiveFlag = archiveFlag;
  }

  public DeleteStatus getDeleteFlagEnum() {
    return deleteFlag;
  }

  public void setDeleteFlagEnum(DeleteStatus deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  @NotNull
  public Set<ScorecardUseStatus> getScorecardFlagsEnum() {
    return scorecardFlags;
  }

  /**
   * Resets the flags to the specified flags
   * @param scorecardFlags
   */
  public void setScorecardFlagsEnum(@NotNull Collection<ScorecardUseStatus> scorecardFlags) {
    if (scorecardFlags.isEmpty()) {
      this.scorecardFlags = EnumSet.noneOf(ScorecardUseStatus.class);
    } else {
      this.scorecardFlags = EnumSet.copyOf(scorecardFlags); // defensive copy
    }
  }

  /**
   * Add a flag to {@link #getScorecardFlagsEnum()}
   *
   * @param scorecardFlag the flag to add
   * @return true if this was not already set
   */
  public boolean addScorecardFlag(ScorecardUseStatus scorecardFlag) {
    return this.scorecardFlags.add(scorecardFlag);
  }

  /**
   * Remove a flag from {@link #getScorecardFlagsEnum()}
   *
   * @param scorecardFlag the flag to remove
   * @return true if this flag was set
   */
  public boolean removeScorecardFlag(ScorecardUseStatus scorecardFlag) {
    return this.scorecardFlags.remove(scorecardFlag);
  }

  public MixerStatus getMixerFlagEnum() {
    return mixerFlag;
  }

  public void setMixerFlagEnum(MixerStatus mixerFlag) {
    this.mixerFlag = mixerFlag;
  }

  public RestoreStatus getRestoreFlagEnum() {
    return restoreFlag;
  }

  public void setRestoreFlagEnum(RestoreStatus restoreFlag) {
    this.restoreFlag = restoreFlag;
  }

  public SynchroStatus getSynchroFlagEnum() {
    return synchroFlag;
  }

  public void setSynchroFlagEnum(SynchroStatus synchroFlag) {
    this.synchroFlag = synchroFlag;
  }

  public String getArchiveFlag() {
    return archiveFlag == null ? null : archiveFlag.getMark();
  }

  private void setArchiveFlag(String archiveFlag) {
    if (archiveFlag != null) {
      this.archiveFlag = ArchiveStatus.getField(archiveFlag);
    }
  }

  public String getDeleteFlag() {
    return deleteFlag == null ? null : deleteFlag.getMark();
  }

  private void setDeleteFlag(String deleteFlag) {
    if (deleteFlag != null) {
      this.deleteFlag = DeleteStatus.getField(deleteFlag);
    }
  }

  /**
   * String-based alternative to {@link #getScorecardFlagsEnum()}
   * that returns a string of concatenated flag marks, e.g. "EST".
   * The order of these flags should match the order of the enum constants thanks to {@link java.util.EnumSet} used internally.
   *
   * @return a string of flag marks concatenated together, not separated by anything; or null if no flags are set
   */
  @Nullable
  public String getScorecardFlags() {
    if (scorecardFlags.isEmpty()) {
      return null;
    }
    StringBuilder scorecardFlagsString = new StringBuilder();
    for (ScorecardUseStatus flag : scorecardFlags) {
      scorecardFlagsString.append(flag.getMark());
    }
    return scorecardFlagsString.toString();
  }

  /**
   * String-based alternative to {@link #setScorecardFlagsEnum(java.util.Collection)}
   * that allows to set the flags via a string of concatenated flag marks, e.g. "EST".
   * The order of the provided flag marks does not matter and repeated marks will be ignored.
   *
   * @param scorecardFlagsString a string of flag marks concatenated together, not separated by anything; can be null
   */
  public void setScorecardFlags(@Nullable String scorecardFlagsString) {
    this.scorecardFlags = EnumSet.noneOf(ScorecardUseStatus.class);
    if (scorecardFlagsString != null) {
      for (ScorecardUseStatus flag : ScorecardUseStatus.values()) {
        if (scorecardFlagsString.contains(flag.getMark())) {
          this.scorecardFlags.add(flag);
        }
      }
    }
  }

  public String getMixerFlag() {
    return mixerFlag == null ? null : mixerFlag.getMark();
  }

  private void setMixerFlag(String mixerFlag) {
    if (mixerFlag != null) {
      this.mixerFlag = MixerStatus.getField(mixerFlag);
    }
  }

  public String getRestoreFlag() {
    return restoreFlag == null ? null : restoreFlag.getMark();
  }

  private void setRestoreFlag(String restoreFlag) {
    if (restoreFlag != null) {
      this.restoreFlag = RestoreStatus.getField(restoreFlag);
    }
  }

  public String getSynchroFlag() {
    return synchroFlag == null ? null : synchroFlag.getMark();
  }

  private void setSynchroFlag(String synchroFlag) {
    if (synchroFlag != null) {
      this.synchroFlag = SynchroStatus.getField(synchroFlag);
    } else {
    	this.synchroFlag = null;
    }
  }

  public String getCoupleState() {
    return coupleState;
  }

  public void setCoupleState(String coupleState) {
    this.coupleState = coupleState;
  }

  public Integer getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(Integer dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public Integer getTimeOfDay() {
    return timeOfDay;
  }

  public void setTimeOfDay(Integer timeOfDay) {
    this.timeOfDay = timeOfDay;
  }


  public String getCallingAgent() {
    return callingAgent;
  }

  public void setCallingAgent(String callingAgent) {
    this.callingAgent = callingAgent;
  }


  public String getCalledAgent() {
    return calledAgent;
  }

  public void setCalledAgent(String calledAgent) {
    this.calledAgent = calledAgent;
  }


  public String getCallingDomain() {
    return callingDomain;
  }

  public void setCallingDomain( String callingDomain) {
    this.callingDomain = callingDomain;
  }


  public String getCalledDomain() {
    return calledDomain;
  }

  public void setCalledDomain( String calledDomain) {
    this.calledDomain = calledDomain;
  }


  public EnumCoupleDirection getCoupleDirection() {
    return coupleDirection;
  }

  public void setCoupleDirection(EnumCoupleDirection coupleDirection) {
    this.coupleDirection = coupleDirection;
  }

  public String getOriginalDomain() {
    return originalDomain;
  }

  public void setOriginalDomain(String originalDomain) {
    this.originalDomain = originalDomain;
  }

  public boolean getIsRandomSid() {
    return randomSid;
  }

  public void setIsRandomSid() {
    randomSid = true;
  }

  public void setCreatedDate(final Date created) {
    this.created = created;
  }

  public Date getCreatedDate() {
    return created;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CouplePOJO)) return false;

    CouplePOJO that = (CouplePOJO) o;

    if (cFileCount != that.cFileCount) return false;
    if (cFileTypes != that.cFileTypes) return false;
    if (callId != that.callId) return false;
    if (id != that.id) return false;
    if (length != that.length) return false;
    if (protectedCouple != that.protectedCouple) return false;
    if (archiveFlag != that.archiveFlag) return false;
    if (bLocation != null ? !bLocation.equals(that.bLocation) : that.bLocation != null) return false;
    if (bMethod != null ? !bMethod.equals(that.bMethod) : that.bMethod != null) return false;
    if (calledIp != null ? !calledIp.equals(that.calledIp) : that.calledIp != null) return false;
    if (calledPartyName != null ? !calledPartyName.equals(that.calledPartyName) : that.calledPartyName != null)
      return false;
    if (callingIp != null ? !callingIp.equals(that.callingIp) : that.callingIp != null) return false;
    if (callingNumber != null ? !callingNumber.equals(that.callingNumber) : that.callingNumber != null) return false;
    if (callingPartyName != null ? !callingPartyName.equals(that.callingPartyName) : that.callingPartyName != null)
      return false;
    if (coupleState != null ? !coupleState.equals(that.coupleState) : that.coupleState != null) return false;
    if (coupleType != null ? !coupleType.equals(that.coupleType) : that.coupleType != null) return false;
    if (deleteFlag != that.deleteFlag) return false;
    if (description != null ? !description.equals(that.description) : that.description != null) return false;
    if (!scorecardFlags.equals(that.scorecardFlags)) return false;
    if (expired != null ? !expired.equals(that.expired) : that.expired != null) return false;
    if (extdataKey != null ? !extdataKey.equals(that.extdataKey) : that.extdataKey != null) return false;
    if (extdataValue != null ? !extdataValue.equals(that.extdataValue) : that.extdataValue != null) return false;
    if (externalDataList != null ? !new HashSet<ExternalDataPOJO>(externalDataList).equals(new HashSet<ExternalDataPOJO>(that.externalDataList)) : that.externalDataList != null)
      return false;
    if (finalCalledNumber != null ? !finalCalledNumber.equals(that.finalCalledNumber) : that.finalCalledNumber != null)
      return false;
    if (flag != null ? !flag.equals(that.flag) : that.flag != null) return false;
    if (mixerFlag != that.mixerFlag) return false;
    if (originalCalledNumber != null ? !originalCalledNumber.equals(that.originalCalledNumber) : that.originalCalledNumber != null)
      return false;
    if (problemStatus != null ? !problemStatus.equals(that.problemStatus) : that.problemStatus != null) return false;
    if (restoreFlag != that.restoreFlag) return false;
    if (sid != null ? !sid.equals(that.sid) : that.sid != null) return false;
    if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
    if (stopDate != null ? !stopDate.equals(that.stopDate) : that.stopDate != null) return false;
    if (cFileList != null ? !new HashSet<FilePOJO>(cFileList).equals(new HashSet<FilePOJO>(that.cFileList)) : that.cFileList != null) return false;
    if (synchroFlag != that.synchroFlag) return false;
    if (timeOfDay != null ? !timeOfDay.equals(that.timeOfDay) : that.timeOfDay != null) return false;
    if (dayOfWeek != null ? !dayOfWeek.equals(that.dayOfWeek) : that.dayOfWeek != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
    result = 31 * result + (stopDate != null ? stopDate.hashCode() : 0);
    result = 31 * result + (originalCalledNumber != null ? originalCalledNumber.hashCode() : 0);
    result = 31 * result + (finalCalledNumber != null ? finalCalledNumber.hashCode() : 0);
    result = 31 * result + (callingPartyName != null ? callingPartyName.hashCode() : 0);
    result = 31 * result + (calledPartyName != null ? calledPartyName.hashCode() : 0);
    result = 31 * result + (coupleType != null ? coupleType.hashCode() : 0);
    result = 31 * result + (problemStatus != null ? problemStatus.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (timeOfDay != null ? timeOfDay.hashCode() : 0);
    result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
    return result;
  }
}
