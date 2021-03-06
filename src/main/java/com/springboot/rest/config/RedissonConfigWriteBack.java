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

import com.springboot.rest.domain.dto.WriteBackCacheEntityDTO;
import com.springboot.rest.infrastructure.adaptor.WriteBackCacheEntityJPAAdaptor;
import com.springboot.rest.infrastructure.entity.SampleEntity;

@Configuration
public class RedissonConfigWriteBack implements InitializingBean {

  //  protected SampleEntityDTO sampleEntityDTO ;

	private final String CACHE_NAME="users";

    private RedissonClient redissonClient;

    Logger logger = LoggerFactory.getLogger(RedissonConfigWriteBack.class);

    @Autowired
    private WriteBackCacheEntityJPAAdaptor userJPAAdapter;
    
    
    

    @Bean
    public RMapCache<Long, SampleEntity> userRMapCache() {
        final RMapCache<Long, SampleEntity> userRMapCache = redissonClient.getMapCache(CACHE_NAME,MapOptions.<Long, SampleEntity>defaults()
                .writer(getMapWriter()).loader(getMapLoader())
                .writeMode(MapOptions.WriteMode.WRITE_BEHIND).writeBehindDelay(30000)//in milliseconds
                .writeBehindBatchSize(5));
        return userRMapCache;
    }

    @Bean
    public RedissonClient redissonClient() {
        final Config config = new Config();
        config.useMasterSlaveServers().setDnsMonitoringInterval(200000).setMasterAddress("redis://localhost:6379");
        return Redisson.create(config);
    }

    private MapWriter<Long, SampleEntity> getMapWriter() {
        return new MapWriter<Long, SampleEntity>() {

            @Override
            public void write(final Map<Long, SampleEntity> map) {
                logger.info("*********************** write");
                map.forEach( (k, v) -> {
                	
                	WriteBackCacheEntityDTO sampleEntityDTO=new WriteBackCacheEntityDTO();
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

    private MapLoader<Long, SampleEntity> getMapLoader() {
        return new MapLoader<Long, SampleEntity>() {

            @Override
            public Iterable<Long> loadAllKeys() {

                logger.info("*********************** Iterable<Long> loadAllKeys()");
                List<Long> list = new ArrayList<Long>(){

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;};

                list.add(1L);
                list.add(2L);
                list.add(3L);
                list.add(4L);
                list.add(33L);
                list.add(34L);

                return list;
            }

            @Override
            public SampleEntity load(Long key) {
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

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    

}
