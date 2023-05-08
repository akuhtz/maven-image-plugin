package de.akquinet.jbosscc.maven.plugin.image;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;

import java.awt.Color;

public class ColorUtilTest
{
  @Test
  public void fromRgbOrNameCreatesCorrectColorWhenRgbValueIsGiven() throws Exception
  {
    Color fromString = ColorUtil.fromRgbOrName("#AA00FF");

    Assertions.assertThat(fromString.getRed()).isEqualTo(170);
    Assertions.assertThat(fromString.getGreen()).isEqualTo(0);
    Assertions.assertThat(fromString.getBlue()).isEqualTo(255);
  }

  @Test
  public void fromRgbCreatesCorrectColorWhenMixedCaseRgbValueIsGiven() throws Exception
  {
    Color fromString = ColorUtil.fromRgbOrName("#aA00fF");

    Assertions.assertThat(fromString.getRed()).isEqualTo(170);
    Assertions.assertThat(fromString.getGreen()).isEqualTo(0);
    Assertions.assertThat(fromString.getBlue()).isEqualTo(255);
  }

  @Test
  public void fromRgbOrNameCreatesCorrectColorWhenColorNameIsGiven() throws Exception
  {
    Color fromString = ColorUtil.fromRgbOrName("MAGENTA");

    Assertions.assertThat(fromString).isEqualTo(Color.MAGENTA);
  }

  @Test
  public void fromNameThrowsIllegalArgumentExceptionWhenNonExistingColorNameIsGiven()
  {
    Assertions
            .assertThatThrownBy(() -> ColorUtil.fromName("doesnotexist")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void fromNameThrowsIllegalArgumentExceptionWhenNullIsPassed()
  {
    Assertions
            .assertThatThrownBy(() -> ColorUtil.fromName(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void fromRgbThrowsIllegalArgumentExceptionWhenNullIsPassed()
  {
    Assertions
            .assertThatThrownBy(() -> ColorUtil.fromRgb(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void fromRgbThrowsIllegalArgumentExceptionWhenNameWithoutLeadingHashmarkIsPassed()
  {
    Assertions
            .assertThatThrownBy(() -> ColorUtil.fromRgb("AA00FF")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void fromRgbThrowsIllegalArgumentExceptionWhenNameWithWrongFormatIsPassed() 
  {
    Assertions
            .assertThatThrownBy(() -> ColorUtil.fromRgb("#aa00ff55")).isInstanceOf(IllegalArgumentException.class);
  }
}
