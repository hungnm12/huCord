package com.example.taskcrud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name = "user_channel",
            joinColumns = @JoinColumn(name = "channel_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<AppUser> users = new ArrayList<>();

//    @OneToMany(mappedBy = "channel")
//    private List<ChatMessage> chatMessages = new ArrayList<>();

    public List<AppUser> getUsers() {
        return users;
    }

    public void addUser(AppUser user) {
        if (!users.contains(user)) {
            users.add(user);
            user.getChannels().add(this);
        }

    }

    public void deleteUser(AppUser user) {
        if (users.contains(user)) {
            if (user.getAppUSerRole() == AppUserRole.ADMIN) {
                throw new UnsupportedOperationException("Deleting admin users is not allowed");
            } else {
                users.remove(user);
                user.getChannels().remove(this);
            }
        }
    }
}
