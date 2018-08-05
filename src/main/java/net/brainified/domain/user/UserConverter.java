package net.brainified.domain.user;

import net.brainified.db.UserDetailsDocument;

interface UserConverter {

  UserDetailsDocument createDocument(AddUserRequest addUserRequest);

  User createUser(UserDetailsDocument savedDocument);

}
