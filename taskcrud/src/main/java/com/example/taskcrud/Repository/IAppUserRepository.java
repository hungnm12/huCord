package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//đảm bảo rằng trong quá trình thực thi phương thức, không có thay đổi nào được thực hiện trên cơ sở dữ liệu.
// Nếu bất kỳ thay đổi nào được thực hiện, Spring sẽ ném ra một ngoại lệ.
//@Transactional(readOnly = true)
public interface IAppUserRepository extends JpaRepository<AppUser,Integer> {

    Optional<AppUser>findByEmail(String email);


}

