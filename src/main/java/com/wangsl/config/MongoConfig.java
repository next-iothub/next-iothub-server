package com.wangsl.config;

import com.wangsl.config.converter.BsonTimestampToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {


	@Override
	protected String getDatabaseName() {
		return "mqtt";
	}

	@Bean
	@Override
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = Arrays.asList(
			new BsonTimestampToDateConverter()
		);
		return new MongoCustomConversions(converters);
	}
}
