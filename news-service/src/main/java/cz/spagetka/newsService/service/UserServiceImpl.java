package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User getUser(String authId) {
        return this.userRepository.findByAuthId(authId)
                .orElseGet(()->this.createUser(authId));
    }

    private User createUser(String authId){
        User user = User.builder()
                .authId(authId)
                .build();

        return this.userRepository.save(user);
    }
}
