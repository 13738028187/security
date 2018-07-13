package com.hzdy.hardware;

import java.util.Locale;

public enum Parameter {

	RESOLUTION_SECONDS("resolution-seconds"),

	//获取Application地址
	STORAGE_DIRECTORY("storage-directory"),

	LOG("log"),
	
	ERROR_TRANSFORM_PATTERN("error-transform-pattern"),

	
	DISPLAYED_COUNTERS("displayed-counters"),

	CUSTOM_REPORTS("custom-reports"),

	SAMPLING_EXCLUDED_PACKAGES("sampling-excluded-packages"),

	SAMPLING_INCLUDED_PACKAGES("sampling-included-packages"),

	NO_DATABASE("no-database"),

	QUARTZ_DEFAULT_LISTENER_DISABLED("quartz-default-listener-disabled"),

	GZIP_COMPRESSION_DISABLED("gzip-compression-disabled"),
	
	SYSTEM_ACTIONS_ENABLED("system-actions-enabled"),

	CSRF_PROTECTION_ENABLED("csrf-protection-enabled"),

	
	ALLOWED_ADDR_PATTERN("allowed-addr-pattern"),

	/**
	 * List of authorized users for BASIC auth, when you do no want to use a realm and "security-constraint" in web.xml.<br/>
	 * Format : user:password, one by line or separated by comma <br/>
	 * <pre>
	 * user1:pwd1, user2:pwd2
	 * user3:pwd3
	 * </pre>
	 */
	AUTHORIZED_USERS("authorized-users"),

	PLUGIN_AUTHENTICATION_DISABLED("plugin-authentication-disabled"),

	UPDATE_CHECK_DISABLED("update-check-disabled"),

	DISABLED("disabled"),
	
	DATASOURCES("datasources"),

	REWRAP_DATASOURCES("rewrap-datasources"),

	MAIL_SESSION("mail-session"),

	ADMIN_EMAILS("admin-emails"),

	MAIL_PERIODS("mail-periods"),

	TRANSPORT_FORMAT("transport-format"),

	LOCALE("locale"),

	MONITORING_PATH("monitoring-path"),
	
	ANALYTICS_ID("analytics-id"),

	CONTEXT_FACTORY_ENABLED("context-factory-enabled"),

	DNS_LOOKUPS_DISABLED("dns-lookups-disabled"),

	CONNECTIONS_STACK_TRACES_DISABLED("connections-stack-traces-disabled"),
	
	LOGGER_CLASS("logger-class"),

	JMX_EXPOSE_ENABLED("jmx-expose-enabled"),
	
	APPLICATION_NAME("application-name"),

	APPLICATION_VERSION("application-version"),

	MAVEN_REPOSITORIES("maven-repositories"),

	RUM_ENABLED("rum-enabled");

	private final String code;

	Parameter(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	static Parameter valueOfIgnoreCase(String parameter) {
		return valueOf(parameter.toUpperCase(Locale.ENGLISH).trim());
	}
}
