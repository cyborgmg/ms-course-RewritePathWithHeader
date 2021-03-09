package com.devsuperior.hroauth.services;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignclientes.UserFeignCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserFeignCliente userFeignCliente;

    public User findByEmail(String email){

        User user = userFeignCliente.findByEmail(email).getBody();

        if(user==null){
            log.error("mail não encontrado = {}",email);
            throw new IllegalArgumentException("email não encontrado");
        }

        log.info("mail encontrado = {}",email);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userFeignCliente.findByEmail(email).getBody();
        if(user==null){
            log.error("mail não encontrado = {}",email);
            throw new UsernameNotFoundException("email não encontrado");
        }
        log.info("mail encontrado = {}",email);
        return user;
    }
}
