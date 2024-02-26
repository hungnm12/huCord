package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends JpaRepository<File,Long> {
}
