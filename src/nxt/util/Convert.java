package nxt.util;

import java.math.BigInteger;
import nxt.Nxt;

public final class Convert
{
  public static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyz";
  public static final BigInteger two64 = new BigInteger("18446744073709551616");
  
  public static byte[] convert(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    for (int i = 0; i < arrayOfByte.length; i++)
    {
      int j = "0123456789abcdefghijklmnopqrstuvwxyz".indexOf(paramString.charAt(i * 2));
      int k = "0123456789abcdefghijklmnopqrstuvwxyz".indexOf(paramString.charAt(i * 2 + 1));
      if ((j < 0) || (k < 0) || (j > 15)) {
        throw new NumberFormatException("Invalid hex number: " + paramString);
      }
      arrayOfByte[i] = ((byte)((j << 4) + k));
    }
    return arrayOfByte;
  }
  
  public static String convert(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int k : paramArrayOfByte)
    {
      int m;
      localStringBuilder.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt((m = k & 0xFF) >> 4)).append("0123456789abcdefghijklmnopqrstuvwxyz".charAt(m & 0xF));
    }
    return localStringBuilder.toString();
  }
  
  public static String convert(long paramLong)
  {
    BigInteger localBigInteger = BigInteger.valueOf(paramLong);
    if (paramLong < 0L) {
      localBigInteger = localBigInteger.add(two64);
    }
    return localBigInteger.toString();
  }
  
  public static String convert(Long paramLong)
  {
    return convert(nullToZero(paramLong));
  }
  
  public static Long parseUnsignedLong(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("trying to parse null");
    }
    BigInteger localBigInteger = new BigInteger(paramString.trim());
    if ((localBigInteger.signum() < 0) || (localBigInteger.compareTo(two64) != -1)) {
      throw new IllegalArgumentException("overflow: " + paramString);
    }
    return zeroToNull(localBigInteger.longValue());
  }
  
  public static int getEpochTime()
  {
    return (int)((System.currentTimeMillis() - Nxt.epochBeginning + 500L) / 1000L);
  }
  
  public static Long zeroToNull(long paramLong)
  {
    return paramLong == 0L ? null : Long.valueOf(paramLong);
  }
  
  public static long nullToZero(Long paramLong)
  {
    return paramLong == null ? 0L : paramLong.longValue();
  }
}