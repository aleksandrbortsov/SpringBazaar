package com.springbazaar.service;

import com.springbazaar.domain.FullName;
import com.springbazaar.domain.Person;
import com.springbazaar.domain.Role;
import com.springbazaar.domain.User;
import com.springbazaar.domain.type.RoleType;
import com.springbazaar.repository.PersonRepository;
import com.springbazaar.repository.RoleRepository;
import com.springbazaar.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PersonRepository personRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
    }


    @Override
    public User saveOrUpdate(User userForm, Person personForm, List<String> roleForm) {
        LOGGER.debug("addNewUser start");
        Set<Role> roles = new HashSet<>();
        for (String roleItem : roleForm) {
            Role userRole = roleRepository.findByRoleType(RoleType.findByStringType(StringUtils.upperCase(roleItem)));
            if (userRole == null) {
                userRole = new Role(RoleType.findByStringType(StringUtils.upperCase(roleItem)));
            }
            roles.add(userRole);
        }
        FullName personFullName = new FullName(personForm.getFullName().getFirstName(),
                personForm.getFullName().getMiddleName(),
                personForm.getFullName().getLastName());
        Person newPerson = new Person(personFullName);

        User newUser = new User(userForm.getUsername(), userForm.getPassword());

        newPerson.setUser(newUser);
        newPerson.setEmail(userForm.getUsername());
        newUser.setPerson(newPerson);
        personRepository.save(newPerson);
        roleRepository.save(roles);
        newUser.setRoles(roles);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User saveOrUpdate(User user) {
        return null;
    }

    @Override
    public User getUserById(BigInteger id) {
        return userRepository.findOne(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
