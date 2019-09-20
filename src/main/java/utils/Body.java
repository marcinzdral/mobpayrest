package utils;

import java.util.*;

public class Body extends BaseCore {

    protected Map<String, Object> getFuelBody_createOrder_DK() {
        Map<String, Object> body = new HashMap<>();
        body.put("siteId", 10555);
        body.put("mobileDeviceId", "1234");
        body.put("pspSelector", "MOBILE_PAY");
        body.put("pumpId", 2);

        return body;
    }

    protected Map<String, Object> getFuelBody_reserveFunds() {
        Map<String, Object> body = new HashMap<>();
        body.put("Amount", resource.getTotalAmount());

        return body;
    }

    protected Map<String, Object> getCarwashBody_createOrder_DK(String stayInOut) {
        Map<String, Object> body = new HashMap<>();
        body.put("userLocationDuringCarwash", stayInOut);
        body.put("mobileUser", "1234");
        body.put("pspSelector", "MOBILE_PAY");
        body.put("siteId", 11185);
        body.put("articleNo", "1113585");

        return body;
    }


    protected Map<String, Object> getCarwashBody_reserveFunds() {
        Map<String, Object> body = new HashMap<>();
        body.put("Amount", resource.getTotalAmount());

        return body;
    }

    protected Map<String, Object> getCarwashBody_authorizeCarwash() {
        Map<String, Object> body = new HashMap<>();
        body.put("washpointNo", 1);

        return body;
    }
}
