package pl.allblue.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public abstract class Pager
{

    private String tag = null;
    private AppCompatActivity activity = null;
    private Fragment fragment = null;
    private int view_Id = -1;

    private Pages pages = null;

    private String activePage_Name = null;
    private Fragment activePage_Fragment = null;


    public Pager(String pager_tag, AppCompatActivity activity, int page_view_id)
    {
        this.tag = pager_tag;
        this.activity = activity;
        this.view_Id = page_view_id;

        this.pages = new Pages(this.tag);
    }

    public Pager(String pager_tag, Fragment fragment, int page_view_id)
    {
        this.tag = pager_tag;
        this.fragment = fragment;
        this.view_Id = page_view_id;

        this.pages = new Pages(pager_tag);
    }

    public Fragment getActiveFragment()
    {
        return this.activePage_Fragment;
    }

    public Pages getPages()
    {
        return this.pages;
    }

    public int getViewId()
    {
        return this.view_Id;
    }

    public boolean onBackPressed()
    {
        if (this.activePage_Fragment != null) {
            if (this.activePage_Fragment instanceof Pager.OnBackPressedListener) {
                if (((Pager.OnBackPressedListener)this.activePage_Fragment)
                        .onBackPressed())
                    return true;
            }
        }

        return false;
    }

    public void onDestroyView()
    {
        this.activePage_Name = null;
        this.activePage_Fragment = null;
    }


    protected FragmentManager getFragmentManager()
    {
        if (this.activity != null)
            return this.activity.getSupportFragmentManager();
        if (this.fragment != null)
            return this.fragment.getChildFragmentManager();

        throw new AssertionError("`null` parent activity or fragment.");
    }

    protected String getStateKey(String state_ext)
    {
        return this.tag + ".States_" + state_ext;
    }

    protected void setActivePage(String page_name, Fragment fragment)
    {
        PageInfo pageInfo = this.pages.get(page_name);

        this.activePage_Name = page_name;
        this.activePage_Fragment = fragment;

        pageInfo.getPage().onSet();
    }

    protected void setFragment(String page_name, Fragment fragment)
    {
        PageInfo pageInfo = this.pages.get(page_name);

        this.activePage_Name = page_name;
        this.activePage_Fragment = fragment;

        pageInfo.getPage().onSet();
    }


//    public abstract boolean onBackPressed();
    public abstract void onCreateView(@Nullable Bundle saved_instance_state);
    public abstract void onSaveInstanceState(Bundle out_state);


    public interface OnBackPressedListener
    {
        boolean onBackPressed();
    }

}
