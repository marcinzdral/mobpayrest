package utils;

public class Resource {

    private static Resource singleInstance = null;

    private final String mobileBackendUrl = "https://mobile-backend.test.gneis.io/api/v1";
    private final String mobilePayUrl = "https://mobilepay-mock-stable.test.gneis.io";
    private String orderId;
    private String processId;
    private float presetAmount;
    private float totalAmount;
    private int pumpId;
    private int siteId;
    private boolean stayIn;

    private Resource() {}

    public static Resource getInstance() {
        if (singleInstance == null)
            singleInstance = new Resource();

        return singleInstance;
    }

    public String getMobileBackendUrl() {
        return mobileBackendUrl;
    }

    public String getMobilePayUrl() {
        return mobilePayUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public float getPresetAmount() {
        return presetAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPresetAmount(float presetAmount) {
        this.presetAmount = presetAmount;
    }

    public int getPumpId() {
        return pumpId;
    }

    public void setPumpId(int pumpId) {
        this.pumpId = pumpId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public boolean isStayIn() {
        return stayIn;
    }

    public void setStayIn(boolean stayIn) {
        this.stayIn = stayIn;
    }
}
