package ar.edu.utn.dds.repo;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import ar.edu.utn.dds.modeloWeb.Resultado;

public class ResultadoRepo {
	private RedisTemplate<String, Resultado> redisTemplate;
	private static String RESULTADO_KEY = "Resultado";

	public RedisTemplate<String, Resultado> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Resultado> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(Resultado resultado) {
		this.redisTemplate.opsForHash().put(RESULTADO_KEY, resultado.getId(), resultado);
	}

	public Object find(String id) {
		return this.redisTemplate.opsForHash().get(RESULTADO_KEY, id);
	}

	public Map<Object, Object> findAll() {
		return this.redisTemplate.opsForHash().entries(RESULTADO_KEY);
	}

	public void delete(String id) {
		this.redisTemplate.opsForHash().delete(RESULTADO_KEY, id);

	}
}
