package com.ike.commonutils.net.retrofitnetutils.model;

import java.util.List;

/**
 * Created by dell on 2017/5/20.
 */

public class model {


    /**
     * list : [{"birthday":722275200,"content":"测试","dataId":12205,"faceUri":"201703/20/BL1410101351555090.JPEG","geoCode":"wm3ytdx8","geoLat":30.68758,"geoLng":103.963233,"geoName":"四川省·成都市","nAskFor":1,"nComment":0,"nPraise":11,"name":"carry155","official":0,"photos":"201703/13/BL1410101514563970.JPEG","sex":0,"status":40,"timeIn":1489389380,"title":"测试收到并感谢","userId":41010},{"birthday":804528000,"content":"11122","dataId":12152,"faceUri":"201703/27/BL1416021113507560.jpg","geoCode":"wwgjkwfx","geoLat":38.93,"geoLng":116.92,"geoName":"天津市","nAskFor":2,"nComment":0,"nPraise":8,"name":"test1","official":0,"photos":"201703/07/BL1416021451461750.JPEG","sex":1,"status":40,"timeIn":1488869515,"title":"#超级喜欢就拿走#不给","userId":41602},{"birthday":0,"content":"123\n","dataId":12158,"faceUri":"201703/22/BL1416031147329650.jpg","geoCode":"","geoLat":0,"geoLng":0,"geoName":"张家口市·桥东区","nAskFor":1,"nComment":0,"nPraise":6,"name":"test2","official":0,"photos":"201703/07/BL1416031540198950.jpg","sex":0,"status":6,"timeIn":1488872434,"title":"标题重复","userId":41603},{"birthday":0,"content":"12345","dataId":12146,"faceUri":"201703/22/BL1416031147329650.jpg","geoCode":"wsse4m6p","geoLat":25.87,"geoLng":118.93,"geoName":"福建省·福州市","nAskFor":1,"nComment":0,"nPraise":6,"name":"test2","official":0,"photos":"201703/07/BL1416030937018060.JPEG,201703/07/BL1416030937027340.JPEG","sex":0,"status":40,"timeIn":1488850646,"title":"七天提醒收货物","userId":41603},{"birthday":0,"content":"我是真的不","dataId":12214,"faceUri":"201703/22/BL1416031147329650.jpg","geoCode":"","geoLat":0,"geoLng":0,"geoName":"龙岩市·新罗区","nAskFor":1,"nComment":0,"nPraise":1,"name":"test2","official":0,"photos":"201703/17/BL1416030945297590.jpg","sex":0,"status":40,"timeIn":1489715139,"title":"我们的感情都","userId":41603},{"birthday":0,"content":"1234","dataId":12145,"faceUri":"201703/22/BL1416031147329650.jpg","geoCode":"wksn6pj4","geoLat":26.45,"geoLng":106.97,"geoName":"贵州省·黔南布依族苗族自治州","nAskFor":1,"nComment":0,"nPraise":1,"name":"test2","official":0,"photos":"201703/07/BL1416030907006020.JPEG,201703/07/BL14160309070100310.JPEG,201703/07/BL1416030907011190.JPEG","sex":0,"status":40,"timeIn":1488848834,"title":"123","userId":41603}]
     * time : 1495335501
     */

    private int time;
    private List<ListBean> list;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * birthday : 722275200
         * content : 测试
         * dataId : 12205
         * faceUri : 201703/20/BL1410101351555090.JPEG
         * geoCode : wm3ytdx8
         * geoLat : 30.68758
         * geoLng : 103.963233
         * geoName : 四川省·成都市
         * nAskFor : 1
         * nComment : 0
         * nPraise : 11
         * name : carry155
         * official : 0
         * photos : 201703/13/BL1410101514563970.JPEG
         * sex : 0
         * status : 40
         * timeIn : 1489389380
         * title : 测试收到并感谢
         * userId : 41010
         */

        private int birthday;
        private String content;
        private int dataId;
        private String faceUri;
        private String geoCode;
        private double geoLat;
        private double geoLng;
        private String geoName;
        private int nAskFor;
        private int nComment;
        private int nPraise;
        private String name;
        private int official;
        private String photos;
        private int sex;
        private int status;
        private int timeIn;
        private String title;
        private int userId;

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
            this.birthday = birthday;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
        }

        public String getFaceUri() {
            return faceUri;
        }

        public void setFaceUri(String faceUri) {
            this.faceUri = faceUri;
        }

        public String getGeoCode() {
            return geoCode;
        }

        public void setGeoCode(String geoCode) {
            this.geoCode = geoCode;
        }

        public double getGeoLat() {
            return geoLat;
        }

        public void setGeoLat(double geoLat) {
            this.geoLat = geoLat;
        }

        public double getGeoLng() {
            return geoLng;
        }

        public void setGeoLng(double geoLng) {
            this.geoLng = geoLng;
        }

        public String getGeoName() {
            return geoName;
        }

        public void setGeoName(String geoName) {
            this.geoName = geoName;
        }

        public int getNAskFor() {
            return nAskFor;
        }

        public void setNAskFor(int nAskFor) {
            this.nAskFor = nAskFor;
        }

        public int getNComment() {
            return nComment;
        }

        public void setNComment(int nComment) {
            this.nComment = nComment;
        }

        public int getNPraise() {
            return nPraise;
        }

        public void setNPraise(int nPraise) {
            this.nPraise = nPraise;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOfficial() {
            return official;
        }

        public void setOfficial(int official) {
            this.official = official;
        }

        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTimeIn() {
            return timeIn;
        }

        public void setTimeIn(int timeIn) {
            this.timeIn = timeIn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
