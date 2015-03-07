package com.ibatis.scorecardmodel.bo;

/**
 * Interface for beans that need localization.
 * Each instance can or can not be localized, and if it can be, it has to generate its unique localization key.
 * The localization keys must be deterministic, so they can be put into property files.
 *
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 22.3.2010
 * Time: 14:01:37
 */
public interface Localizable {

  /**
   * Is this instance localized?
   * @return <code>true</code> if this instance is localized, <code>false</code> otherwise
   */
  public boolean isLocalized();

  /**
   * Generates a localization key for this object instance.
   * @return localization key for this instance
   */
  public String getLocalizationKey();
}
