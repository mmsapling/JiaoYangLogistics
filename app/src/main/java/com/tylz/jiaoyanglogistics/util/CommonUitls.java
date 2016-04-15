package com.tylz.jiaoyanglogistics.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.model.AddressInfo;
import com.tylz.jiaoyanglogistics.model.AreaInfo;
import com.tylz.jiaoyanglogistics.model.CityInfo;
import com.tylz.jiaoyanglogistics.model.ContactsInfo;
import com.tylz.jiaoyanglogistics.model.StreetInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                    String first_pingyin = pingYin.substring(0, 1)
                                                  .toUpperCase();
                    if (first_pingyin.matches("[A-Z]")) {
                        info.sortLetter = first_pingyin;
                    } else {
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
        TelephonyManager tm        = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String           DEVICE_ID = tm.getDeviceId();
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
        Class c = obj instanceof Class
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

    /**
     * 格式化时间串
     * @param date  传入 xxxx-x-x 或者xxxx-0x-0x
     * @return 返回xxxx年xx月xx日
     */
    public static String formatDataString(String date) {
        String[]      dates = date.split("-");
        StringBuilder sb    = new StringBuilder();
        sb.append(dates[0] + "年");
        if (dates[1].length() == 2) {
            sb.append(dates[1] + "月");
        } else {
            sb.append("0" + dates[1] + "月");
        }
        if (dates[2].length() == 2) {
            sb.append(dates[2] + "日");
        } else {
            sb.append("0" + dates[2] + "日");
        }
        return sb.toString();
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {

        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder  sb     = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static AddressInfo parseJson(Context context) {
        AddressInfo addressInfo = new AddressInfo();
        try {
            InputStream is = context.getAssets()
                                    .open(Constants.JSON_ADDRESS_DATA_NAME);
            String json = CommonUitls.convertStreamToString(is);
            Gson gson = new Gson();
            addressInfo = gson.fromJson(json, AddressInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressInfo;
    }

    /**
     * 得到所有的市
     * @param datas  地址数据
     * @return  返回市信息集合
     */
    public static List<CityInfo> getAllCityInfo(AddressInfo datas){
        List<CityInfo> list= new ArrayList<CityInfo>();
        //初始化 地址数据
        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);
            list.add(cityInfo);
        }
        return list;
    }
    /**
     * 根据城市名 获取这个城市下面的所有区域
     */
    public static List<AreaInfo> getAreaDataByCityName(AddressInfo datas, CityInfo city) {
        List<AreaInfo> areaDatas = new ArrayList<AreaInfo>();
        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);
            if (!cityInfo.name.equals(city.name)) {
                continue;
            }
            for (int j = 0; j < cityInfo.child.size(); j++) {
                AreaInfo areaInfo = cityInfo.child.get(j);
                areaDatas.add(areaInfo);
            }
        }


        return areaDatas;
    }
    /**
     * 根据城市名 获取这个城市下面的所有区域
     */
    public static List<AreaInfo> getAreaDataByCityId(AddressInfo datas, String id) {
        List<AreaInfo> areaDatas = new ArrayList<AreaInfo>();
        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);
            if (!cityInfo.id.equals(id)) {
                continue;
            }
            for (int j = 0; j < cityInfo.child.size(); j++) {
                AreaInfo areaInfo = cityInfo.child.get(j);
                areaDatas.add(areaInfo);
            }
        }


        return areaDatas;
    }
    /**
     * 根据城市名 ,区域名获取这个城市下面的所有区域
     */
    public static List<StreetInfo> getStreetDataByAreaName(AddressInfo datas,
                                                           CityInfo city,
                                                           AreaInfo area)
    {
        List<StreetInfo> streetDatas = new ArrayList<StreetInfo>();

        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);
            if (!cityInfo.name.equals(city.name)) {
                continue;
            }
            for (int j = 0; j < cityInfo.child.size(); j++) {
                AreaInfo areaInfo = cityInfo.child.get(j);
                if (!areaInfo.name.equals(area.name)) {
                    continue;
                }
                for (int k = 0; k < areaInfo.child.size(); k++) {
                    StreetInfo streetInfo = areaInfo.child.get(k);
                    streetDatas.add(streetInfo);
                }
            }
        }
        return streetDatas;
    }
    /**
     * 根据城市名 ,区域名获取这个城市下面的所有区域
     */
    public static List<StreetInfo> getStreetDataByAreaId(AddressInfo datas,
                                                           String cityId,
                                                           String areaId)
    {
        List<StreetInfo> streetDatas = new ArrayList<StreetInfo>();

        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);
            if (!cityInfo.id.equals(cityId)) {
                continue;
            }
            for (int j = 0; j < cityInfo.child.size(); j++) {
                AreaInfo areaInfo = cityInfo.child.get(j);
                if (!areaInfo.id.equals(areaId)) {
                    continue;
                }
                for (int k = 0; k < areaInfo.child.size(); k++) {
                    StreetInfo streetInfo = areaInfo.child.get(k);
                    streetDatas.add(streetInfo);
                }
            }
        }
        return streetDatas;
    }
    /**
     * 根据id找城市信息
     * @param datas 地址数据
     * @param id 市的id
     * @return  返回城市信息
     */
    public static CityInfo getCityInfoById(AddressInfo datas,String id)
    {
        CityInfo data = null;
        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);
            if (!cityInfo.id.equals(id)) {
                continue;
            }
            data  = cityInfo;
        }
        return data;
    }
    /**
     * 根据id找地区信息
     * @param datas 地址数据
     * @param id 地区的id
     * @return  返回地区信息
     */
    public static AreaInfo getAreaInfoById(AddressInfo datas,String id)
    {
        AreaInfo data = null;
        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);

            for(int j = 0; j < cityInfo.child.size(); j++){
                AreaInfo areaInfo = cityInfo.child.get(j);
                if (!areaInfo.id.equals(id)) {
                    continue;
                }
                data = areaInfo;
            }
        }
        return data;
    }
    /**
     * 根据id找地区信息
     * @param datas 地址数据
     * @param id 地区的id
     * @return  返回地区信息
     */
    public static StreetInfo getStreetInfoById(AddressInfo datas,String id)
    {
        StreetInfo data = null;
        for (int i = 0; i < datas.address.size(); i++) {
            CityInfo cityInfo = datas.address.get(i);

            for(int j = 0; j < cityInfo.child.size(); j++){
                AreaInfo areaInfo = cityInfo.child.get(j);
                for(int k = 0; k < areaInfo.child.size();k++){
                    StreetInfo streetInfo = areaInfo.child.get(k);
                    if(!streetInfo.id.equals(id)){
                        continue;
                    }
                    data = streetInfo;
                }
            }
        }
        return data;
    }

    /**
     * 根据id得到省的名称
     * @param id 省的id
     * @return 省的名称
     */
    public static String getProvinceNameById(String id){
        String name = "广东";
        if(id.equals("400000")){
            name = "广东";
        }
        return name;
    }
}
