package com.example.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.api.domain.ViaCepDTO;


@FeignClient(name = "viaCepFeignService", url = "https://viacep.com.br/ws")
public interface ViaCepFeignService {
	
	
	@GetMapping("/{cep}/json")
	public ViaCepDTO consultarCep(@PathVariable String cep);

}
