package io.pp1.users;


import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import antlr.StringUtils;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> getAll(){
		
		List<User> users = userRepository.findAll();
		return users;
	}
	

	@GetMapping("/users/{id}")
	public boolean userExist(@PathVariable Integer id) {
		
		
		return userRepository.existsById(id);
	}
	
	@PostMapping(value = "/users/login")
	public User userLogin(@RequestBody final User user) {
		
		PasswordEncoder passEnc = passwordEncoder();
		
		String netid = user.getNet_Id();
		String temp = userRepository.getPassByNetID(netid);
		
//		if((user.getPassword()).equals(temp)) {
		if(passEnc.matches(user.getPassword(), temp)) {
			User OurUser = userRepository.getUserByPass(temp);
			OurUser.setPassword("true");
			return OurUser;
			
		}else {
			
			user.setPassword("false");
		}
		
		return user;
	}
	
	@PostMapping(value = "/users/checkuser")
	public User checkNetId(@RequestBody final User users) {
		
		return userRepository.existByNetID(users.getNet_Id());
	}
	
	private int checkPass(String pass) {
		
		if(pass.length()<8) {
			
			return 1;
			
		}    
		
		Pattern p = Pattern.compile( "[0-9]" );
	    Matcher m = p.matcher(pass);

		if(!m.find()) {
			
			return 1;
		}
		
		return 0;
	}

	
	@PostMapping(value = "/users")
	public StringResponse post(@RequestBody final User users){
		//init pass encoder
		PasswordEncoder passEnc = passwordEncoder();
		
		//make sure netid isn't already in use,
		if(userRepository.existByNetID(users.getNet_Id()) != null){
			return new StringResponse("invalid2");
		}else {
		
			//check if its an ISU email
			String givenNetId = users.getNet_Id();
			if(givenNetId.indexOf('@') >= 0) {
				
				String[] arr= givenNetId.split("@");
				
		
				if(!(arr[arr.length-1].equals("iastate.edu"))){
					
					return new StringResponse("invalid1");
					
					//must be 8 char and alphanumeric
				}else if(checkPass(users.getPassword())==1){
					
					return new StringResponse("invalid3");
				}
			}
			
			
			users.setPassword(passEnc.encode(users.getPassword()));
			userRepository.save(users);
		}
		
		return new StringResponse("Valid");
	}

}
