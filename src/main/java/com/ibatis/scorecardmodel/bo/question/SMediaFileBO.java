package com.ibatis.scorecardmodel.bo.question;


import com.ibatis.scorecardmodel.BaseBean;

import javax.persistence.*;

/**
 * Media file used by ScoreCARD for:
 * <ul>
 * <li>IVR Survey audio questions</li>
 * <li>IVR Survey audio user comments</li>
 * </ul>
 */
@Entity
@Table(name = "smediafiles")
public class SMediaFileBO extends BaseBean {
  private static final long serialVersionUID = -1462281048829016406L;

  public enum Fields {
    TYPE("type"),
    PATH("path"),
    CONTENT_TYPE("contentType");

    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values()) {
        if (field.getFieldName().equals(fieldName)) {
          return field;
        }
      }
      return null;
    }
  }

  @Id
  @GeneratedValue
  private Integer smediafileid;
  @Transient
  private String filename;
  @Column(name = "mftype")
  private String type;
  @Column(name = "path")
  private String path;
  @Column(name = "content_type")
  private String contentType;
  @Transient
  private boolean temporary = true;

  @Override
  public Integer getId() {
    return getSmediafileid();
  }

  @Override
  public void setId(Integer value) {
    setSmediafileid(value);
  }

  public Integer getSmediafileid() {
    return smediafileid;
  }

  public void setSmediafileid(Integer id) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.smediafileid = id;
    temporary &= (id == null);
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    markChanged(type, this.type);
    this.type = type;
  }

  /**
   * Relative path for {@link SMediaFileContentDao} to use and UI to show.
   * This relative path will contain {@code /} as separators, not system-specific separators.
   * It's up to DAO to handle any separator conversion.
   */
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    markChanged(path, this.path);
    this.path = path;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    markChanged(contentType, this.contentType);
    this.contentType = contentType;
  }

  public String getFilename() {
    return filename != null ? filename
            : path == null ? null
            : path.substring(path.lastIndexOf('/') + 1);
  }

  /**
   * Whether this is a temp media file. Temp media files are not stored in the final location.
   * Instead, their contents must be retrieved directly by reading a file at {@link #getPath()}.
   *
   * @return true if this is a temp media file; false otherwise
   */
  public boolean isTemporary() {
    return temporary;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    return smediafileid != null // cannot be certain they are equal when one ID is null
            && other instanceof SMediaFileBO
            && smediafileid.equals(((SMediaFileBO) other).getId());
  }

  @Override
  public String toString() {
    return "SMediaFileBO{" + smediafileid + ": " + type + "(" + contentType + ") @ \"" + path + "\"}";
  }

  @Override
  public SMediaFileBO newInstance() {
    return new SMediaFileBO();
  }
}
