package com.example.userserver.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Transactional
	public UserInfo createUser(UserRequest request) {
		String hashedPassword = passwordEncoder.encode(request.getPlainPassword());
		User user = new User(request.getUsername(), request.getEmail(), hashedPassword);
		if (userRepository.findByUsername(request.getUsername()) != null){
			throw new RuntimeException("Username duplicated");
		}
		return new UserInfo(userRepository.save(user));
	}

	public UserInfo getUser(int userId) {
		User user = userRepository.findById(userId).orElse(null);
		if(user == null)
			return null;
		return new UserInfo(user);
	}

	public UserInfo getUserByName(String name){
		User user = userRepository.findByUsername(name);
		if(user == null)
			return null;
		return new UserInfo(user);
	}

	// TODO : 로그인, 회원가입 dto 분리하기
	public UserInfo signIn(UserRequest signInRequest) {
		User user = null;
		//입력에서 아이디 검사
		if(signInRequest.getUsername() != null){
			user = userRepository.findByUsername(signInRequest.getUsername());
		}
		//아이디가 존제하지 않음
		if(user == null){
			return null;
		}

		boolean isPasswordMatch = passwordEncoder.matches(signInRequest.getPlainPassword(), user.getPassword());
		//로그인 성콩
		if(isPasswordMatch){
			return new UserInfo(user);
		}
		return null;
	}
}
