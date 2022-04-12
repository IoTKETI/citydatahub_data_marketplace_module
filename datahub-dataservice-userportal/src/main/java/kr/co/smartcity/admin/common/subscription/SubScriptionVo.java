package kr.co.smartcity.admin.common.subscription;

import java.util.ArrayList;
import java.util.List;
public class SubScriptionVo {
    private String type;
    private List<EntityVo> entities = new ArrayList<EntityVo>();
    private List<String> datasetIds = new ArrayList<String>();
    private NotificationVo notification;
    private String expires;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<EntityVo> getEntities() {
        return entities;
    }
    public void setEntities(List<EntityVo> entities) {
        this.entities = entities;
    }
    public List<String> getDatasetIds() {
        return datasetIds;
    }
    public void setDatasetIds(List<String> datasetIds) {
        this.datasetIds = datasetIds;
    }
    public NotificationVo getNotification() {
        return notification;
    }
    public void setNotification(NotificationVo notification) {
        this.notification = notification;
    }
    public String getExpires() {
        return expires;
    }
    public void setExpires(String expires) {
        this.expires = expires;
    }
}