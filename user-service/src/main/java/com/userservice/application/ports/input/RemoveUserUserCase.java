package com.userservice.application.ports.input;

import com.userservice.domain.user.User;

public interface RemoveUserUserCase {
    void removeUser(User user);
}
