package kr.co.smartcity.admin.common.subscription;

public class NotificationVo {
    private String format;
    private EndpointVo endpoint;
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public EndpointVo getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(EndpointVo endpoint) {
        this.endpoint = endpoint;
    }
}