package utils;

public enum CarwashStatus {

    CREATED("CREATED"),
    RESERVED("RESERVED"),
    WAITING_FOR_CUSTOMER("WAITING_FOR_CUSTOMER"),
    SERVICE_READY("SERVICE_READY"),
    SERVICE_IN_USE("SERVICE_IN_USE"),
    SERVICE_COMPLETED("SERVICE_COMPLETED");

    private String status;

    CarwashStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
