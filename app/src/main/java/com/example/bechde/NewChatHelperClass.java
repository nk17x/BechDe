package com.example.bechde;

public class NewChatHelperClass {
    String chatsid,fcuid1, fcuid2, fcimgurl1, fcimgurl2, fcname1, fcname2,imgurl,adtitle;

    @Override
    public String toString() {
        return "NewChatHelperClass{" +
                "chatsid='" + chatsid + '\'' +
                ", fcuid1='" + fcuid1 + '\'' +
                ", fcuid2='" + fcuid2 + '\'' +
                ", fcimgurl1='" + fcimgurl1 + '\'' +
                ", fcimgurl2='" + fcimgurl2 + '\'' +
                ", fcname1='" + fcname1 + '\'' +
                ", fcname2='" + fcname2 + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", adtitle='" + adtitle + '\'' +
                '}';
    }

    public String getChatsid() {
        return chatsid;
    }

    public void setChatsid(String chatsid) {
        this.chatsid = chatsid;
    }

    public String getFcuid1() {
        return fcuid1;
    }

    public void setFcuid1(String fcuid1) {
        this.fcuid1 = fcuid1;
    }

    public String getFcuid2() {
        return fcuid2;
    }

    public void setFcuid2(String fcuid2) {
        this.fcuid2 = fcuid2;
    }

    public String getFcimgurl1() {
        return fcimgurl1;
    }

    public void setFcimgurl1(String fcimgurl1) {
        this.fcimgurl1 = fcimgurl1;
    }

    public String getFcimgurl2() {
        return fcimgurl2;
    }

    public void setFcimgurl2(String fcimgurl2) {
        this.fcimgurl2 = fcimgurl2;
    }

    public String getFcname1() {
        return fcname1;
    }

    public void setFcname1(String fcname1) {
        this.fcname1 = fcname1;
    }

    public String getFcname2() {
        return fcname2;
    }

    public void setFcname2(String fcname2) {
        this.fcname2 = fcname2;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAdtitle() {
        return adtitle;
    }

    public void setAdtitle(String adtitle) {
        this.adtitle = adtitle;
    }

    public NewChatHelperClass(String chatsid, String fcuid1, String fcuid2, String fcimgurl1, String fcimgurl2, String fcname1, String fcname2, String imgurl, String adtitle) {
        this.chatsid = chatsid;
        this.fcuid1 = fcuid1;
        this.fcuid2 = fcuid2;
        this.fcimgurl1 = fcimgurl1;
        this.fcimgurl2 = fcimgurl2;
        this.fcname1 = fcname1;
        this.fcname2 = fcname2;
        this.imgurl = imgurl;
        this.adtitle = adtitle;
    }



    public NewChatHelperClass() {
    }
}
