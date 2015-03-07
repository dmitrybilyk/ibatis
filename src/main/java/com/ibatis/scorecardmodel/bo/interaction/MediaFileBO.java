package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;

import javax.xml.bind.annotation.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 12.5.11
 * Time: 15:50
 */
@XmlRootElement(name = "mediaFile")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaFileBO extends BaseBean {

  public enum Fields {
    PATH("path"),
    TYPE("type"),
    CHECKSUM("checksum"),
    CHECKSUM_TYPE("checksumType"),
    DIGEST("digest"),
    ENCRYPTION_KEY_ID("encryptionKeyId");

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

  private Integer mediaFileId;
  private String path;
  private String type;
  private Long checksum;
  private String checksumType;
  private String digest;
  private String encryptionKeyId;
  @XmlElementWrapper(name = "voice_tags")
  @XmlElement(name = "voice_tag")
  private TrackableLinkedHashSet<VoiceTagBO> voicetags;


  public MediaFileBO() {
      voicetags = new TrackableLinkedHashSet<VoiceTagBO>();
  }
 
  @Override
  public Integer getId() {
    return mediaFileId;
  }

  @Override
  public void setId(Integer value) {
    setMediaFileId(value);
  }

  public Integer getMediaFileId() {
    return mediaFileId;
  }

  //used by ibatis
  public void setMediaFileId(Integer mediaFileId) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.mediaFileId = mediaFileId;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    markChanged(path, this.path);
    this.path = path;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    markChanged(type, this.type);
    this.type = type;
  }

  public Long getChecksum() {
    return checksum;
  }

  public void setChecksum(Long checksum) {
    markChanged(checksum, this.checksum);
    this.checksum = checksum;
  }

  public String getChecksumType() {
    return checksumType;
  }

  public void setChecksumType(String checksumType) {
    markChanged(checksumType, this.checksumType);
    this.checksumType = checksumType;
  }

  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    markChanged(digest, this.digest);
    this.digest = digest;
  }

  public String getEncryptionKeyId() {
    return encryptionKeyId;
  }

  public void setEncryptionKeyId(String encryptionKeyId) {
    markChanged(encryptionKeyId, this.encryptionKeyId);
    this.encryptionKeyId = encryptionKeyId;
  }


  @Override
  public int hashCode() {
    return ((getMediaFileId() == null) ? 0 : getMediaFileId().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof MediaFileBO))
      return false;
    MediaFileBO other = (MediaFileBO) obj;
    if (getMediaFileId() == null || other.getMediaFileId() == null)
      return false;
    return getMediaFileId().equals(other.getMediaFileId());
  }


  @Override
  public String toString() {
    return new StringBuilder().append("MediaFileBO{")
        .append("mediaFileId=").append(mediaFileId)
        .append(", path='").append(path).append('\'')
        .append(", type='").append(type).append('\'')
        .append(", checksum=").append(checksum)
        .append(", checksumType='").append(checksumType).append('\'')
        .append(", digest='").append(digest).append('\'')
        .append(", encryptionKeyId='").append(encryptionKeyId).append('\'')
//        .append("voicetags = ").append(voicetags).append('\'')
        .append('}').toString();
  }

    @Override
  public BaseBean newInstance() {
    return new MediaFileBO();
  }
    
   @Override
    public void lock() {
        super.lock();
//        voicetags.lock();
    }

    @Override
    public boolean hasAnythingChanged() {
        return super.hasAnythingChanged() ;
    }

   public Set<VoiceTagBO> getVoicetags() {
        return voicetags;
    }

    public boolean hasVoicetag(VoiceTagBO voiceTagBO) {
        return voicetags.contains(voiceTagBO);
    }

    public boolean addVoicetag(VoiceTagBO voiceTagBO) {
        return this.voicetags.add(voiceTagBO);
    }

    public boolean removeVoicetag(VoiceTagBO voiceTagBO) {
        return this.voicetags.remove(voiceTagBO);
    }

    public void addVoicetags(Collection<VoiceTagBO> voiceTagBO) {
        this.voicetags.addAll(voiceTagBO);
    }

    public void removeVoicetag(Set<VoiceTagBO> voiceTagBO) {
        this.voicetags.removeAll(voiceTagBO);
    }


      public void clearVoicetags() {
          this.voicetags.clear();
      }

}
