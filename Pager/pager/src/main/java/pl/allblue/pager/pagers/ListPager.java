package pl.allblue.pager.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.allblue.pager.PageInfo;
import pl.allblue.pager.Pager;
import pl.allblue.pager.PagerInstance;
import pl.allblue.pager.Pages;

public class ListPager implements PagerInstance
{

    static public final String StateExts_ActivePage = "ActivePage";


    private Pages pages = null;
    private Pager pager = null;

    private List<String> pages_Stack = new ArrayList<>();
    private String page_Active = null;


    public ListPager(String pagerTag, AppCompatActivity activity, int pageViewId)
    {
        this.pages = new Pages(pagerTag);
        this.pager = new Pager(pagerTag, activity, pageViewId);
    }

    public ListPager(String pagerTag, Fragment fragment, int pageViewId)
    {
        this.pages = new Pages(pagerTag);
        this.pager = new Pager(pagerTag, fragment, pageViewId);
    }

    public void change(String pageName, Bundle args)
    {
        if (this.pages_Stack.indexOf(pageName) != -1)
            this.pages_Stack.remove(pageName);
        this.pages_Stack.add(pageName);

        this.pager.set(pageName, this.pages.get(pageName).getPage(), args,
                false, true);
    }

    public void change(String pageName)
    {
        this.change(pageName, null);
    }

    public Pages getPages()
    {
        return this.pages;
    }


    /* PagerInstance Overrides */
//    @Override
//    public Pager getPager()
//    {
//        return this.pager;
//    }

    @Override
    public boolean onPagerBackPressed()
    {
        if (this.pager.onPagerBackPressed())
            return true;

        if (this.pages_Stack.size() <= 1)
            return false;

        String pageName = this.pages_Stack.get(this.pages_Stack.size() - 2);
        this.pages_Stack.remove(this.pages_Stack.size() - 1);

        this.change(pageName, null);
        return true;
    }
    /* / PagerInstance Overrides */

}
