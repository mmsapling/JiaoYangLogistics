package com.tylz.jiaoyanglogistics.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.model.ContactsInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        try {

            ContentResolver resolver = context.getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = null;
            String selection = null;
            String[] selectionArgs = null;
            String sortOrder = null;
            Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    ContactsInfo info = new ContactsInfo();
                    //得到手机号码
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //联系人名称
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Long contactId = cursor.getLong(cursor.getColumnIndex("contact_id"));
                    //头像这里获取有问题， 查资料再来
                    //联系人头像bitmap
                    Bitmap contactPhoto = null;
                    //photoId大于0 表示联系人有头像，如果没有给此人设置一个默认头像
                    // Uri photoUri = ContentUris.withAppendedId(uri, contactId);
                    // InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(resolver,photoUri);
                    // contactPhoto = BitmapFactory.decodeStream(is);
                    if (contactPhoto == null) {
                        contactPhoto = BitmapFactory.decodeResource(context.getResources(),
                                                                    R.mipmap.bg_head);
                    }
                    String pingYin = PinyinUtils.getPingYin(name);
                    String first_pingyin = pingYin.substring(0, 1).toUpperCase();
                    if(first_pingyin.matches("[A-Z]")){
                        info.sortLetter = first_pingyin;
                    }else{
                        info.sortLetter = "☆";
                    }
                    info.pingyin = pingYin;
                    info.mobile = number;
                    info.name = name;
                    info.photo = contactPhoto;
                    //添加到集合中
                    contactsInfoList.add(info);
                }
            }
            cursor.close();
            return contactsInfoList;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取Android手机唯一标志串
     *
     * @return DEVICE_ID
     */
    public static String getDeviceId(Context context)
    {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }

    /**
     * 获取父类的泛型参数
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericType(Object obj) {
        Class<T> klass = null;
        Class    c     = obj instanceof Class
                         ? (Class) obj
                         : obj.getClass();
        while (c != null) {
            Type t = c.getGenericSuperclass();
            if (t != null && t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args != null && args.length > 0) {
                    klass = (Class<T>) args[0];
                    break;
                }
            }
            c = c.getSuperclass();
            if (Object.class.equals(c)) {
                break;
            }
        }
        return klass;
    }

}
