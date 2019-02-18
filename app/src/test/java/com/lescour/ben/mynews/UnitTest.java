package com.lescour.ben.mynews;

import com.bumptech.glide.RequestManager;
import com.lescour.ben.mynews.controller.BaseCustomSearchAndCategories;
import com.lescour.ben.mynews.model.Article;
import com.lescour.ben.mynews.utils.AlarmReceiver;
import com.lescour.ben.mynews.view.BaseRecyclerViewAdapter;
import com.lescour.ben.mynews.view.ViewHolder;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {

    /**
     * rawDate is different for every API
     * example :
     * Search Article : 2019-02-11T11:26:01+0000
     * Most Popular : 2019-02-05
     * Top Stories : 2019-02-10T12:00:43-05:00
     * but always starts with a same format "yyyy-MM-dd"
     */
    @Test
    public void rawDateOfArticle_IsCorrectlyTransform() {
        BaseRecyclerViewAdapter recyclerViewAdapter = new BaseRecyclerViewAdapter() {
            @Override
            protected void updateWithArticle(Article article, RequestManager glide, ViewHolder holder) {

            }

            @Override
            protected String getRawDate(Article article) {
                return null;
            }
        };

        assertEquals("10/02/2019", recyclerViewAdapter.getDateWhitNewFormat("2019-02-10T12:00:43-05:00"));
    }

    @Test
    public void beginDateNeedYesterday() {
        AlarmReceiver alarmReceiver = new AlarmReceiver();

        Calendar calendar = Calendar.getInstance();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String beginDate = format.format(calendar2.getTime());

        assertEquals(beginDate, alarmReceiver.setYesterdayToBeginDate(calendar));
    }

    @Test
    public void checkCategoriesBuilder() {
        BaseCustomSearchAndCategories baseCustomSearchAndCategories = new BaseCustomSearchAndCategories() {
        };
        String arts = "\"arts\"";
        String business = null;
        String entrepreneurs = null;
        String politics = null;
        String sports = null;
        String travel = "\"travel\"";

        assertEquals("\"arts\"\"travel\"", baseCustomSearchAndCategories.buildCompactCategoriesBuilder(arts, business, entrepreneurs, politics, sports, travel));
    }
}

