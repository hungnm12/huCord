package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface IAppUserRepository extends JpaRepository<AppUser,Integer> {

    AppUser findByEmail (String email);



}

