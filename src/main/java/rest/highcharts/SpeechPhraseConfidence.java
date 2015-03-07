package rest.highcharts;

/**
 * Confidence level representing all confidence values between specified min value (inclusive)
 * and the next level's min (exclusive).
 * Theoretical min and max for confidence is 0% and 100% respectively.
 * The min values are specified in percents (e.g., 65% is specified as 65, not 0.65).
 */
public enum SpeechPhraseConfidence {
  MAYBE(20d), PROBABLY(40d), CERTAINLY(65d);

  private final double value;
  public final static double MAX = 100d;

  SpeechPhraseConfidence(double confidenceValue) {
    this.value = confidenceValue;
  }

  public SpeechPhraseConfidence inverse() {
    switch (this){
      case MAYBE: return CERTAINLY;
      case PROBABLY: return PROBABLY;
      case CERTAINLY: return MAYBE;
    }
    throw new IllegalStateException("Confidence level has no inverse value defined: " + this);
  }

  public double getThresholdValue() {
    return value;
  }

  public static SpeechPhraseConfidence fromValue(double value) {
    if (value >= CERTAINLY.value) {
      return CERTAINLY;
    } else if (value >= PROBABLY.value) {
      return PROBABLY;
    } else if (value >= MAYBE.value) {
      return MAYBE;
    }
    return null;
  }
}
