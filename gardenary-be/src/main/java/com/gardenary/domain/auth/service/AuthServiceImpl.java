package com.gardenary.domain.auth.service;


import com.gardenary.domain.auth.dto.response.AuthResponseDto;
import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.domain.profile.repository.ProfileRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.config.security.JwtProvider;
import com.gardenary.global.error.exception.UserApiException;
import com.gardenary.global.error.model.ProfileErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ProfileRepository profileRepository;

    private final JwtProvider jwtProvider;

    @Override
    public AuthResponseDto signIn(User user) {
        Profile profile = profileRepository.findByUser(user);

        if (profile == null) {
            throw new UserApiException(ProfileErrorCode.PROFILE_NOT_FOUND);
        }

        String nickname = profile.getNickname();
        String accessToken = jwtProvider.generateAccessToken(user.getKakaoId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getKakaoId());

        return new AuthResponseDto(accessToken, refreshToken, nickname);
    }
}
