package com.ibatis;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Date;

/**
 * Statically typed value holders to work around GWT limitations.
 * GWT cannot serialize Object and doesn't allow reflection nor overloaded setters.
 */
public class ValueHolders {
  public static final NullHolder NULL_HOLDER = new NullHolder();

  private ValueHolders() {
  }

  /**
   * Creates a new statically typed {@link ValueHolder} holding the provided value.
   * See the list of existing ValueHolder implementations in this class.
   * GWT generally supports statically typed ValueHolders, however specific examples should be tested before use.
   *
   * @param value the value to be held; or null to get {@link NullHolder}
   * @return a new value holder
   */
  @SuppressWarnings("unchecked")
  @NotNull
  public static <T> ValueHolder<T> forValue(@Nullable T value) {
    if (value == null) {
      return (ValueHolder<T>) NULL_HOLDER;
    } else if (value instanceof String) {
      return (ValueHolder<T>) new StringHolder((String) value);
    } else if (value instanceof Date) {
      return (ValueHolder<T>) new DateHolder((Date) value);
    } else if (value instanceof Character) {
      return (ValueHolder<T>) new CharacterHolder((Character) value);
    } else if (value instanceof Byte) {
      return (ValueHolder<T>) new ByteHolder((Byte) value);
    } else if (value instanceof Short) {
      return (ValueHolder<T>) new ShortHolder((Short) value);
    } else if (value instanceof Integer) {
      return (ValueHolder<T>) new IntegerHolder((Integer) value);
    } else if (value instanceof Long) {
      return (ValueHolder<T>) new LongHolder((Long) value);
    } else if (value instanceof Boolean) {
      return (ValueHolder<T>) new BooleanHolder((Boolean) value);
    } else if (value instanceof Float) {
      return (ValueHolder<T>) new FloatHolder((Float) value);
    } else if (value instanceof Double) {
      return (ValueHolder<T>) new DoubleHolder((Double) value);
    } else if (value instanceof Enum<?>) {
      return (ValueHolder<T>) new EnumHolder((Enum) value);
    } else if (value instanceof Collection<?>) {
      return (ValueHolder<T>) new CollectionHolder((Collection) value);
    } else {
      throw new IllegalArgumentException("Unsupported class: " + value.getClass().getName());
    }
  }

  public static class NullHolder implements ValueHolder<Void> {
    @Override
    public Void getValue() {
      return null;
    }

    @Override
    public String toString() {
      return String.valueOf((Object) null);
    }
  }

  public static class StringHolder implements ValueHolder<String> {
    private String value;

    public StringHolder() {
    }

    public StringHolder(String value) {
      this.value = value;
    }

    @Override
    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  public static class DateHolder implements ValueHolder<Date> {
    private Date value;

    public DateHolder() {
    }

    public DateHolder(Date value) {
      this.value = value;
    }

    @Override
    public Date getValue() {
      return value;
    }

    public void setValue(Date value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class CharacterHolder implements ValueHolder<Character> {
    private Character value;

    public CharacterHolder() {
    }

    public CharacterHolder(Character value) {
      this.value = value;
    }

    @Override
    public Character getValue() {
      return value;
    }

    public void setValue(Character value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class ByteHolder implements ValueHolder<Byte> {
    private Byte value;

    public ByteHolder() {
    }

    public ByteHolder(Byte value) {
      this.value = value;
    }

    @Override
    public Byte getValue() {
      return value;
    }

    public void setValue(Byte value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class ShortHolder implements ValueHolder<Short> {
    private Short value;

    public ShortHolder() {
    }

    public ShortHolder(Short value) {
      this.value = value;
    }

    @Override
    public Short getValue() {
      return value;
    }

    public void setValue(Short value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class IntegerHolder implements ValueHolder<Integer> {
    private Integer value;

    public IntegerHolder() {
    }

    public IntegerHolder(Integer value) {
      this.value = value;
    }

    @Override
    public Integer getValue() {
      return value;
    }

    public void setValue(Integer value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class LongHolder implements ValueHolder<Long> {
    private Long value;

    public LongHolder() {
    }

    public LongHolder(Long value) {
      this.value = value;
    }

    @Override
    public Long getValue() {
      return value;
    }

    public void setValue(Long value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class BooleanHolder implements ValueHolder<Boolean> {
    private Boolean value;

    public BooleanHolder() {
    }

    public BooleanHolder(Boolean value) {
      this.value = value;
    }

    @Override
    public Boolean getValue() {
      return value;
    }

    public void setValue(Boolean value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class FloatHolder implements ValueHolder<Float> {
    private Float value;

    public FloatHolder() {
    }

    public FloatHolder(Float value) {
      this.value = value;
    }

    @Override
    public Float getValue() {
      return value;
    }

    public void setValue(Float value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class DoubleHolder implements ValueHolder<Double> {
    private Double value;

    public DoubleHolder() {
    }

    public DoubleHolder(Double value) {
      this.value = value;
    }

    @Override
    public Double getValue() {
      return value;
    }

    public void setValue(Double value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class EnumHolder<T extends Enum<T>> implements ValueHolder<Enum<T>> {
    private Enum<T> value;

    public EnumHolder() {
    }

    public EnumHolder(Enum<T> value) {
      this.value = value;
    }

    @Override
    public Enum<T> getValue() {
      return value;
    }

    public void setValue(Enum<T> value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class CollectionHolder<T> implements ValueHolder<Collection<T>> {
    private Collection<T> value;

    public CollectionHolder() {
    }

    public CollectionHolder(Collection<T> value) {
      this.value = value;
    }

    @Override
    public Collection<T> getValue() {
      return value;
    }

    public void setValue(Collection<T> value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }
}
