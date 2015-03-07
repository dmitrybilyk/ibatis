package com.ibatis.scorecardmodel.bo.interaction;

/**
 * <p>Description: POJO objekt popisujici ulozeny filtr</p>
 * <p>Date: 23.3.2005 17:09:55</p>
 *
 * @author Tomas Beranek
 */

public class FilterPOJO {
  private int id = 0;
  private int userId = 0;
  private String name = null;
  private boolean publicFilter = false;
  private int pageLen = 0;
  private boolean random = false;

  public FilterPOJO() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isPublic() {
    return publicFilter;
  }

  public void setPublic(boolean aPublic) {
    publicFilter = aPublic;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPageLen() {
    return pageLen;
  }

  public void setPageLen(int pageLen) {
    this.pageLen = pageLen;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public boolean isRandom() {
    return random;
  }

  public void setRandom(boolean random) {
    this.random = random;
  }

}
