package net.brainified.domain.authentication;

import net.brainified.db.UserDetailsDocument;

interface UserConverter {

  UserDetailsDocument createDocument(LoginData loginData);

  User createUser(UserDetailsDocument savedDocument);

}
