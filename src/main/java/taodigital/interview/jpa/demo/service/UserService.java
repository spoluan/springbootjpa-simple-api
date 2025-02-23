package taodigital.interview.jpa.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taodigital.interview.jpa.demo.entity.AppUser;
import taodigital.interview.jpa.demo.entity.Inventory;
import taodigital.interview.jpa.demo.model.request.UserLoginRequest;
import taodigital.interview.jpa.demo.model.request.UserRequest;
import taodigital.interview.jpa.demo.model.response.InventoryBookResponse;
import taodigital.interview.jpa.demo.model.response.InventoryUserResponse;
import taodigital.interview.jpa.demo.model.response.UserResponse;
import taodigital.interview.jpa.demo.repository.AppUserRepository;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ValidationService validationService;

    public String login(UserLoginRequest request) {
        validationService.validate(request);

        appUserRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        });

        return "Success";
    }

    public UserResponse get(UUID id) {

        UserRequest request = new UserRequest();
        request.setId(id);

        validationService.validate(request);

        AppUser user = appUserRepository.findById(request.getId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        });

        return toUserLoginResponse(user);
    }

    private UserResponse toUserLoginResponse(AppUser user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .inventories(user.getInventoryList().stream().map(inventory -> toInventoryUserResponse(inventory)).toList())
                .build();
    }

    private InventoryUserResponse toInventoryUserResponse(Inventory inventory) {
        return InventoryUserResponse.builder()
                .id(inventory.getId())
                .borrowDate(inventory.getBorrowDate())
                .bookId((inventory.getBook() == null) ? null : inventory.getBook().getId())
                .title((inventory.getBook() == null) ? null : inventory.getBook().getTitle())
                .author((inventory.getBook() == null) ? null : inventory.getBook().getAuthor())
                .image((inventory.getBook() == null) ? null : inventory.getBook().getImage())
                .build();
    }

}
