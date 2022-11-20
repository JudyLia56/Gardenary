package com.gardenary.domain.avatar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvatarListResponseDto {

    private boolean flag;
    private List<AvatarResponseDto> avatarResponseDtoList;

}
