package com.ucr.reco.service;

import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.LoginDTO;
import com.ucr.reco.model.dto.UserDTO;
import com.ucr.reco.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserJpaRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    /*
        public User add(User user){
            if(repository.existsByEmail(user.getEmail())){
                return null;
            }
            return repository.save(user);
        }
    */

    //Add
    /*
    public User add(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            return null;
        } else {
            if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
                return null;
            }
        }
        return repository.save(user);
        //return "Proceso exitoso";
    }*/

    public User add(UserDTO user) {

        if (repository.existsByEmail(user.getEmail())) {
            return null;
        } else {
            if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
                return null;
            }
        }
        User userTemp = new User();//se crea un nuevo usuario vacío
        userTemp.setName(user.getName());
        userTemp.setEmail(user.getEmail());
        userTemp.setPassword(user.getPassword());
        userTemp.setRole(user.getRole());
        //conforme se le agregan los atributos que tiene el DTO se rellena la información del usuario vacío
        return repository.save(userTemp);//por último se retorna el usuario que se creó, dicho usuario contiene la información del usuario temporal
        //return "Proceso exitoso";
    }

    public User login(LoginDTO user) {
        User userExists = repository.getByEmail(user.getEmail());

        if (userExists != null && userExists.getPassword().equals(user.getPassword())) {
            return userExists;
        }

        return null;
    }

    public User getById(Integer id) {
        User user = repository.findById(id.intValue());
        if (user != null) {
            return user;
        }
        /*if (repository.existsById(id)) {
            return repository.findById(id).get();
        }*/
        return null;
    }

    public User update(User user) {
        User userExits = repository.getByEmail(user.getEmail());//se vrifica que sí exista por el email, en algunos casos ID
        //y se registra en el nuevo usuario temporal
        if (userExits != null) {//luego se verifica que NINGUN atributo esté vacío
            if (user.getName() != null) {
                userExits.setName(user.getName());
            }
            if (user.getPassword() != null) {
                userExits.setPassword(user.getPassword());
            }
            if (user.getRole() != null) {
                userExits.setRole(user.getRole());
            }

        } else {
            return null;
        }
        return repository.save(userExits);
    }

    public User delete(Integer id) {
        Optional<User> userExits = repository.findById(id);
        if (userExits.isPresent()) {
            repository.deleteById(id);
            return (User) userExits.get();
        } else {
            return null;
        }
    }


    public User changePassword(String email, String newPassword) {
        User userExits = repository.getByEmail(email);
        if (userExits != null) {
            userExits.setPassword(newPassword);
            return repository.save(userExits);
        } else {
            return null;
        }
    }

    public User getByEmail(String email) {
        /*
        User user = repository.getByEmail(email);
        if (user != null) {
            return user;
        }
        if (repository.existsById(id)) {
            return repository.findById(id).get();
        }*/

        return repository.getByEmail(email);
    }

}
