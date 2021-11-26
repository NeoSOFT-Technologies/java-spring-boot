package com.springboot.rest.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.redisson.Redisson;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapLoader;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.springboot.rest.infrastructure.entity.SampleEntity2;
import com.springboot.rest.infrastructure.repository.SampleEntity2Repository;
@Configuration
@Transactional
public class RedissonConfigReadThrough implements InitializingBean{

	private final String CACHE_NAME = "sampleEntity";
	//private SampleEntity2Mapper sampleMapper;
	
	private RedissonClient reddissonClient;
	
	@Autowired
	private SampleEntity2Repository sampleEntity2JpaAdapter;
	
	Logger logger = LoggerFactory.getLogger(RedissonConfigReadThrough.class);
	

	@Bean
	public RedisCacheConfiguration CacheConfigurationException() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(40))
				.disableCachingNullValues()
				.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
	
	@Bean
	public RMapCache<Long, SampleEntity2> sampleRMapCache(){
		return reddissonClient.getMapCache(CACHE_NAME,MapOptions.<Long, SampleEntity2>defaults()
				.loader(getMapLoader()));
	}

	@Bean
	public RedissonClient redissonClient() {
		final Config config = new Config();
		config.useMasterSlaveServers().setDnsMonitoringInterval(200000).setMasterAddress("redis://localhost:6379");
		return Redisson.create(config);
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		final Config config = new Config();
		config.useMasterSlaveServers().setDnsMonitoringInterval(200000).setMasterAddress("redis://localhost:6379");
		this.reddissonClient = Redisson.create(config);
		
	}
	
//	private MapWriter<Long, SampleEntity2> getMapWriter() {
//        return new MapWriter<Long, SampleEntity2>() {
//
//            @Override
//            public void write(final Map<Long, SampleEntity2> map) {
//                logger.info("*********************** write");
//                map.forEach( (k, v) -> {
//                    sampleEntity2JpaAdapter.save(sampleMapper.entityToDto(v));
//                });
//            }
//
//            @Override
//            public void delete(Collection<Long> keys) {
//                logger.info("*********************** delete");
//                keys.stream().forEach(e -> {
//                    sampleEntity2JpaAdapter.deleteById(e);
//                });
//            }
//        };
//    }
	private MapLoader<Long, SampleEntity2> getMapLoader(){
		return new MapLoader<Long, SampleEntity2>() {

			@Override
			public SampleEntity2 load(Long key) {
				logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ SampleEntity2 load(Long key)");
				
				return sampleEntity2JpaAdapter.findById(key).get();
			}

			@Override
			public Iterable<Long> loadAllKeys() {
				logger.info("############################## Itrerable<Long> loadAllKeys()");
				List<Long> list = new ArrayList<Long>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;};
				List<SampleEntity2> list1 = sampleEntity2JpaAdapter.findAll();
				for(SampleEntity2 sample : list1) {
					list.add(sample.getId());
				}
				return list;
			}
		};
	}

}
