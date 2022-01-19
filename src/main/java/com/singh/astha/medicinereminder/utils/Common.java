package com.singh.astha.medicinereminder.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Common {
    public static OffsetDateTime getOffsetDateTime() {
        Date date = new Date();
        OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
        return offsetDateTime;
    }
}
