package com.example.taskcrud.entity.appuser;


import com.example.taskcrud.entity.AppUserRole;
import com.example.taskcrud.entity.Channel;
import com.example.taskcrud.entity.Token;
import jakarta.persistence.*;
import lombok.*;
//import org.hibernate.mapping.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.channels.Channels;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    //    Thuộc tính EnumType.STRING được truyền vào Annotation để chỉ định rằng giá trị của enum sẽ được lưu trữ dưới dạng chuỗi (string) trong cơ sở dữ liệu.
    @Enumerated(EnumType.STRING)
    private AppUserRole appUSerRole;

    @ManyToMany(mappedBy = "users")
    private List<Channel> channels = new ArrayList<>();



    public List<Channel> getChannels() {
        return channels;
    }

    public void addChannel(Channel channel){
        if(!channels.contains(channel)){
            channels.add(channel);
            channel.getUsers().add(this);
        }
    }

    public void removeChannel(Channel channel){
        if ((channels.contains(channel))){
            channels.remove(channel);
            channel.getUsers().remove(this);
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority>grantedAuthorities = List.of(new SimpleGrantedAuthority(appUSerRole.name()));
        return grantedAuthorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPassWord(String newPassword){
        this.password = newPassword;
    }
    public void setFullname(String newFullname){
        this.fullName = newFullname;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //    Phương thức isAccountNonLocked được sử dụng để kiểm tra trạng thái khóa của tài khoản người dùng.
//    Nếu tài khoản người dùng bị khóa, thì người dùng không thể đăng nhập vào hệ thống.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //Phương thức này trả về một giá trị boolean xác định xem các thông tin xác thực của tài khoản người dùng có hết hạn hay không.
//
//Nếu các thông tin xác thực của tài khoản người dùng không hết hạn, phương thức trả về giá trị true, ngược lại, nếu các thông tin xác thực của tài khoản người dùng đã hết hạn, phương thức trả về giá trị false.
//
//Thông thường, thông tin xác thực bao gồm mật khẩu của người dùng hoặc các thông tin xác thực khác được sử dụng để xác thực người dùng. Các thông tin xác thực này có thể được yêu cầu để được cập nhật định kỳ để đảm bảo tính bảo mật của hệ thống.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
