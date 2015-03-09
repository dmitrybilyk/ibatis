package com.ibatis;

import org.springframework.test.context.ContextConfiguration;

/**
 * Radek Mensik, 15.6.12
 */

@ContextConfiguration(locations = {"classpath:itdb-postgres-callrec-context.xml", "classpath:itdb-postgres-sqlmap-context.xml"})
public class SpeechTagDaoPostgresITDB extends SpeechTagDaoITDBBase {

  @Override
  protected String getPathToDataSet() {
    return "/data/USERINFO.xml";
  }

  @Override
  protected void resetSpeechSequences() {
//    dbOperations.execute("ALTER SEQUENCE callrec.speech_phrases_seq RESTART 10000");
//    dbOperations.execute("ALTER SEQUENCE callrec.speech_tags_seq RESTART 10000");
  }
}
