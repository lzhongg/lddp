package com.sxd.lddp.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
	@Value("#{appconfig['bootstrap.servers']}")
	public String bootstrapServers;

	@Value("#{appconfig['auto.commit.interval.ms']}")
	public String autoCommit;

	@Value("#{appconfig['enable.auto.commit']}")
	public boolean enableAutoCommit;

	@Value("#{appconfig['heartbeat.interval.ms']}")
	public String heartbeatInterval;

	@Value("#{appconfig['session.timeout.ms']}")
	public String kSessionTimeout;

	@Value("#{appconfig['request.timeout.ms']}")
	public String kRequestTimeout;

	@Value("#{appconfig['key.deserializer']}")
	public String keyDeserializer;

	@Value("#{appconfig['value.deserializer']}")
	public String valueDeserializer;

	@Value("#{appconfig['kafka.consumer.num']}")
	public Integer kConsumerNum;
	@Value("#{appconfig['kafka.group.id']}")
	public String kafkaGroupId;

	@Value("#{appconfig['kafka.topic']}")
	public String kafkaTopic;


}
