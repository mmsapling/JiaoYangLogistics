package com.tylz.jiaoyanglogistics.factory;

import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.fragment.BusinessApproveFra;
import com.tylz.jiaoyanglogistics.fragment.ConSigneesAddressFra;
import com.tylz.jiaoyanglogistics.fragment.RealNameApproveFra;
import com.tylz.jiaoyanglogistics.fragment.ShipperAddressFra;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tylz
 * @time 2016/3/25 0025 15:13
 * @des 生成/实例化/返回一个一个的Fragment
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class FragmentFactory {
    public static final String                    TAG_APPROVE       = "MyApproveFra";
    public static final String                    TAG_ADDRESS       = "AddressBookFra";
    public static final String                    TAG_APPROVE0 = TAG_APPROVE + 0; //实名认证
    public static final String                    TAG_APPROVE1 = TAG_APPROVE + 1; //企业认证
    public static final String                    TAG_ADDRESS0 = TAG_ADDRESS + 0; //发货人地址
    public static final String                    TAG_ADDRESS1 = TAG_ADDRESS + 1; //企业认证
    public static       Map<String, BaseFragment> mCacheFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(String position) {

        BaseFragment fragment = null;
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }
        switch (position) {
            case TAG_APPROVE0:
                fragment = new RealNameApproveFra();
                break;
            case TAG_APPROVE1:
                fragment = new BusinessApproveFra();
                break;
            case TAG_ADDRESS0:
                fragment = new ShipperAddressFra();
                break;
            case TAG_ADDRESS1:
                fragment = new ConSigneesAddressFra();
                break;
        }
        mCacheFragmentMap.put(position, fragment);
        return fragment;
    }
}
