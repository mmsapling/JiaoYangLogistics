package com.tylz.jiaoyanglogistics.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.model.ContactsInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tylz
 * @time 2016/3/18 0018 16:03
 * @des 一些常用的方法
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes 一些常用的方法
 */

public class CommonUitls {
    /**保存文件到本地路径*/
    public static void saveBitmapToLocalFile(String localImagePath, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(localImagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            bitmap = null;
        }
    }

    /**
     * 从内存卡中读取bitmap
     */
    public static Bitmap getBitmapFromFile(String localImagePath) {
        Bitmap bitmap = null;
        try {
            File file = new File(localImagePath);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
            }
        } catch (FileNotFoundException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        } catch (IOException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        } catch (OutOfMemoryError ex) {
            bitmap = null;
            System.gc();
        }
        return bitmap;
    }

    /**
     * 从网络中获取bitmap
     */
    public static Bitmap getBitmapFromURL(String imageUrl, String localImagePath) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            FileOutputStream fos = new FileOutputStream(localImagePath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (FileNotFoundException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        } catch (IOException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        } catch (OutOfMemoryError ex) {
            bitmap = null;
            System.gc();
        }
        return bitmap;
    }

    /**
     * 得到手机通讯联系人信息
     */
    public static List<ContactsInfo> getPhoneContacts(Context context) {
        List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();
        ContentResolver    resolver         = context.getContentResolver();
        Uri                uri              = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[]           projection       = null;
        String             selection        = null;
        String[]           selectionArgs    = null;
        String             sortOrder        = null;
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContactsInfo info = new ContactsInfo();
                //得到手机号码
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //联系人名称
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                Long contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                //联系人头像id
                Long photoId = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
                //联系人头像bitmap
                Bitmap contactPhoto = null;
                //photoId大于0 表示联系人有头像，如果没有给此人设置一个默认头像
                if (photoId > 0) {
                    Uri photoUri = ContentUris.withAppendedId(uri, contactId);
                    InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(resolver,
                                                                                           photoUri);
                    contactPhoto = BitmapFactory.decodeStream(is);
                } else {
                    contactPhoto = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_head);
                }
                info.sortLetter = CharacterParser.getFirstSpell(name);
                info.mobile = number;
                info.name = name;
                info.photo = contactPhoto;
                //添加到集合中
                contactsInfoList.add(info);
            }
        }
        cursor.close();
        return contactsInfoList;
    }
}
