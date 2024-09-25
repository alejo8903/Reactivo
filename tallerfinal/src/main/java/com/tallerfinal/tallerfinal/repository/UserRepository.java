package com.tallerfinal.tallerfinal.repository;


import com.tallerfinal.tallerfinal.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends ReactiveMongoRepository<User, String> {


}


