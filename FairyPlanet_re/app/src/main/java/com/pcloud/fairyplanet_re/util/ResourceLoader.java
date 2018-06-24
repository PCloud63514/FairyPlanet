package com.pcloud.fairyplanet_re.util;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLUtils;

import com.pcloud.fairyplanet_re.domain.SpriteInfo;
import com.pcloud.fairyplanet_re.domain.TexturePacker;
import com.pcloud.fairyplanet_re.domain.UnitInfo;
import com.pcloud.fairyplanet_re.inface.UnitAction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by PCloud on 2018-06-14.
 */

public class ResourceLoader {
    private static final String TAG = "ResourceLoader";
    //지금은 UI뿐이지만 나중에는 Sound도 불러오거나 SoundPool 데이터를 여기서 생성시킬까 생각 중임.

    public void loadingResource(UnitInfo unitInfo,Map<UnitAction, ArrayList<Integer>> unitActionMap) throws Exception {
        TexturePacker texturePacker = TexturePackerXmlParser.loadTexturePacker();
        fairyImageActionSprite(unitActionMap,texturePacker);
    }
//    public void loadingResource(UnitResource unitResource) throws Exception {
//        TexturePacker texturePacker = TexturePackerXmlParser.loadTexturePacker(unitResource.getUnitInfo());
//        imageActionSprite(unitResource.getActionImageHandles(), texturePacker);
//        Log.d(TAG,"loadingREsource END");
//    }

    private int getImageHandle(Bitmap bitmap) {
        int[] textureIds = new int[1];
        GLES10.glBlendFunc(GLES10.GL_ONE,GLES10.GL_ONE_MINUS_SRC_ALPHA);
        GLES10.glGenTextures(1, textureIds, 0);
        GLES10.glActiveTexture(GLES10.GL_TEXTURE0);
        GLES10.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0]);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0, bitmap,0);

        return textureIds[0];
    }

    private void fairyImageActionSprite(Map<UnitAction, ArrayList<Integer>> unitActionMap,
                                                                  TexturePacker texturePacker) throws Exception {
        AssetManager assetManager = MyApplication.getAppContext().getResources().getAssets();
        InputStream in = null;
        Bitmap sheetBitmap = null;
        try {
            in = assetManager.open(texturePacker.getImagePath());
            sheetBitmap = BitmapFactory.decodeStream(in);

            for(SpriteInfo spriteInfo : texturePacker.getSpriteInfoList()) {
                String[] action = spriteInfo.getN().split("_");
                UnitAction unitAction = setUnitAction(action[0]);

                ArrayList<Integer> actionBitmapList = unitActionMap.get(unitAction);
                if(actionBitmapList != null) {
                    Bitmap bitmap = Bitmap.createBitmap(sheetBitmap,
                            spriteInfo.getX(),
                            spriteInfo.getY(),
                             spriteInfo.getW() - 1,
                            spriteInfo.getH() - 1);
                    int imageHandle = getImageHandle(bitmap);
                    int index = Integer.parseInt(action[1].split(".png")[0]);
                    if(index < actionBitmapList.size()) {
                        actionBitmapList.add(imageHandle, index);
                    } else {
                        actionBitmapList.add(imageHandle);
                    }
                    bitmap.recycle();
                } else {
                    actionBitmapList = new ArrayList<Integer>();
                    Bitmap bitmap = Bitmap.createBitmap(sheetBitmap,
                            spriteInfo.getX(),
                            spriteInfo.getY(),
                            spriteInfo.getW() - 1,
                            spriteInfo.getH() - 1);
                    actionBitmapList.add(getImageHandle(bitmap));
                    bitmap.recycle();
                    unitActionMap.put(unitAction,actionBitmapList);
                }
            }
            sheetBitmap.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UnitAction setUnitAction(String str) {
        switch (str) {
            case "idle": return UnitAction.idle;
            case "hungry": return UnitAction.hungry;
            case "like": return UnitAction.like;
            case "move": return UnitAction.move;
            case "tired": return UnitAction.tired;
            case "seek": return UnitAction.seek;
            case "dislike": return UnitAction.dislike;
            default: return UnitAction.idle;
        }
    }
}
