package pl.allblue.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Pager implements PagerInstance
{

    private String tag = null;
    private AppCompatActivity activity = null;
    private Fragment fragment = null;
    private int view_Id = -1;

    private String activePage_Name = null;
    private Fragment activePage_Fragment = null;


    public Pager(String pagerTag, AppCompatActivity activity, int pageViewId)
    {
        this.tag = pagerTag;
        this.activity = activity;
        this.view_Id = pageViewId;
    }

    public Pager(String pagerTag, Fragment fragment, int pageViewId)
    {
        this.tag = pagerTag;
        this.fragment = fragment;
        this.view_Id = pageViewId;
    }

    public Fragment getActiveFragment()
    {
        return this.activePage_Fragment;
    }

    public String getActivePageName()
    {
        return this.activePage_Name;
    }

    public int getViewId()
    {
        return this.view_Id;
    }

    public void remove(String pageName)
    {
        Fragment fragment = this.getFragmentManager().findFragmentByTag(
                this.getPageTag(pageName));
        if (fragment == null)
            return;

        this.getFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }

    public void set(String pageName, Page page, Bundle args, boolean createNew,
            boolean saveState)
    {
        Fragment pageFragment = null;
        boolean isNewFragment = false;

        String pageTag = this.getPageTag(pageName);

        if (!createNew)
            pageFragment = this.getFragmentManager().findFragmentByTag(pageTag);

        if (pageFragment == null) {
            pageFragment = page.onPageCreate();
            isNewFragment = true;
        }

        if (args != null)
            pageFragment.setArguments(args);


        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        ft.replace(this.getViewId(), pageFragment, pageTag);
        if (saveState && isNewFragment)
            ft.addToBackStack(pageTag);
        ft.commit();

        this.activePage_Name = pageName;
        this.activePage_Fragment = pageFragment;

        if (pageFragment instanceof PageSetListener)
            ((PageSetListener)pageFragment).onPageSet(pageFragment);

        page.onPageSet(pageFragment);
    }

    public void set(String pageName, Page page, Bundle args, boolean createNew)
    {
        this.set(pageName, page, args, createNew, false);
    }

    public void set(String pageName, Page page, Bundle args)
    {
        this.set(pageName, page, args, true);
    }

    public void set(String pageName, Page page)
    {
        this.set(pageName, page, null);
    }


    protected FragmentManager getFragmentManager()
    {
        if (this.activity != null)
            return this.activity.getSupportFragmentManager();
        if (this.fragment != null)
            return this.fragment.getChildFragmentManager();

        throw new AssertionError("`null` parent activity or fragment.");
    }


    private String getPageTag(String pageName)
    {
        return this.tag + "." + pageName;
    }

    private String getStateKey(String stateExt)
    {
        return this.tag + ".States_" + stateExt;
    }


    /* PagerInstance Overrides */
//    @Override
//    public Pager getPager()
//    {
//        return this;
//    }

    @Override
    public boolean onPagerBackPressed()
    {
        if (this.activePage_Fragment != null) {
            if (this.activePage_Fragment instanceof BackPressedListener) {
                if (((BackPressedListener)this.activePage_Fragment)
                        .onPagerBackPressed())
                    return true;
            }
        }

        return false;
    }
    /* / PagerInstance Overrides */


    public interface BackPressedListener
    {
        boolean onPagerBackPressed();
    }

    public interface PageSetListener
    {
        void onPageSet(Fragment pageFragment);
    }

}
