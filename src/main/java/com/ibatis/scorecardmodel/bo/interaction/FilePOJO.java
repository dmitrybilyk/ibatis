package com.ibatis.scorecardmodel.bo.interaction;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * <p>Description: POJO objekt pro cfiles</p>
 * <p>Date: 23.3.2005 15:24:50</p>
 *
 * @author Tomas Beranek
 */

public class FilePOJO {

  public enum Fields {
    CFILE_ID, CFILE_SGID, CFILE_CKTYPE, CFILE_CKVALUE, CFILE_CFSIZE, CFILE_CFPATH, CFILE_CFTYPE,
    CFILE_DIGEST, CFILE_ENC_KEY_ID, CFILE_START_DATE, CFILE_STOP_DATE
  }
  public static final String QUERY_PARAM_COUPLE_ID = "coupleId";
  public static final String QUERY_PARAM_SG_ID = "sgid";
  public static final String QUERY_PARAM_FILE_LENGTH = "fileLength";
  public static final String QUERY_PARAM_CHSUM_TYPE = "chsumType";
  public static final String QUERY_PARAM_CHSUM = "chsum";
  public static final String QUERY_PARAM_FILE_TYPE = "fileType";
  public static final String QUERY_PARAM_FILE_PATH = "filePath";
  public static final String QUERY_PARAM_ENCRYPT_KEY_ID = "encryptKeyId";
  public static final String QUERY_PARAM_DIGEST = "digest";
  public static final String QUERY_PARAM_START_DATE = "start_ts";
  public static final String QUERY_PARAM_STOP_DATE = "stop_ts";

  private int id =  0;
  private int callId = 0;
  private int coupleId = 0;
  private int streamGroupId = 0;
  private String path;
  private String type;
  private long checksum = 0;
  private int length = 0;
  private String checksumType;
  private String digest;
  private String encryptionKeyId;
  private Date startDate = null;
  private Date stopDate = null;
//  private List<VoiceTagBO> voiceTags = new ArrayList<VoiceTagBO>();

  public FilePOJO() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCallId() {
    return callId;
  }

  public void setCallId(int callId) {
    this.callId = callId;
  }

  public long getChecksum() {
    return checksum;
  }

  public void setChecksum(long checksum) {
    this.checksum = checksum;
  }

  public String getChecksumType() {
    return checksumType;
  }

  public void setChecksumType(String checksumType) {
    this.checksumType = checksumType;
  }

  public int getCoupleId() {
    return coupleId;
  }

  public void setCoupleId(int coupleId) {
    this.coupleId = coupleId;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getStreamGroupId() {
    return streamGroupId;
  }

  public void setStreamGroupId(int streamGroupId) {
    this.streamGroupId = streamGroupId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getDigest() {
    return digest;
  }


  public void setDigest(String digest) {
    this.digest = digest;
  }


  public String getEncryptionKeyId() {
    return encryptionKeyId;
  }


  public void setEncryptionKeyId(String encryptionKeyId) {
    this.encryptionKeyId = encryptionKeyId;
  }

  public String getName() {
    return "";
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("FilePOJO [id=").append(id).append(", callId=").append(callId).append(", coupleId=")
    .append(coupleId).append(", streamGroupId=").append(streamGroupId).append(", ");
    if (path != null) {
      builder.append("path=").append(path).append(", ");
    }
    if (type != null) {
      builder.append("type=").append(type).append(", ");
    }
    builder.append("checksum=").append(checksum).append(", length=").append(length).append(", ");
    if (checksumType != null) {
      builder.append("checksumType=").append(checksumType).append(", ");
    }
    if (digest != null) {
      builder.append("digest=").append(digest).append(", ");
    }
    if (encryptionKeyId != null) {
      builder.append("encryptionKeyId=").append(encryptionKeyId).append(", ");
    }
    if (startDate != null) {
      builder.append("startDate=").append(startDate).append(", ");
    }
    if (stopDate != null) {
      builder.append("stopDate=").append(stopDate);
    }
    builder.append("]");
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FilePOJO)) return false;

    FilePOJO filePOJO = (FilePOJO) o;

    if (callId != filePOJO.callId) return false;
    if (checksum != filePOJO.checksum) return false;
    if (coupleId != filePOJO.coupleId) return false;
    if (id != filePOJO.id) return false;
    if (length != filePOJO.length) return false;
    //if (streamGroupId != filePOJO.streamGroupId) return false; considered same if streamgroup is different
    if (!checksumType.equals(filePOJO.checksumType)) return false;
    if (digest != null ? !digest.equals(filePOJO.digest) : filePOJO.digest != null) return false;
    if (encryptionKeyId != null ? !encryptionKeyId.equals(filePOJO.encryptionKeyId) : filePOJO.encryptionKeyId != null)
      return false;
    if (!path.equals(filePOJO.path)) return false;
    if (startDate != null ? !startDate.equals(filePOJO.startDate) : filePOJO.startDate != null) return false;
    if (stopDate != null ? !stopDate.equals(filePOJO.stopDate) : filePOJO.stopDate != null) return false;
    if (!type.equals(filePOJO.type)) return false;
//    if (voiceTags != null ? !new HashSet<>(voiceTags).equals(new HashSet<>(filePOJO.voiceTags)) : filePOJO.voiceTags != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + callId;
    result = 31 * result + coupleId;
    //result = 31 * result + streamGroupId; considered same if streamgroup is different
    result = 31 * result + path.hashCode();
    result = 31 * result + type.hashCode();
    result = 31 * result + (int) (checksum ^ (checksum >>> 32));
    result = 31 * result + length;
    result = 31 * result + checksumType.hashCode();
    result = 31 * result + (digest != null ? digest.hashCode() : 0);
    result = 31 * result + (encryptionKeyId != null ? encryptionKeyId.hashCode() : 0);
    result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
    result = 31 * result + (stopDate != null ? stopDate.hashCode() : 0);
//    result = 31 * result + (voiceTags != null ? voiceTags.hashCode() : 0);
    return result;
  }




   @Override
   public Object clone() {
     FilePOJO file = new FilePOJO();
     file.setId(getId());
     file.setCoupleId(getCoupleId());
     file.setStreamGroupId(getStreamGroupId());
     file.setPath(getPath());
     file.setType(getType());
     file.setChecksum(getChecksum());
     file.setLength(getLength());
     file.setChecksumType(getChecksumType());
     file.setDigest(getDigest());
     file.setEncryptionKeyId(getEncryptionKeyId());
     file.setStartDate(getStartDate());
     file.setStopDate(getStopDate());
//     file.setVoiceTags(getVoiceTags());
     return file;
   }



//   public List<VoiceTagBO> getVoiceTags() {
//     return voiceTags;
//   }
//
//   public void setVoiceTags(List<VoiceTagBO> voiceTags) {
//     this.voiceTags = voiceTags;
//   }



}
