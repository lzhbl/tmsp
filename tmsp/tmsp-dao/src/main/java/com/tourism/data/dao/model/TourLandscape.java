package com.tourism.data.dao.model;

import java.io.Serializable;
import java.util.Date;

public class TourLandscape implements Serializable {
    /**
     * ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     * 景点名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 景点位置
     *
     * @mbg.generated
     */
    private String location;

    /**
     * 经度
     *
     * @mbg.generated
     */
    private Double longitude;

    /**
     * 纬度
     *
     * @mbg.generated
     */
    private Double latitude;

    /**
     * 门票价钱
     *
     * @mbg.generated
     */
    private String price;

    /**
     * 开发时间
     *
     * @mbg.generated
     */
    private Date devtime;

    /**
     * 景点类别
     *
     * @mbg.generated
     */
    private String category;

    /**
     * 景点介绍
     *
     * @mbg.generated
     */
    private String introduce;

    /**
     * 图片
     *
     * @mbg.generated
     */
    private String pictures;

    /**
     * 视频
     *
     * @mbg.generated
     */
    private String videos;

    /**
     * 状态(0:正常,1:锁定)
     *
     * @mbg.generated
     */
    private Byte locked;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Long ctime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getDevtime() {
        return devtime;
    }

    public void setDevtime(Date devtime) {
        this.devtime = devtime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public Byte getLocked() {
        return locked;
    }

    public void setLocked(Byte locked) {
        this.locked = locked;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", location=").append(location);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", price=").append(price);
        sb.append(", devtime=").append(devtime);
        sb.append(", category=").append(category);
        sb.append(", introduce=").append(introduce);
        sb.append(", pictures=").append(pictures);
        sb.append(", videos=").append(videos);
        sb.append(", locked=").append(locked);
        sb.append(", ctime=").append(ctime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TourLandscape other = (TourLandscape) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDevtime() == null ? other.getDevtime() == null : this.getDevtime().equals(other.getDevtime()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getIntroduce() == null ? other.getIntroduce() == null : this.getIntroduce().equals(other.getIntroduce()))
            && (this.getPictures() == null ? other.getPictures() == null : this.getPictures().equals(other.getPictures()))
            && (this.getVideos() == null ? other.getVideos() == null : this.getVideos().equals(other.getVideos()))
            && (this.getLocked() == null ? other.getLocked() == null : this.getLocked().equals(other.getLocked()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDevtime() == null) ? 0 : getDevtime().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getIntroduce() == null) ? 0 : getIntroduce().hashCode());
        result = prime * result + ((getPictures() == null) ? 0 : getPictures().hashCode());
        result = prime * result + ((getVideos() == null) ? 0 : getVideos().hashCode());
        result = prime * result + ((getLocked() == null) ? 0 : getLocked().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        return result;
    }
}