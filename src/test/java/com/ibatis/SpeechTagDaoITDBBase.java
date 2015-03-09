package com.ibatis;

import com.ibatis.dao.PersonDaoIbatis;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Radek Mensik, 15.6.12
 */
public abstract class SpeechTagDaoITDBBase extends ITDBBase {

  public static final String PHRASE_TEXT = "I love you";
  public static final String FS_ADMISSIONS_CUSTOMER_SERVICE = "FS ADMISSIONS CUSTOMER SERVICE";
  public static final String SPEECH_TAG_NAME = FS_ADMISSIONS_CUSTOMER_SERVICE;
  public static final String HANDSHAKE_PNG = "handshake.png";
  public static final String TRANSFERING_CREDIT = "TRANSFERING CREDIT";
  public static final String FS_ADMISSIONS_COMPLIANCE = "FS Admissions Compliance";
  public static final String PHRASE_TO_BE_DELETED = "phrase to be deleted";
  public static final String MY_ICO = "my.ico";
  public static final String MY_TAG = "My tag";
  public static final String NAZDAR = "Nazdar";
  static final int CFILEID_WITH_VOICE_TAG = 95;
  static final int CFILEID = 96;
  private PersonDaoIbatis dao;

  @BeforeMethod
  protected void getDao() {
    dao = new PersonDaoIbatis(sqlMapClient);
  }

  @Test public void testTagsCount() throws Exception {
    Integer interactionCount = dao.getAllPersonsCount();
    System.out.println("testdima");
    assertEquals(interactionCount.intValue(), 1, "database should contain");
  }

}
