package com.rivaresa.cusmateogl.supporting_class;

import java.util.ArrayList;
import java.util.HashMap;

public class NewAdvertGetSet {

    private static ArrayList<HashMap<String, String>> newsDataList;

    public static ArrayList<HashMap<String, String>> getNewsValue() {
        return newsDataList;
    }

    public static ArrayList<HashMap<String, String>> setNewsValue(ArrayList<HashMap<String, String>> _newsDataList) {
        return newsDataList = _newsDataList;
    }

    public static int getCount() {
        return 4;
    }
}
