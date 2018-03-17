package pl.allblue.pager.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Stack;

import pl.allblue.pager.PageInfo;
import pl.allblue.pager.Pager;
import pl.allblue.pager.PagerInstance;
import pl.allblue.pager.Pages;

public class StackPager implements PagerInstance
{

    static public final String StateExts_PagesStack = "PagesStack";


    private Pages pages = null;
    private Pager pager = null;

    private Stack<String> pages_Stack = new Stack<>();


    public StackPager(String pagerTag, AppCompatActivity activity, int pageViewId)
    {
        this.pages = new Pages(pagerTag);
        this.pager = new Pager(pagerTag, activity, pageViewId);
    }

    public StackPager(String pagerTag, Fragment fragment, int pageViewId)
    {
        this.pages = new Pages(pagerTag);
        this.pager = new Pager(pagerTag, fragment, pageViewId);
    }

    public Pages getPages()
    {
        return this.pages;
    }

    public void push(String pageName, Bundle bundle)
    {
        this.pages_Stack.add(pageName);

        String pageIndex = Integer.toString(this.pages_Stack.size() - 1);

        this.pager.set(pageIndex, this.pages.get(pageName).getPage(), bundle,
                false, true);
    }

    public void push(String pageName)
    {
        this.push(pageName, null);
    }

    public void pop()
    {
        if (this.pages_Stack.size() < 2)
            throw new AssertionError("Stack empty.");

        String pageIndex = Integer.toString(this.pages_Stack.size() - 2);
        String pageName = this.pages_Stack.get(this.pages_Stack.size() - 2);

        this.pager.set(pageIndex, this.pages.get(pageName).getPage(), null,
                false, true);
        this.pager.remove(this.pages_Stack.pop());
    }

    public int size()
    {
        return this.pages_Stack.size();
    }


    /* PageInstance Overrides */

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

        this.pop();

        return true;
    }
    /* / PagerInstance Overrides */

}
