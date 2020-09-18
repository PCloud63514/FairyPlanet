package com.pcloud.fairyplanet_re.util;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.pcloud.fairyplanet_re.R;
import com.pcloud.fairyplanet_re.domain.FairyInfo;
import com.pcloud.fairyplanet_re.domain.SpriteInfo;
import com.pcloud.fairyplanet_re.domain.TexturePacker;
import com.pcloud.fairyplanet_re.domain.UnitInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by PCloud on 2018-06-13.
 */

public class TexturePackerXmlParser {

    public static TexturePacker loadTexturePacker() throws Exception {
        Resources resources = MyApplication.getAppContext().getResources();
        AssetManager assetManager = resources.getAssets();
        InputStream input = null;
        XmlPullParserFactory xmlPullParserFactory = null;
        XmlPullParser xmlPullParser = null;

        TexturePacker texturePacker = new TexturePacker();

        try {
            String unitName = FairyInfo.getInstance().getUnitName().toLowerCase();
            input = assetManager.open(unitName + ".xml");
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(input,"UTF-8");

            int eventType = xmlPullParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if(xmlPullParser.getDepth() > 1) {
                        if (xmlPullParser.getAttributeCount() >= 0) {
                            String n = xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_name)) != null ? xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_name)) : "";
                            int x = xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_x)) != null ? Integer.parseInt(xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_x))) : 0;
                            int y = xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_y)) != null ? Integer.parseInt(xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_y))) : 0;
                            int w = xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_width)) != null ? Integer.parseInt(xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_width))) : 0;
                            int h = xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_height)) != null ? Integer.parseInt(xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_height))) : 0;
                            float pX = xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_px)) != null ? Float.valueOf(xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_px))) : 0;
                            float pY = xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_py)) != null ? Float.valueOf(xmlPullParser.getAttributeValue(null,resources.getString(R.string.sprite_py))) : 0;

                            texturePacker.getSpriteInfoList().add(new SpriteInfo(n,x,y,w,h,pX,pY));
                        }
                    } else {
                        String imagePath = xmlPullParser.getAttributeValue(null, resources.getString(R.string.image_path)) != null ? xmlPullParser.getAttributeValue(null, resources.getString(R.string.image_path)) : "";
                        int width = xmlPullParser.getAttributeValue(null, resources.getString(R.string.width)) != null ? Integer.parseInt(xmlPullParser.getAttributeValue(null, resources.getString(R.string.width))) : 0;
                        int height = xmlPullParser.getAttributeValue(null, resources.getString(R.string.height)) != null ? Integer.parseInt(xmlPullParser.getAttributeValue(null, resources.getString(R.string.height))) : 0;

                        if(!"".equals(imagePath)) {
                            texturePacker.setImagePath(imagePath);
                        } else {
                            throw new Exception("texturePacker null ImagePath throw");
                        }

                        if(0 < width) {
                            texturePacker.setWidth(width);
                        } else{
                            throw new Exception("texturePacker 0 width throw");
                        }

                        if(0 < height) {
                            texturePacker.setHeight(height);
                        } else {
                            throw new Exception("texturePacker 0 width throw");
                        }
                    }
                } else if (eventType == XmlPullParser.TEXT) {

                } else if (eventType == XmlPullParser.END_TAG) {

                }
                eventType = xmlPullParser.next();
            }

        }catch (Exception e) {
            throw e;
        }
        return texturePacker;
    }
}
