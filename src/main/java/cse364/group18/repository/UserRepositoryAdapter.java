package cse364.group18.repository;

import cse364.group18.exception.NotFoundException;
import cse364.group18.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public class UserRepositoryAdapter {
    UserRepository userRepository;

    List<User> userList;
    HashMap<Long, User> userMap = new HashMap<>();

    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userList = userRepository.findAll();
    }

    public List<User> findAll() {
        if (this.userList.isEmpty()){
            this.userList = userRepository.findAll();
            for (User user : this.userList){
                this.userMap.putIfAbsent(user.getUserId(), user);
            }
        }
        return this.userList;
    }

    public User findByUserId(Long userId) throws NotFoundException {
        if (this.userMap.isEmpty())
            findAll();

        if (this.userMap.containsKey(userId)) {
            return this.userMap.get(userId);
        } else {
            throw new NotFoundException("Not found user id: ", userId.toString());
        }
    }
}

