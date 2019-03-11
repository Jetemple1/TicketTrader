package io.pp1.logos;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogoController {
	
	@Autowired
	private LogoRepository logoRepository;
	
	@GetMapping(value = "/logos")
	public LogoService getAll(){
		
		return new LogoService(logoRepository.findAll());

	}
	
	
	@GetMapping(value = "/logos/{id}")
	public Optional<Logo> getUrl(@PathVariable Integer id) {
		
		return logoRepository.findById(id);
	}

	@PostMapping(value = "/logos")
	public void post(@RequestBody final Logo logo){
		logoRepository.save(logo);
	}
	
}
