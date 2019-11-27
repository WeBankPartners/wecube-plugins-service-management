package com.webank.servicemanagement.commons;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service.management")
public class AppProperties {

	@ConfigurationProperties(prefix = "service.management.httpclient")
	public class HttpClientProperties {
		private int connectTimeout = 30000;
		private int requestTimeout = 30000;
		private int socketTimeout = 60000;
		private int maxTotalConnections = 50;
		private int poolSizeOfScheduler = 50;
		private int defaultKeepAliveTimeMillis = 20000;
		private int closeIdleConnectionWaitTimeSecs = 30;

		public int getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public int getRequestTimeout() {
			return requestTimeout;
		}

		public void setRequestTimeout(int requestTimeout) {
			this.requestTimeout = requestTimeout;
		}

		public int getSocketTimeout() {
			return socketTimeout;
		}

		public void setSocketTimeout(int socketTimeout) {
			this.socketTimeout = socketTimeout;
		}

		public int getMaxTotalConnections() {
			return maxTotalConnections;
		}

		public void setMaxTotalConnections(int maxTotalConnections) {
			this.maxTotalConnections = maxTotalConnections;
		}

		public int getPoolSizeOfScheduler() {
			return poolSizeOfScheduler;
		}

		public void setPoolSizeOfScheduler(int poolSizeOfScheduler) {
			this.poolSizeOfScheduler = poolSizeOfScheduler;
		}

		public int getDefaultKeepAliveTimeMillis() {
			return defaultKeepAliveTimeMillis;
		}

		public void setDefaultKeepAliveTimeMillis(int defaultKeepAliveTimeMillis) {
			this.defaultKeepAliveTimeMillis = defaultKeepAliveTimeMillis;
		}

		public int getCloseIdleConnectionWaitTimeSecs() {
			return closeIdleConnectionWaitTimeSecs;
		}

		public void setCloseIdleConnectionWaitTimeSecs(int closeIdleConnectionWaitTimeSecs) {
			this.closeIdleConnectionWaitTimeSecs = closeIdleConnectionWaitTimeSecs;
		}
	}

	@ConfigurationProperties(prefix = "service.management")
	public class ServiceManagementProperties {
		private String wecubeCoreAddress;

		public String getWecubeCoreAddress() {
			return wecubeCoreAddress;
		}

		public void setWecubeCoreAddress(String wecubeCoreAddress) {
			this.wecubeCoreAddress = wecubeCoreAddress;
		}
	}

}
