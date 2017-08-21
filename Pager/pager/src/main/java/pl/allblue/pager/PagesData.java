package pl.allblue.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class PagesData
{

    private String tag = null;
    private AppCompatActivity activity = null;
    private Fragment fragment = null;
    private int view_Id = -1;

    private Map<String, Page> pages = new HashMap<>();

    private String firstPage_Name = null;
    private String defaultPage_Name = null;

    private String activePage_Name = null;
    private Fragment activePage_Fragment = null;


    public PagesData(String pager_tag, AppCompatActivity activity, int page_view_id)
    {
        this.tag = pager_tag;
        this.activity = activity;
        this.view_Id = page_view_id;
    }

    public PagesData(String pager_tag, Fragment fragment, int page_view_id)
    {
        this.tag = pager_tag;
        this.fragment = fragment;
        this.view_Id = page_view_id;
    }

    public void addPage(String page_name, PageInstance page_instance)
    {
        Page page = new Page(this.tag, page_name, page_instance);

        this.pages.put(page_name, page);
        if (this.firstPage_Name == null)
            this.firstPage_Name = page_name;
    }

    public Page getActivePage()
    {
        return this.activePage_Name == null ?
                null : this.getPage(this.activePage_Name);
    }

    public FragmentManager getFragmentManager()
    {
        if (this.activity != null)
            return this.activity.getSupportFragmentManager();
        if (this.fragment != null)
            return this.fragment.getChildFragmentManager();

        throw new AssertionError("`null` parent activity or fragment.");
    }

    public Page getDefaultPage()
    {
        if (this.defaultPage_Name == null) {
            if (this.pages.size() == 0)
                throw new AssertionError("No pages added.");

            return this.pages.get(this.firstPage_Name);
        }

        return this.pages.get(this.defaultPage_Name);
    }

    public Page getPage(String page_name)
    {
        if (!this.pages.containsKey(page_name))
            throw new AssertionError("Page `" + page_name + "` does not exist.");

        return this.pages.get(page_name);
    }

    public String getStateKey(String state_ext)
    {
        return this.tag + ".States_" + state_ext;
    }

    public int getViewId()
    {
        return this.view_Id;
    }

    public boolean onBackPressed()
    {
        if (this.activePage_Fragment != null) {
            if (this.activePage_Fragment instanceof PagesManager.OnBackPressedListener) {
                if (((PagesManager.OnBackPressedListener)this.activePage_Fragment)
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

    public void setActivePage(String page_name, Fragment fragment)
    {
        Page page = this.getPage(page_name);

        this.activePage_Name = page_name;
        this.activePage_Fragment = fragment;

        page.getInstance().onSet();
    }

    public void setDefaultPage(String page_name)
    {
        this.defaultPage_Name = page_name;
    }


    public class Page
    {

        private String name = null;
        private String tag = null;
        private PageInstance instance = null;

        public Page(String pager_tag, String name, PageInstance instance)
        {
            this.name = name;
            this.tag = pager_tag + "." + name;
            this.instance = instance;
        }

        public PageInstance getInstance()
        {
            return this.instance;
        }

        public String getName()
        {
            return this.name;
        }

        public String getTag()
        {
            return this.tag;
        }

    }

    public void setFragment(String page_name, Fragment fragment)
    {
        Page page = this.pages.get(page_name);

        this.activePage_Name = page_name;
        this.activePage_Fragment = fragment;

        page.getInstance().onSet();
    }

    public interface PageInstance
    {
        Fragment onCreate();
        void onSet();
    }

}
