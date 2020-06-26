package org.example.distribution.service;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.example.distribution.domain.Distribution;
import org.example.distribution.exception.LookupException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LookupService {

    private void validateOwnerForLookup(int userKey, String roomKey, Distribution distribution) {
        if (userKey != distribution.getUserKey() || !roomKey.equals(distribution.getRoomKey())) {
            throw new LookupException("Invalid userKey or roomKey. lookup is only allowed to owner");
        }
    }

    private void validateExpiredDateForLookup(Distribution distribution) {
        long createdTime = distribution.getCreatedAt();
        long currentTime = System.currentTimeMillis();
        long expiredTime = DateUtils.addDays(new Date(createdTime), 7).getTime();

        if (expiredTime < currentTime) {
            throw new LookupException("Invalid lookup Time. lookup is only 7 days available");
        }
    }

    public void validate(int userKey, String roomKey, Distribution distribution) {
        validateOwnerForLookup(userKey, roomKey, distribution);
        validateExpiredDateForLookup(distribution);
    }

}
