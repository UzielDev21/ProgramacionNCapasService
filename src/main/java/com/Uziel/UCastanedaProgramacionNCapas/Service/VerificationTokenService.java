package com.Uziel.UCastanedaProgramacionNCapas.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    private final Map<String, Integer> verificationTokens = new ConcurrentHashMap<>();
    private final Map<String, Long> tokenExpirations = new ConcurrentHashMap<>();
    private final Set<String> usedTokens = ConcurrentHashMap.newKeySet();
    private static final long EXPIRATION_TIME = 15 * 60 * 1000;

    public String generateToken(int idUsuario) {
        String tokenEmail = UUID.randomUUID().toString();
        long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;

        verificationTokens.put(tokenEmail, idUsuario);
        tokenExpirations.put(tokenEmail, expirationTime);

        return tokenEmail;
    }

    private void cleanupToken(String tokenEmail) {
        verificationTokens.remove(tokenEmail);
        tokenExpirations.remove(tokenEmail);
    }

    public Integer getUserIdFromToken(String tokenEmail) {
        return verificationTokens.get(tokenEmail);
    }

    public void markTokenAsUsed(String tokenEmail) {
        usedTokens.add(tokenEmail);
        cleanupToken(tokenEmail);
    }

    public boolean isTokenValid(String tokenEmail) {

        if (tokenEmail == null) {
            return false;
        }

        if (usedTokens.contains(tokenEmail)) {
            return false;
        }

        Long expiration = tokenExpirations.get(tokenEmail);
        if (expiration == null || expiration < System.currentTimeMillis()) {
            cleanupToken(tokenEmail);
            return false;
        }

        return verificationTokens.containsKey(tokenEmail);
    }

}
