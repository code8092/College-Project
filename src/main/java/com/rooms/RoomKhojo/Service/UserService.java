package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.Entity.User;
import com.rooms.RoomKhojo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(long id) throws Exception {
        return userRepository.findById(id).
                orElseThrow(() -> new Exception("Customer not found with id: " + id));

    }

    public User updateUser(long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            return null;
        }

        User existingUser = optionalUser.get();
        existingUser.setName(userDetails.getName());
        existingUser.setName(userDetails.getPhoneNo());

        return userRepository.save(existingUser);
    }

    public boolean deleteUser(long id) {

        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
