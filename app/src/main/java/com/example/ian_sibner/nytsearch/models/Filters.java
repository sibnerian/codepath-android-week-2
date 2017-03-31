package com.example.ian_sibner.nytsearch.models;

import android.support.annotation.Nullable;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Parcel
public class Filters {
    public String sortOrder;
    public String query;
    public Set<String> newsDesks;
    @Nullable
    public GregorianCalendar beginDate;

    // Empty constructor for Parcel
    public Filters() {}

    public Filters(String sortOrder, Set<String> newsDesks, GregorianCalendar beginDate, String query) {
        this.sortOrder = sortOrder;
        this.newsDesks = newsDesks;
        this.beginDate = beginDate;
        this.query = query;
    }

    private String formattedNewsDesks() {
        StringBuilder builder = new StringBuilder();
        builder.append("news_desk:(");
        List<String> stringsWithOrder = new ArrayList<>(newsDesks);
        for (int i = 0; i < stringsWithOrder.size(); i++) {
            builder.append("\"" + stringsWithOrder.get(i) + "\"");
            builder.append(i == stringsWithOrder.size() - 1 ? "" : " ");
        }
        builder.append(")");
        return builder.toString();
    }

    private String formattedBeginDate() {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return dt1.format(beginDate.getTime());
    }

    public Map<String,String> queryMap() {
        HashMap<String, String> result = new HashMap<>();
        if (query != null) {
            result.put("q", query);
        }
        result.put("sort", sortOrder == null ? "newest" : sortOrder);
        if (newsDesks != null && newsDesks.size() > 0) {
            result.put("fq", this.formattedNewsDesks());
        }
        if (beginDate != null) {
            result.put("begin_date", formattedBeginDate());
        }
        return result;
    }

    public void setQuery(String q) {
        query = q;
    }
}
