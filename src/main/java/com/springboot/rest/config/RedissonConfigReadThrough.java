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

import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import com.springboot.rest.infrastructure.repository.ReadThroughEntityRepository;
@Configuration
@Transactional
public class RedissonConfigReadThrough implements InitializingBean{

	private final String CACHE_NAME = "sampleEntity";
	//private ReadThroughEntityMapper sampleMapper;
	
	private RedissonClient reddissonClient;
	
	@Autowired
	private ReadThroughEntityRepository sampleEntity2JpaAdapter;
	
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
	public RMapCache<Long, ReadThroughEntity> sampleRMapCache(){
		return reddissonClient.getMapCache(CACHE_NAME,MapOptions.<Long, ReadThroughEntity>defaults()
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
	
//	private MapWriter<Long, ReadThroughEntity> getMapWriter() {
//        return new MapWriter<Long, ReadThroughEntity>() {
//
//            @Override
//            public void write(final Map<Long, ReadThroughEntity> map) {
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
	private MapLoader<Long, ReadThroughEntity> getMapLoader(){
		return new MapLoader<Long, ReadThroughEntity>() {

			@Override
			public ReadThroughEntity load(Long key) {
				logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ReadThroughEntity load(Long key)");
				
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
				List<ReadThroughEntity> list1 = sampleEntity2JpaAdapter.findAll();
				for(ReadThroughEntity sample : list1) {
					list.add(sample.getId());
				}
				return list;
			}
		};
	}

}
