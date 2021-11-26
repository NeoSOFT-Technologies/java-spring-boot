package com.springboot.rest.config;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapLoader;
import org.redisson.api.map.MapWriter;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.infrastructure.adaptor.WriteThroughCacheEntityJPAAdaptor;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;

@Configuration
public class RedissonConfigWriteThrough implements InitializingBean {

  //  protected SampleEntityDTO sampleEntityDTO ;

	private final String CACHE_NAME="users";

    private RedissonClient redissonClient;

    Logger logger = LoggerFactory.getLogger(RedissonConfigWriteThrough.class);

    @Autowired
    private WriteThroughCacheEntityJPAAdaptor userJPAAdapter;
    
    
    

    @Bean
    public RMapCache<Long, WriteThroughCacheEntity> userRMapCache() {
        final RMapCache<Long, WriteThroughCacheEntity> userRMapCache = redissonClient.getMapCache(CACHE_NAME,MapOptions.<Long, WriteThroughCacheEntity>defaults()
                .writer(getMapWriter()).loader(getMapLoader())
                .writeMode(MapOptions.WriteMode.WRITE_THROUGH));
        return userRMapCache;
    }

    @Bean
    public RedissonClient redissonClient() {
        final Config config = new Config();
        config.useMasterSlaveServers().setDnsMonitoringInterval(200000).setMasterAddress("redis://localhost:6379");
        return Redisson.create(config);
    }

    private MapWriter<Long, WriteThroughCacheEntity> getMapWriter() {
        return new MapWriter<Long, WriteThroughCacheEntity>() {

            @Override
            public void write(final Map<Long, WriteThroughCacheEntity> map) {
                logger.info("*********************** write");
                map.forEach( (k, v) -> {
                	
                	WriteThroughCacheEntityDTO sampleEntityDTO=new WriteThroughCacheEntityDTO();
                	sampleEntityDTO.setId(v.getId());
                	sampleEntityDTO.setAge(v.getAge());
                	sampleEntityDTO.setName(v.getName());
                	sampleEntityDTO.setPassword(v.getPassword());
                	sampleEntityDTO.setPhone(v.getPhone());
                	
                    userJPAAdapter.save(sampleEntityDTO);
                });
            }

            @Override
            public void delete(Collection<Long> keys) {
                logger.info("*********************** delete");
                keys.stream().forEach(e -> {
                    userJPAAdapter.deleteById(e);
                    
                });
              
            }
        };
    }

    private MapLoader<Long, WriteThroughCacheEntity> getMapLoader() {
        return new MapLoader<Long, WriteThroughCacheEntity>() {

            @Override
            public Iterable<Long> loadAllKeys() {

                logger.info("*********************** Iterable<Long> loadAllKeys()");
                List<Long> list = new ArrayList<Long>(){};

                list.add(1L);
                list.add(2L);
                list.add(3L);
                list.add(4L);
                list.add(33L);
                list.add(34L);

                return list;
            }

            @Override
            public WriteThroughCacheEntity load(Long key) {
                logger.info("*********************** User load(Long key)");
//                return userRepository.getById(key);
                
                return userJPAAdapter.findById(key).get();
            }

        };
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        final Config config = new Config();
        config.useMasterSlaveServers().setDnsMonitoringInterval(200000).setMasterAddress("redis://localhost:6379");
        this.redissonClient = Redisson.create(config);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//        return (builder) -> builder
//                .withCacheConfiguration("users",
//                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));
//    }
//
//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(15))
//                .disableCachingNullValues()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }
//    
//    
    
    
    
    
    
    
    
    
    
    

}
