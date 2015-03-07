package rest.api;

import javax.ws.rs.core.MediaType;

public final class ApiConstants {
  // centralized place for all the constants
  public static final String AUDIO_MP3_MEDIA_TYPE = "audio/mpeg";
  public static final String REC_MEDIA_TYPE = "application/vnd.zoom.recd";
  public static final String JSON = MediaType.APPLICATION_JSON;
  public static final String AUDIO_MEDIA_TYPE = "audio/*";
  public static final String AUDIO_WAV_MEDIA_TYPE = "audio/vnd.wave";
  public static final String CSV_MIME_TYPE = "text/csv";
  public static final String IQY_MIME_TYPE = "text/x-ms-iqy";

  public static final String SESSIONID_HEADER = "sessionId";
  public static final String COUPLE_SID_PARAMETER = "couplesid";

  private ApiConstants() {
    throw new AssertionError();
  }

  public static enum ResourceFormatEnum {
    mp3(AUDIO_MP3_MEDIA_TYPE),
    recd(REC_MEDIA_TYPE),
    xml(MediaType.TEXT_XML),
    csv(CSV_MIME_TYPE),
    iqy(IQY_MIME_TYPE),
    json(MediaType.APPLICATION_JSON),
    wav(AUDIO_WAV_MEDIA_TYPE),
    audio(AUDIO_MP3_MEDIA_TYPE + "," + AUDIO_WAV_MEDIA_TYPE);

    private final String mimeType;

    ResourceFormatEnum(String mime) {
      this.mimeType = mime;
    }

    public String getMimeType() {
      return mimeType;
    }
  }
}