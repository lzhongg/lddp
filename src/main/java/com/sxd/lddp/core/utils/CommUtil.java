package com.sxd.lddp.core.utils;

import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;

import java.io.*;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;



public final class CommUtil
{

	/**
	 * <p>
	 * 功能描述:将数字格式化为指定长度的字符串格式
	 * </p>
	 * <p>
	 * 输入参数:数值，长度
	 * </p>
	 */
	public static String FormatNum(int num, int len)
	{
		String s = String.valueOf(num);
		if (s.length() < len)
		{
			s = CommUtil.FILL("0", '0', len - s.length(), 'L') + s;
		}
		return s;
	}

	/**
	 * 生成长度为n的字符串
	 */
	public static String blank(int n)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++)
			sb.append(' ');
		return sb.toString();
	}

	/**
	 * <p>
	 * 方法名称:
	 * </p>
	 * <p>
	 * 功能描述:字符串填充
	 * </p>
	 * <p>
	 * 参数介绍:需要填充的字符串;填充的字符;填充长度;左右标志
	 * </p>
	 * 
	 */
	public static String FILL(String s, char c, int n, char f)
	{
		int iByteLen = StringToBytes(s).length;
		if (iByteLen >= n)
		{
			return s;
		}
		else
		{
			byte[] fillChars = new byte[n - iByteLen];
			for (int i = 0; i < fillChars.length; i++)
				fillChars[i] = (byte) c;

			if (f == 'L') // 左补
			{
				return new String(fillChars) + s;
			}
			else
			// 右补
			{
				return s + new String(fillChars);
			}

		}
	}

	/**
	 * 将字符串转为字符型
	 *
	 * @param str
	 * @return
	 */
	public static char StringToChar(String str)
	{
		if (str == null || str.length() <= 0)
			return ' ';
		else
			return str.charAt(0);
	}

	/**
	 * 将字符串转为字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] StringToBytes(String str)
	{
		try
		{
			if (str == null || str.length() <= 0)
				return new byte[0];
			else
				return str.getBytes(Constant.CHAR_GBK);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 方法名称:
	 * </p>
	 * <p>
	 * 功能描述:字符串填充、超长截取
	 * </p>
	 * <p>
	 * 参数介绍:需要填充的字符串;填充的字符;填充长度;左右标志
	 * </p>
	 * 
	 */
	public static String FILLFormate(String s, char c, int n, char f)
	{
		int iByteLen = StringToBytes(s).length;
		if (iByteLen >= n)
		{
			byte[] fillChars = new byte[n];
			for (int i = 0; i < fillChars.length; i++)
			{
				
				fillChars[i] = s.getBytes()[i];
			}
			return new String(fillChars);
		}
		else
		{
			byte[] fillChars = new byte[n - iByteLen];
			for (int i = 0; i < fillChars.length; i++)
				fillChars[i] = (byte) c;

			if (f == 'L') // 左补
			{
				return new String(fillChars) + s;
			}
			else
			// 右补
			{
				return s + new String(fillChars);
			}

		}
	}

	/**
	 * <p>
	 * 功能描述:将数字格式化为 "01"格式
	 * </p>
	 * <p>
	 * 输入参数:数值
	 * </p>
	 */
	public static String FormatNum(int num)
	{
		String s = String.valueOf(num);
		if (num < 10)
		{
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 获得分钟格式的交易时间，格式：HH24MI，如：0812或2007
	 * 
	 * @return
	 */
	public static String getExchMinute()
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);

		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);

		return sb.toString();
	}

	/**
	 * 获得分钟格式的交易时间，格式：HH24:MI，如：08:12或20:07 其中":"号是输入参数ch
	 * 
	 * @param ch
	 *            分钟符
	 * @return
	 */
	public static String getExchMinute(char ch)
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		sb.append(ch);
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);

		return sb.toString();
	}

	/**
	 * 获取当前交易时间，如122415，12小时24分15秒
	 */
	public static String getExchTime()
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);
		if (_second < 10)
			sb.append("0");
		sb.append(_second);

		return sb.toString();
	}

	/**
	 * 转换标准的时间（格式 HH24:MI:SS）致秒数。
	 * 
	 * @param sExchTime
	 *            格式 HH24:MI:SS
	 * @return 返回该时间的秒数
	 */
	public static int changeToSecond(String sExchTime) throws Exception
	{
		String sHour = "0";
		String sMinute = "0";
		String sSecond = "0";

		if (sExchTime.length() == 6)
		{
			sHour = sExchTime.substring(0, 2);
			sMinute = sExchTime.substring(2, 4);
			sSecond = sExchTime.substring(4, 6);
		}
		else if (sExchTime.length() == 8 && sExchTime.charAt(2) == ':'
				&& sExchTime.charAt(5) == ':')
		{
			sHour = sExchTime.substring(0, 2);
			sMinute = sExchTime.substring(3, 5);
			sSecond = sExchTime.substring(6, 8);
		}
		else
		{
			throw new Exception("时间格式不正确，应该为 [HH24:MI:SS]或[HH24MISS] !");
		}

		return Integer.parseInt(sHour) * 60 * 60 + Integer.parseInt(sMinute)
				* 60 + Integer.parseInt(sSecond);
	}

	/**
	 * 获取当前的交易时间，如12:24:15，12小时24分15秒
	 * 
	 * @param ch
	 *            分隔符
	 * @return
	 */
	public static String getExchTime(char ch)
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		sb.append(ch);
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);
		sb.append(ch);
		if (_second < 10)
			sb.append("0");
		sb.append(_second);

		return sb.toString();
	}

	/**
	 * 获得当前的毫秒时间
	 * 
	 * @return
	 */
	public static String getTimeInMs()
	{
		String TimeStr;
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		long _ms = nowtime.getTimeInMillis() % 1000;// 获取毫秒数

		TimeStr = FormatNum(_hour) + FormatNum(_minute) + FormatNum(_second);
		TimeStr = TimeStr + CommUtil.FILL(String.valueOf(_ms), '0', 3, 'L');
		return TimeStr;
	}

	/**
	 * 使用分隔符分隔当前时间， 如c=':'，则格式如：12:24:32 ,代表12时24分32秒
	 * 
	 * @param c
	 *            分隔符
	 * @return
	 */
	public static String getTime(char c)
	{
		String TimeStr;
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		TimeStr = FormatNum(_hour) + c + FormatNum(_minute) + c
				+ FormatNum(_second);
		return TimeStr;
	}

	/**
	 * 获取当前时间的6位形式，格式如：122432 ,代表12时24分32秒
	 * 
	 * @return
	 */
	public static String getTime()
	{
		String TimeStr;
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		TimeStr = FormatNum(_hour) + FormatNum(_minute) + FormatNum(_second);
		return TimeStr;
	}

	public static int getNvlInt(ResultSet rs, int columnIndex)
			throws SQLException
	{
		int tmpInt = rs.getInt(columnIndex);
		return tmpInt;
	}

	public static int getNvlInt(ResultSet rs, String columnName)
			throws SQLException
	{
		int tmpInt = rs.getInt(columnName);
		return tmpInt;
	}

	public static long getNvlLong(ResultSet rs, int columnIndex)
			throws SQLException
	{
		long tmpLong = rs.getLong(columnIndex);
		return tmpLong;
	}

	public static long getNvlLong(ResultSet rs, String columnName)
			throws SQLException
	{
		long tmpLong = rs.getLong(columnName);
		return tmpLong;
	}

	public static double getNvlDouble(ResultSet rs, int columnIndex)
			throws SQLException
	{
		return rs.getDouble(columnIndex);
	}

	public static double getNvlDouble(ResultSet rs, String columnName)
			throws SQLException
	{
		return rs.getDouble(columnName);
	}

	public static String getNvlClob(ResultSet rs, String columnName)
			throws SQLException, IOException
	{
		StringBuffer sbResult = new StringBuffer();
		Clob clob = rs.getClob(columnName);
		BufferedReader brClob = null;
		try
		{
			brClob = new BufferedReader(clob.getCharacterStream());

			for (;;)
			{
				String line = brClob.readLine();
				if (line == null)
					break;
				sbResult.append(line);
			}
		}
		finally
		{
			if (brClob != null)
				brClob.close();
		}
		return sbResult.toString();
	}

	public static String getNvlClob(ResultSet rs, int columnIndex)
			throws SQLException, IOException
	{
		StringBuffer sbResult = new StringBuffer();
		Clob clob = rs.getClob(columnIndex);
		BufferedReader brClob = null;
		try
		{
			brClob = new BufferedReader(clob.getCharacterStream());

			for (;;)
			{
				String line = brClob.readLine();
				if (line == null)
					break;
				sbResult.append(line);
			}
		}
		finally
		{
			if (brClob != null)
				brClob.close();
		}
		return sbResult.toString();
	}

	public static BigDecimal getNvlMoney(ResultSet rs, int columnIndex)
			throws SQLException
	{
		BigDecimal bd_money = rs.getBigDecimal(columnIndex);
		if (bd_money == null)
			return BigDecimal.ZERO;
		else
			return bd_money.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getNvlMoney(ResultSet rs, String columnName)
			throws SQLException
	{
		BigDecimal bd_money = rs.getBigDecimal(columnName);
		if (bd_money == null)
			return BigDecimal.ZERO;
		else
			return bd_money.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static String getNvlString(ResultSet rs, int columnIndex)
			throws SQLException
	{
		String tmpStr = rs.getString(columnIndex);
		if (tmpStr == null)
		{
			tmpStr = "";
		}
		return tmpStr.trim();
	}

	public static String getNvlString(ResultSet rs, String columnName)
			throws SQLException
	{
		String tmpStr = rs.getString(columnName);
		if (tmpStr == null)
		{
			tmpStr = "";
		}
		return tmpStr.trim();
	}

	public static String getNvlString(HashMap<String,Object> map, String columnName)
			throws SQLException
	{
		String tmpStr = String.valueOf(map.get(columnName));
		if (tmpStr == null)
		{
			tmpStr = "";
		}
		return tmpStr.trim();
	}

	public static byte[] getNvlBytes(ResultSet rs, int columnIndex)
			throws SQLException
	{
		String str = rs.getString(columnIndex);
		if (str == null)
		{
			return "".getBytes();
		}
		else
			return str.getBytes();
	}

	public static byte[] getNvlBytes(ResultSet rs, String columnName)
			throws SQLException
	{
		String str = rs.getString(columnName);
		if (str == null)
		{
			return "".getBytes();
		}
		else
			return str.getBytes();
	}

	public static char getNvlChar(ResultSet rs, int columnIndex)
			throws SQLException
	{
		byte[] bytes = rs.getBytes(columnIndex);
		if (bytes == null || bytes.length <= 0)
		{
			return '\0';
		}
		return (char) bytes[0];
	}

	public static char getNvlChar(ResultSet rs, String columnName)
			throws SQLException
	{
		byte[] bytes = rs.getBytes(columnName);
		if (bytes == null || bytes.length <= 0)
		{
			return '\0';
		}
		return (char) bytes[0];
	}

	public static BigDecimal getNvlBigDecimalByPrecision(ResultSet rs,
			int columnIndex, int pre) throws SQLException
	{
		return getNvlBigDecimal(rs, columnIndex).setScale(pre,
				BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getNvlBigDecimalByPrecision(ResultSet rs,
			String columnName, int pre) throws SQLException
	{
		return getNvlBigDecimal(rs, columnName).setScale(pre,
				BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getNvlBigDecimal(ResultSet rs, int columnIndex)
			throws SQLException
	{
		BigDecimal tmpBigDecimal = rs.getBigDecimal(columnIndex);
		if (tmpBigDecimal == null)
		{
			tmpBigDecimal = new BigDecimal(0.00);
		}
		return tmpBigDecimal;
	}

	public static BigDecimal getNvlBigDecimal(ResultSet rs, String columnName)
			throws SQLException
	{
		BigDecimal tmpBigDecimal = rs.getBigDecimal(columnName);
		if (tmpBigDecimal == null)
		{
			tmpBigDecimal = new BigDecimal(0.00);
		}
		return tmpBigDecimal;
	}

	public static String getNvlDateString(ResultSet rs, int columnIndex)
			throws SQLException
	{
		java.sql.Timestamp date = rs.getTimestamp(columnIndex);
		if (date != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}
		else
		{
			return "";
		}
	}

	public static String getNvlDateString(ResultSet rs, String columnName)
			throws SQLException
	{
		java.sql.Timestamp date = rs.getTimestamp(columnName);
		if (date != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}
		else
		{
			return "";
		}
	}

	/**
	 * 获取完全格式的的日期格式
	 * 
	 * @return 格式如 20008-09-15 10:33:25:12
	 */
	public static String getFullDateTime()
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		int _millisecond = nowtime.get(Calendar.MILLISECOND); // 获取毫秒数

		sb.append(_year);
		sb.append("-");
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		sb.append("-");
		if (_day < 10)
			sb.append("0");
		sb.append(_day);
		sb.append(" ");

		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		sb.append(":");
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);
		sb.append(":");
		if (_second < 10)
			sb.append("0");
		sb.append(_second);
		sb.append(":");
		if (_millisecond < 10)
			sb.append("00");
		else if (_millisecond < 100)
			sb.append("0");
		sb.append(_millisecond);

		return sb.toString();
	}

	/**
	 * <p>
	 * 函数功能：获得异常的详细信息
	 * </p>
	 * <p>
	 * 输入参数：e 异常对象
	 * </p>
	 * <p>
	 * 返回值：将异常的堆栈信息转为字符串
	 * </p>
	 */
	public static String getExpStack(Exception e)
	{
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bo);
		e.printStackTrace(pw);
		pw.flush();
		pw.close();
		return bo.toString();
	}

	public static String getExpStack(Throwable e)
	{
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bo);
		e.printStackTrace(pw);
		pw.flush();
		pw.close();
		return bo.toString();
	}

	/**
	 * <p>
	 * 函数功能：获取机器日期
	 * </p>
	 * <p>
	 * 输入参数：无
	 * </p>
	 * <p>
	 * 返回值：机器日期，格式：YYYYMMDD
	 * </p>
	 */
	public static String getPCDate()
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数

		sb.append(_year);
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		if (_day < 10)
			sb.append("0");
		sb.append(_day);

		return sb.toString();
	}

	/**
	 * 获得中文的机器日期
	 * 
	 * @return
	 */
	public static String getPCDateChinese()
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数

		sb.append(_year);
		sb.append("年");
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		sb.append("月");
		if (_day < 10)
			sb.append("0");
		sb.append(_day);
		sb.append("日");

		return sb.toString();
	}

	/**
	 * 时间相加减
	 * gc.add(field, day);field=1 表示year,2表示月,3表示周,5表示日;day正数是加,负数是减
	 * @return
	 */
	public static String addDateByStyle(Date date,int field,int amount,String style)
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(field, amount);
		gc.getTime();
		SimpleDateFormat df = new SimpleDateFormat(style);
		return df.format(gc.getTime());
	}
	
	public static String currentDateTime(int type)
	{
		SimpleDateFormat df = null;
		String style = null;
		switch (type)
		{
		case 0:
			style = "yyyy-MM-dd HH:mm:ss";
			break;
		case 1:
			style = "yyyy-MM-dd";
			break;
		case 2:
			style = "HH:mm:ss";
			break;
		case 3:
			style = "yyyyMMddhhmmss";
			break;
		case 4:
			style = "yyyyMMdd";
			break;
		case 5:
			style = "HHmmss";
			break;
		case 6:
			style = "yyyyMM";
			break;
		default:
			style = "yyyy-MM-dd hh:mm:ss";
			break;
		}

		try
		{
			df = new SimpleDateFormat(style);
			return df.format(new Date());
		}
		catch (Exception ex)
		{
			df = new SimpleDateFormat("yyyyMMddhhmmss");
			return df.format(new Date());
		}
	}

	/**
	 * 获得系统的交易日期
	 * 
	 * @return 如果没有则返回null
	 */
	public static String getExchDate()
	{
		return currentDateTime(4);
	}

	/**
	 * <p>
	 * 函数功能：获取机器日期
	 * </p>
	 * <p>
	 * 输入参数：c,日期之间的分隔符
	 * </p>
	 * <p>
	 * 返回值：用分隔符c分隔的日期
	 * </p>
	 */
	public static String getPCDate(char c)
	{
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数

		sb.append(_year);
		sb.append(c);
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		sb.append(c);
		if (_day < 10)
			sb.append("0");
		sb.append(_day);

		return sb.toString();
	}

	/**
	 * 获得指定日期的上一天
	 * 
	 * @param exch_date
	 *            当前日期，格式 YYYYMMDD
	 * @return 上一个日期
	 */
	public static String getLastDate(String exch_date)
	{
		int year = Integer.parseInt(exch_date.substring(0, 4));
		int month = Integer.parseInt(exch_date.substring(4, 6));
		int day = Integer.parseInt(exch_date.substring(6, 8));
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		gc.add(GregorianCalendar.DAY_OF_MONTH, -1);
		year = gc.get(GregorianCalendar.YEAR);
		month = gc.get(GregorianCalendar.MONTH) + 1;
		day = gc.get(GregorianCalendar.DAY_OF_MONTH);
		return FormatNum(year) + FormatNum(month) + FormatNum(day);
	}

	/**
	 * <p>
	 * 函数功能：获得指定日期的下一天
	 * </p>
	 * <p>
	 * 输入参数：exch_date 当前日期，格式 YYYYMMDD
	 * </p>
	 * <p>
	 * 返回值：下一日期
	 * </p>
	 */
	public static String getNextDate(String exch_date)
	{
		int year = Integer.parseInt(exch_date.substring(0, 4));
		int month = Integer.parseInt(exch_date.substring(4, 6));
		int day = Integer.parseInt(exch_date.substring(6, 8));
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
		year = gc.get(GregorianCalendar.YEAR);
		month = gc.get(GregorianCalendar.MONTH) + 1;
		day = gc.get(GregorianCalendar.DAY_OF_MONTH);
		return FormatNum(year) + FormatNum(month) + FormatNum(day);
	}

	/**
	 * <p>
	 * 函数功能：　获得当前日期的下个月一日
	 * </p>
	 * <p>
	 * 输入参数： 当前日期
	 * </p>
	 * <p>
	 * 返回值：　　下个月的一日
	 * </p>
	 */
	public static String getNextMonthDate(String exch_date)
	{
		String tmp = "01,03,05,07,08,10,12";
		String year = exch_date.substring(0, 4);
		String month = exch_date.substring(4, 6);
		String day = "30";
		if (month.compareTo("02") == 0)
		{
			int y = Integer.parseInt(year);
			if ((y % 4 == 0 && y % 100 == 0) || y % 400 == 0)
				day = "29";
			else
				day = "28";
		}
		else if (tmp.indexOf(month) != -1)
		{
			day = "31";
		}
		else
		{
			day = "30";
		}
		return CommUtil.getNextDate(year + month + day);
	}

	/**
	 * 将一个长整型的数，转换合适的字节数组。
	 * 
	 * @param lValue
	 * @return 返回最少3个字节长度，最大不固定的长度的字节数组
	 */
	public static byte[] toBytesByLong(long lValue)
	{
		// 最高位0代表正数，1代表负数
		byte[] buff = null;
		boolean bIsNegative = false;
		if (lValue < 0)
		{
			lValue = -1 * lValue;
			bIsNegative = true;
		}

		String sBinaryString = Long.toBinaryString(lValue);
		int iBinLen = sBinaryString.length() + 1;
		int m = iBinLen % 8;
		if (m > 0)
			buff = new byte[iBinLen / 8 + 1];
		else
			buff = new byte[iBinLen / 8];

		if (buff.length <= 3)
			buff = new byte[3];

		sBinaryString = CommUtil.FILL(sBinaryString, '0', buff.length * 8, 'L');

		if (bIsNegative == true) // 负
			sBinaryString = "1" + sBinaryString.substring(1);

		for (int i = 0; i < buff.length; i++)
		{
			buff[i] = (byte) Integer.parseInt(sBinaryString.substring(i * 8,
					(i + 1) * 8), 2);
		}

		return buff;
	}

	/**
	 * 将一个字节数组转换为长整型
	 * 
	 * @param bytes
	 *            字节数组,
	 * @return 返回转换后的长整型
	 */
	public static long toLongByBytes(byte[] bytes)
	{
		String sHexString = "";
		for (int i = 0; i < bytes.length; i++)
		{
			String tmp = Integer.toBinaryString(bytes[i]);

			if (tmp.length() < 8)
				tmp = CommUtil.FILL(tmp, '0', 8, 'L'); // 补足8位
			else
				tmp = tmp.substring(tmp.length() - 8); // 取有效的后8位

			sHexString += tmp;
		}

		// 最高位0代表正数，1代表负数
		if (sHexString.charAt(0) == '1')// 负数
		{
			sHexString = "0" + sHexString.substring(1);
			return Long.parseLong(sHexString, 2) * -1;
		}
		else
		{
			return Long.parseLong(sHexString, 2);
		}

	}

	/**
	 * 压缩整型值
	 * 
	 * @param vHtValues
	 *            key=行情字段的序号,value=乘以100后的整型值
	 * @return
	 */
	/*public static String zipLongValue(Hashtable<Integer, Long> vHtValues)
			throws Exception
	{
		ByteBuffer bb = ByteBuffer.allocate(2048);

		// 根据设置的字段，分别压缩
		Enumeration<Integer> enumKey = vHtValues.keys();
		while (enumKey.hasMoreElements())
		{
			Integer key = enumKey.nextElement();
			Long value = vHtValues.get(key);

			// 获得压缩后的字节数组
			byte[] buff = CommUtil.toBytesByLong(value);

			// 获得压缩头 前六位代表字段序号 + 后2位代表长度
			String sBinHead = Long.toBinaryString(key.intValue())
					+ CommUtil.FILL(Long.toBinaryString(buff.length - 3), '0',
							2, 'L');

			byte bHead = Integer.valueOf(sBinHead, 2).byteValue();
			bb.put(bHead);
			bb.put(buff);
		}

		byte[] bytes = new byte[bb.position()];
		System.arraycopy(bb.array(), 0, bytes, 0, bytes.length);
		String sZipValue = DESede.base64Encode(bytes);

		;// CommUtil.WriteLog(Constant.DEBUG, "压缩报文[" + bytes.length + "," +
		// sZipValue.length() + "]：" + sZipValue);

		return sZipValue;
	}*/

	/**
	 * 解压缩长整型数据
	 * 
	 * @param sZipMsg
	 *            压缩后的整型数据
	 * @return
	 * @throws Exception
	 *//*
	public static Hashtable<Integer, Long> unzipLongValue(String sZipMsg)
			throws Exception
	{
		Hashtable<Integer, Long> ht = new Hashtable<Integer, Long>();

		byte[] bValues = DESede.base64DecodeToBytes(sZipMsg);

		for (int i = 0; i < bValues.length; i++)
		{
			// 获得压缩头
			byte bHead = bValues[i];
			String sBinHead = CommUtil.FILL(Integer.toBinaryString(bHead), '0',
					8, 'L');

			if (sBinHead.length() > 8) // 只取有效的后8位
				sBinHead = sBinHead.substring(sBinHead.length() - 8);

			// 获得字段顺序号
			int iSeqNo = Integer.parseInt(sBinHead.substring(0, 6), 2);

			// 获得字段值的字节数
			int iBuffLen = 3 + Integer.parseInt(sBinHead.substring(6, 8), 2);

			if (i >= bValues.length - 1)
				break;

			byte[] buff = new byte[iBuffLen];
			for (int j = 0; j < iBuffLen; j++)
			{
				buff[j] = bValues[i + j + 1];
			}

			i = i + buff.length;

			long bValue = CommUtil.toLongByBytes(buff);

			ht.put(iSeqNo, bValue);
		}

		return ht;
	}*/

	/**
	 * 压缩字节数组
	 * 
	 * @param bytes
	 *            原始字符串转换后的字节数组
	 * @return 返回压缩后的字符数组
	 */
	public static byte[] zip(byte[] bytes) throws Exception
	{
		ByteArrayOutputStream out = null;
		try
		{
			out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(bytes);
			gzip.close();
			return out.toByteArray();
		}
		finally
		{
			if (out != null)
				out.close();
		}
	}

	/**
	 * 解压缩字节数组
	 * 
	 * @param bytes
	 *            压缩后字节数组
	 * @return 返回解压后的字节数组
	 */
	public static byte[] unzip(byte[] bytes) throws Exception
	{
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		try
		{
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(bytes);
			GZIPInputStream gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0)
			{
				out.write(buffer, 0, n);
			}
			return out.toByteArray();
		}
		finally
		{
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}

	}

	/**
	 * 解析字符串形式的IP地址列表
	 * 
	 * @param sAddress
	 *            字符串格式的IP地址列表，格式为： Ip:Port,Ip:Port
	 * @return
	 */
	public static ArrayList<InetSocketAddress> parseStrAddress(String sAddress)
	{
		ArrayList<InetSocketAddress> alAddress = new ArrayList<InetSocketAddress>();

		String[] splitA = sAddress.split("\\,");
		for (int i = 0; i < splitA.length; i++)
		{
			String[] splitB = splitA[i].split("\\:");
			if (splitB.length == 2)
			{
				InetSocketAddress addr = new InetSocketAddress(splitB[0],
						Integer.parseInt(splitB[1]));
				alAddress.add(addr);
			}
		}
		return alAddress;
	}

	/**
	 * 获得SQL中的动态参数列表
	 * 
	 * @param sSql
	 * @return
	 */
	public static ArrayList<String> getSqlDynamicParameters(String sSql,
			char cStartChar, char cEndChar)
	{
		ArrayList<String> alPara = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		boolean bStart = false;
		for (int i = 0; i < sSql.length(); i++)
		{
			char ch = sSql.charAt(i);
			if (ch == cStartChar)
			{
				bStart = true;
				sb.delete(0, sb.length());
			}
			else if (ch == cEndChar)
			{
				if (bStart)
				{
					String sPara = sb.toString();
					if (sPara.length() > 0 && sPara.indexOf(" ") == -1)
						alPara.add(sPara);
					sb.delete(0, sb.length());
					bStart = false;
				}
			}
			else if (bStart)
			{
				sb.append(ch);
			}
		}
		return alPara;
	}

	/**
	 * 写文件
	 * 
	 * @param sFileName
	 * @param bIsAppend
	 */
	public synchronized static void writeToFile(String sFileName,
			String sWriteMsg, boolean bIsNewLine, boolean bIsAppend)
			throws IOException
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(sFileName, bIsAppend);
			fos.write(StringToBytes(sWriteMsg));

			if (bIsNewLine)
				fos.write("\n".getBytes());

			fos.flush();

		}
		finally
		{
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * 检查IP是否合法
	 * 
	 * @param sValidIps
	 *            配置的允许IP地址列表
	 * @param sCheckIp
	 *            当前被检查是否可访问的IP地址
	 * @return
	 */
	public static boolean checkIpAllowAccess(String sValidIps, String sCheckIp)
	{
		boolean bIsCheckSucceed = false;

		String[] checkIpNode = sCheckIp.split("\\.");
		if (checkIpNode.length != 4)
			return false;

		String[] splitA = sValidIps.split("\\,");

		// 示例：168.33.114.*,168.33.112.200-207 分隔次序：\\, \\. \\-
		// 1. 分解出每个IP的配置
		for (int i = 0; i < splitA.length; i++)
		{
			String[] splitB = splitA[i].split("\\.");
			if (splitB.length == 4)
			{
				int j = 0;
				// 2.检查单个IP地址中每一个段的模糊配置
				for (j = 0; j < splitB.length; j++)
				{
					// 不匹配，直接退出循环
					if (checkIpNode[j].equals(splitB[j]) == false
							&& "*".equals(splitB[j]) == false
							&& splitB[j].indexOf("-") == -1)
						break;

					String[] splitC = splitB[j].split("\\-");
					if (splitC.length == 2)
					{
						int k = 0;
						int iStartIp = Integer.parseInt(splitC[0]);
						int iEndIp = Integer.parseInt(splitC[1]);
						for (k = iStartIp; k <= iEndIp
								&& bIsCheckSucceed == false; k++)
						{
							if (checkIpNode[j].equals("" + k) == true)// 匹配上，则退出循环
								break;
						}

						// 如果循环全部走完，说明没有匹配上，退出循环，进行下一段地址检查
						if (k > iEndIp)
							break;
					}
				}

				// 如果每一段都检查通过，则说明地址合法
				if (j >= splitB.length)
				{
					bIsCheckSucceed = true;
					break;
				}
			}
		}
		return bIsCheckSucceed;
	}

	
	public static String toString(Object obj)
	{
		if (obj == null)
			return "";
		else
			return obj.toString().replaceAll("\"", "").trim();
	}

	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 * 
	 * @param  c, 需要判断的字符
	 * @return boolean, 返回true,Ascill字符
	 * 
	 */
	public static boolean isLetter(char c)
	{
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param s
	 *            s ,需要得到长度的字符串
	 * @return int, 得到的字符串长度
	 * 
	 */
	public static int length(String s)
	{
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++)
		{
			len++;
			if (!isLetter(c[i]))
			{
				len++;
			}
		}
		return len;
	}

	/**
	 * 创建XML错误返回报文
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	/*public static String createErrXml(String encoding, String errcod,
			String errmsg, TransReqMsg TReqMsg)
	{
		// 建立document对象，用来操作xml文件
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding(encoding);
		// 建立根节点
		Element rootElement = document.addElement("ROOT");
		// XML头信息
		Element headElement = rootElement.addElement("_HEADER_");
		// SOCKET服务命令字（必填）综合账单CBM定制CST查询QRY
		Element prccodElement = headElement.addElement("PRCCOD");
		prccodElement.setText(TReqMsg.prccod);
		// 渠道类型 PB 网银 CMBRUN 柜台（必填）
		Element usertypeElement = headElement.addElement("USERTYPE");
		usertypeElement.setText(TReqMsg.usertype);
		// 交易流水（必填性、唯一性）
		Element seqnoElement = headElement.addElement("SEQNO");
		seqnoElement.setText(TReqMsg.seqno);
		// 返回码 999错误
		Element rtncodElement = headElement.addElement("RTNCOD");
		rtncodElement.setText(Constant.RSP_CODE_FAILD);
		// 错误信息
		Element errormsgElement = rootElement.addElement("_ERRORMSG_");
		// 错误码
		Element errcodElement = errormsgElement.addElement("_ERRCOD_");
		errcodElement.setText(errcod);
		// 错误描述
		Element errmsgElement = errormsgElement.addElement("_ERRMSG_");
		errmsgElement.addCDATA(errmsg);

		return document.asXML();

	}*/

	
	public static byte[] serialize(Object obj) throws Exception
	{
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try
		{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);

			return baos.toByteArray();
		}
		finally
		{
			if (null != baos)
			{
				baos.flush();
				baos.close();
			}
			if (null != oos)
			{
				oos.flush();
				oos.close();
			}
		}
	}

	/**
	 * 
	 * 使用文件通道的方式复制文件 
	 * @param oldFile   源文件
	 * @param newFile   复制到的新文件
	 */
	public static void fileChannelCopy(File oldFile, File newFile, String newPath)throws Exception
	{
		(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
		//File isFExists = new File(newPath);
		//if(!isFExists.exists() || !isFExists.isDirectory())
		//{
			//throw new Exception("路径[" + newPath + "]创建失败,请登陆流程设计器创建流程!");
		//}
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try
		{
			fi = new FileInputStream(oldFile);
			fo = new FileOutputStream(newFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		}
		finally
		{
			try
			{
				if(null != fi)fi.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				if(null != in)in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				if(null != fo)fo.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				if(null != fo)out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath)
			throws Exception
	{
		(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
		File a = new File(oldPath);
		String[] file = a.list();
		File temp = null;
		for (int i = 0; i < file.length; i++)
		{
			if (oldPath.endsWith(File.separator))
			{
				temp = new File(oldPath + file[i]);
			}
			else
			{
				temp = new File(oldPath + File.separator + file[i]);
			}

			if (temp.isFile())
			{
				// 通道拷贝模式
				// FileChannel fcin = new FileInputStream(temp).getChannel();
				// FileChannel fcout = new FileOutputStream(
				// new File(oldPath + File.separator + file[i])).getChannel();
				// ByteBuffer buff = ByteBuffer.allocate(1024);
				// long size = fcin.size();
				// fcin.transferTo(0,fcin.size(),fcout);
				// fcin.close();
				// fcout.close();

				FileInputStream input = null;
				FileOutputStream output = null;
				try
				{
					input = new FileInputStream(temp);
					output = new FileOutputStream(newPath + "/"
							+ (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1)
					{
						output.write(b, 0, len);
					}
				}
				finally
				{
					try
					{
						if(null != output){output.flush();output.close();}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					try
					{
						if(null != input)input.close();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
				}

				
			}
			if (temp.isDirectory())
			{// 如果是子文件夹
				copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
			}
		}

	}

	public static String BufferedReader(String path) throws IOException
	{
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null)
		{
			sb.append(temp + " ");
			temp = br.readLine();
		}
		return sb.toString();
	}

	public static void fileModify(String srcFilePath, String replaceStr,
			String dstFilePath) throws IOException
	{
		FileReader fr = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			fr = new FileReader(srcFilePath);
			br = new BufferedReader(fr);
			String s;
			while ((s = br.readLine()) != null)
			{
				s = s.replace(replaceStr, "");
				sb.append(s);
			}
		}
		finally
		{
			if (null != fr)
			{
				fr.close();
			}

			if (null != br)
			{
				br.close();
			}
		}

		FileWriter fw = null;
		BufferedWriter bw = null;
		(new File(dstFilePath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
		try
		{
			fw = new FileWriter(dstFilePath);
			bw = new BufferedWriter(fw);
			bw.write(sb.toString());
		}
		finally
		{
			if (null != fw)
			{
				fw.close();
			}

			if (null != bw)
			{
				bw.flush();
				bw.close();
			}
		}

	}

	public static String fileModify(String srcFilePath, String replaceStr)
			throws IOException
	{
		FileReader fr = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			fr = new FileReader(srcFilePath);
			br = new BufferedReader(fr);
			String s;
			while ((s = br.readLine()) != null)
			{
				s = s.replace(replaceStr, "");
				sb.append(s);
			}
		}
		finally
		{
			if (null != fr)
			{
				fr.close();
			}

			if (null != br)
			{
				br.close();
			}
		}
		return sb.toString();
	}

	// 从file转为byte[]
	public static byte[] getBytesFromFile(File f)
	{
		if (f == null)
		{
			return null;
		}
		try
		{
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		}
		catch (IOException e)
		{
		}
		return null;
	}

	// 从byte[]转file
	public static File getFileFromBytes(byte[] b, String outputFile)
	{
		BufferedOutputStream stream = null;
		File file = null;
		try
		{
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stream != null)
			{
				try
				{
					stream.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 以随机访问文件的形式网文件中追加数据 此方法
	 * 
	 * @param filename
	 * @param bt
	 */
	public static synchronized  void addToEndOfFileByRandomAccessFile(String filename,
			byte[] bt) throws Exception
	{
		RandomAccessFile randomFile = null;
		try
		{
			File file = new File(filename);
			if (!file.exists())
			{
				file.createNewFile();
			}
			randomFile = new RandomAccessFile(filename, "rw");
			// 文件长度；也就是这个随机访问文件流的字符数
			long filelength = randomFile.length();
			// 把写文件的指针放在这个流的最后
			randomFile.seek(filelength);
			randomFile.write(bt);
		}
		finally
		{
			if (null != randomFile)
			{
				randomFile.close();
			}
		}
	}

	public static boolean delAllFile(String path) throws Exception
	{
		boolean flag = false;
		File file = new File(path);
		if (!file.exists())
		{
			return flag;
		}
		if (!file.isDirectory())
		{
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++)
		{
			if (path.endsWith(File.separator))
			{
				temp = new File(path + tempList[i]);
			}
			else
			{
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile())
			{
				temp.delete();
			}
			if (temp.isDirectory())
			{
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static void delFolder(String folderPath) throws Exception
	{
		delAllFile(folderPath); // 删除完里面所有内容
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		myFilePath.delete(); // 删除空文件夹
	}

	public static String handleException(Exception e)
	{
		String msg = null;
		if (e instanceof InvocationTargetException)
		{
			Throwable targetEx = ((InvocationTargetException) e)
					.getTargetException();
			if (targetEx != null)
			{
				msg = targetEx.getMessage();
			}
		}
		else
		{
			msg = e.getMessage();
		}
		return msg;
	}

	public static Double toDouble(Object obj)
	{
		try
		{
			if (obj == null)
				return 0.0;
			else if (obj.toString().trim().equals(""))
				return 0.0;
			return Double.parseDouble(obj.toString().trim());
		}
		catch (Exception ex)
		{
			return 0.0;
		}
	}
	
	public static int toInt(Object obj)
	{
		try
		{
			if (obj == null)
				return 0;
			else if (obj.toString().trim().equals(""))
				return 0;
			return Integer.parseInt(obj.toString().trim());
		}
		catch (Exception ex)
		{
			return 0;
		}
	}

	
	public static String toStr(Object obj)
	{
		if (obj == null)
			return "";
		else
			return obj.toString().trim();
	}

	
	public static long toLng(Object obj)
	{
		try
		{
			if (obj == null)
				return 0l;
			else if (obj.toString().trim().equals(""))
				return 0l;
			return Long.parseLong(obj.toString().trim());
		}
		catch (Exception ex)
		{
			return 0l;
		}
	}

	
	public static boolean toBool(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		else
		{
			return Boolean.parseBoolean(toStr(obj));
		}
	}
	
	public static Date oprDate(int type, Date dt, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        switch (type) {
            case 0:
                rightNow.add(Calendar.YEAR, num);
                break;
            case 1:
                rightNow.add(Calendar.MONTH, num);
                break;
            case 2:
                rightNow.add(Calendar.DAY_OF_YEAR, num);
                break;
            default:
                rightNow.add(Calendar.DAY_OF_YEAR, num);
                break;
        }
        return rightNow.getTime();
    }

	public static String dateFormat(String style, Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat(style);
		return df.format(date);
	}

	public static Date dateFormat(int type, String dateStr)
	{
		String style = "";
		switch (type)
		{
		case 0:
			style = "yyyy-MM-dd HH:mm:ss";
			break;
		case 1:
			style = "yyyy-MM-dd";
			break;
		case 2:
			style = "HH:mm:ss";
			break;
		case 3:
			style = "yyyyMMddhhmmss";
			break;
		case 4:
			style = "yyyyMMdd";
			break;
		case 5:
			style = "HHmmss";
			break;
		case 6:
			style = "yyyyMM";
			break;
		default:
			style = "yyyy-MM-dd hh:mm:ss";
			break;
		}
		SimpleDateFormat intSdf = new SimpleDateFormat(style);
		try
		{
			Date date = intSdf.parse(dateStr);
			return date;
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param str
	 * @return

	public static byte[] StringToBytes(String str)
	{
		try
		{
			if (str == null || str.length() <= 0)
				return new byte[0];
			else
				return str.getBytes("UTF-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}*/
	public static String utf8ToUnicode(String str)
	{
		char[] myBuffer = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<str.length();i++){
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if(ub == UnicodeBlock.BASIC_LATIN)
			{
				sb.append(myBuffer[i]);
			}
			else if(ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
			{
				int j = (int)myBuffer[i] - 65248;
				sb.append((char)j);
			}
			else
			{
				short s = (short)myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u"+hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}
	public static String unicodeToGBK(String dataStr)
	{
		int index = 0;
		StringBuffer buffer = new StringBuffer();
		int li_len = dataStr.length();
		while(index < li_len)
		{
			if(index >= li_len-1 || !"\\u".equals(dataStr.substring(index,index+2)))
			{
				buffer.append(dataStr.charAt(index));
				index++;
				continue;
			}
			String charStr = "";
			charStr = dataStr.substring(index+2, index+6);
			char letter = (char)Integer.parseInt(charStr,16);
			buffer.append(letter);
			index +=6;
		}
		return buffer.toString();
	}
	
	public static String readMetaData(String filePath)throws Exception
	{
		String content = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try
		{
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis,"UTF-8");
			br = new BufferedReader(isr);
			
			String line = "";
			while((line = br.readLine()) != null)
			{
				content += line;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			fis.close();
		}
		return content;
	}
	
	public static String writeMetaData(String filePath,String content)throws Exception
	{
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try
		{
			fos = new FileOutputStream(filePath);
			osw = new OutputStreamWriter(fos,"UTF-8");
			osw.write(content);
			osw.flush();
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(osw != null)osw.close();
			if(fos != null)fos.close();
		}
		return content;
	}
	public static Map<String,Object> formatJson(String json)
	{
		Map<String,Object> result = new HashedMap();
		Gson gson = new Gson();
		result = gson.fromJson(json,Map.class);
		return result;
	}
}