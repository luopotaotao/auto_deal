package com.tt.web.upload;

/**
 * Created by tt on 2016/11/28.
 */
public class ProgressEntity {


    private long pBytesRead = 0L;
    private long pContentLength = 0L;
    private int pItems;

    public long getpBytesRead() {
        return pBytesRead;
    }

    public void setpBytesRead(long pBytesRead) {
        this.pBytesRead = pBytesRead;
    }

    public long getpContentLength() {
        return pContentLength;
    }

    public void setpContentLength(long pContentLength) {
        this.pContentLength = pContentLength;
    }

    public int getpItems() {
        return pItems;
    }

    public void setpItems(int pItems) {
        this.pItems = pItems;
    }

    public float getPercent(){
        return (float) this.pBytesRead/this.pContentLength;
    }

    @Override
    public String toString() {
        return "ProgressEntity{" +
                "pBytesRead=" + pBytesRead +
                ", pContentLength=" + pContentLength +
                ", percent=" + getPercent() +
                ", pItems=" + pItems +
                '}';
    }
}