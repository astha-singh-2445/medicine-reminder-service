package com.singh.astha.medicinereminder.services;


import com.singh.astha.medicinereminder.internal.JwtPayload;

public interface JwtService {

    JwtPayload verifyAndDecodeToken(String authHeader);
}
