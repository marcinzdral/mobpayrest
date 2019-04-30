package utils;

public enum FuelStatus {

    CREATED("CREATED"),
    RESERVED("RESERVED"),
    SERVICE_READY("SERVICE_READY"),
    SERVICE_IN_USE("SERVICE_IN_USE"),
    SERVICE_FINANCED("SERVICE_FINANCED");

    private String status;

    FuelStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
