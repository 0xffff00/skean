package party.threebody.s4g.conf.spring;

import java.util.Arrays;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JDK ConcurrentMap-based Cache, by using Spring's SimpleCacheManager<br>
 * alternatives: GuavaCache, CaffeineCache, EhCache
 * 
 * @author hzk
 * @since 2017-08-05
 */
@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public SimpleCacheManager cacheManager() {
		SimpleCacheManager cm = new SimpleCacheManager();
		ConcurrentMapCache cb1 = new ConcurrentMapCache("aaa");
		cm.setCaches(Arrays.asList(cb1));
		return cm;

	}

}