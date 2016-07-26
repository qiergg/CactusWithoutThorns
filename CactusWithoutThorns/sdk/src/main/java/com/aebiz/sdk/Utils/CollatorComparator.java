package com.aebiz.sdk.Utils;


import com.aebiz.sdk.Model.Unit;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

/**
 * Created by zhengxianxiang on 15/11/11.
 */
public class CollatorComparator implements Comparator {
    Collator collator = Collator.getInstance();

    public int compare(Object element1, Object element2) {

        CollationKey key1 = collator
                .getCollationKey(((Unit) element1).getName());
        CollationKey key2 = collator
                .getCollationKey(((Unit) element2).getName());
        return key1.compareTo(key2);
    }
}
