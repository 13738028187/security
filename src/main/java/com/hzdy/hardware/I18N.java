package com.hzdy.hardware;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

final public class I18N {
	// 获取当前包下的translations
	private static final String RESOURCE_BUNDLE_BASE_NAME = Parameters.getResourcePath("translations").replace('/', '.')
			.substring(1);
	// 创建一个线程为Locale国际化
	private static final ThreadLocal<Locale> LOCALE_CONTEXT = new ThreadLocal<Locale>();
	// Locale.ROOT needs 1.6
	// 用于表示根语言环境的有用常量。根语言环境是指其语言、国家、变量是空（""）字符串的语言环境。它被视为所有语言环境的基础语言环境，并且用作语言环境敏感操作的语言/国家的非特定语言环境
	private static final Locale ROOT_LOCALE = Locale.ROOT;
	//获取当前Locale 对象:表示了特定的地理、政治和文化地区
	private static final Locale FIXED_LOCALE = getFixedLocale();

	private I18N() {
		super();
	}

	/**
	 * 设置一个特定的Locale
	 * @param locale
	 *            Locale
	 */
	static void bindLocale(Locale locale) {
		LOCALE_CONTEXT.set(locale);
	}

	/**
	 * 返回当前的Locale 对象
	 * @return Locale
	 */
	static Locale getCurrentLocale() {
		if (FIXED_LOCALE != null) {
			return FIXED_LOCALE;
		}
		final Locale currentLocale = LOCALE_CONTEXT.get();
		if (currentLocale == null) {
			return Locale.getDefault();
		}
		return currentLocale;
	}

	/**
	 * 得到一個ResourceBundle
	 * @return Locale
	 */
	static ResourceBundle getResourceBundle() {
		final Locale currentLocale = getCurrentLocale();
		if (Locale.ENGLISH.getLanguage().equals(currentLocale.getLanguage())) {
			return ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, ROOT_LOCALE);
		}
		return ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, currentLocale);
	}

	/**
	 *删除一个locale对象
	 */
	static void unbindLocale() {
		LOCALE_CONTEXT.remove();
	}

	/**
	 * 根据key获取资源
	 * @param key
	 * @return String
	 */
	public static String getString(String key) {
		return getResourceBundle().getString(key);
	}
	/**
	 * @param key
	 *         
	 * @param arguments
	 *          
	 * @return String
	 */
	public static String getFormattedString(String key, Object... arguments) {
		final String string = getString(key).replace("'", "''");
		return new MessageFormat(string, getCurrentLocale()).format(arguments);
	}

	public static DecimalFormat createIntegerFormat() {
		return new DecimalFormat("#,##0", getDecimalFormatSymbols());
	}

	static DecimalFormat createPercentFormat() {
		return new DecimalFormat("0.00", getDecimalFormatSymbols());
	}

	private static DecimalFormatSymbols getDecimalFormatSymbols() {
	
		return DecimalFormatSymbols.getInstance(getCurrentLocale());
	}
	//设置时间格式
	static DateFormat createDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, getCurrentLocale());
	}

	static DateFormat createDateAndTimeFormat() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, getCurrentLocale());
	}

	static DateFormat createDurationFormat() {
		final DateFormat durationFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.FRENCH);
		durationFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return durationFormat;
	}

	static String getCurrentDate() {
		return createDateFormat().format(new Date());
	}

	static String getCurrentDateAndTime() {
		return createDateAndTimeFormat().format(new Date());
	}

	private static Locale getFixedLocale() {
		// 返回所有已安装语言环境的数组。
		final String locale = Parameters.getParameter(Parameter.LOCALE);
		if (locale != null) {
			for (final Locale l : Locale.getAvailableLocales()) {
				if (l.toString().equals(locale)) {
					return l;
				}
			}
		}
		return null;
	}

	public static void main(String args[]) {
		I18N n = new I18N();
		System.out.println(n.getCurrentLocale());
	}
}
