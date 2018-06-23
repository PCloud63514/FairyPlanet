package com.pcloud.fairyplanet_re.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PCloud on 2018-01-06.
 */

public class TexturePacker {
    private String imagePath;
    private int width;
    private int height;
    private List<SpriteInfo> spriteInfoList;

    public TexturePacker() {
        this.spriteInfoList = new ArrayList<SpriteInfo>();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<SpriteInfo> getSpriteInfoList() {
        return spriteInfoList;
    }

    public void setSpriteInfoList(List<SpriteInfo> spriteInfoList) {
        this.spriteInfoList = spriteInfoList;
    }
}
