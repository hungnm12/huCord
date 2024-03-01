package com.example.taskcrud.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUsersInChannelReq {
    String token;
    String name;
}
