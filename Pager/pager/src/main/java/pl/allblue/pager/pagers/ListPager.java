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

public class ListPager extends Pager
{

    static public final String StateExts_ActivePage = "ActivePage";


    private List<String> pages_Stack = new ArrayList<>();
    private String page_Active = null;


    public ListPager(String pagerTag, AppCompatActivity activity, int pageViewId)
    {
        super(pagerTag, activity, pageViewId);
    }

    public ListPager(String pagerTag, Fragment fragment, int pageViewId)
    {
        super(pagerTag, fragment, pageViewId);
    }

    public void loadInstanceState(@Nullable Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            this.page_Active = savedInstanceState.getString(
                    this.getStateKey(ListPager.StateExts_ActivePage), null);
            if (this.page_Active == null) {
                throw new AssertionError(
                        "Pagers `onSaveInstanceState` not called.");
            }
        } else if (this.getPages().getDefault() != null)
            this.page_Active = this.getPages().getDefault().getName();
    }

    public void set(String pageName, Bundle args, boolean createNew, boolean addToStack)
    {
        PageInfo pageInfo = this.getPages().get(pageName);

        Fragment pageFragment = null;
        boolean isNewFragment = false;

        if (!createNew) {
            pageFragment = this.getFragmentManager().findFragmentByTag(
                    pageInfo.getTag());
        }

        if (pageFragment == null) {
            pageFragment = pageInfo.getPage().onPageCreate();
            isNewFragment = true;
        }

        pageFragment.setArguments(args);

        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        ft.replace(this.getViewId(), pageFragment, pageInfo.getTag());
        if (isNewFragment)
            ft.addToBackStack(pageInfo.getTag());
        ft.commit();

        if (addToStack) {
            this.pages_Stack.remove(pageName);
            this.pages_Stack.add(pageName);
        }

        this.page_Active = pageName;

        Log.d("ListPager", pageName);

        this.setActivePage(pageName, pageFragment);
    }

    public void set(String pageName, Bundle bundle, boolean createNew)
    {
        this.set(pageName, bundle, createNew, false);
    }

    public void set(String pageName, Bundle bundle)
    {
        this.set(pageName, bundle, false);
    }

    public void set(String pageName)
    {
        this.set(pageName, null);
    }


    /* Pager Overrides */
    @Override
    public boolean onPagerBackPressed()
    {
        if (super.onPagerBackPressed())
            return true;

        if (this.pages_Stack.size() <= 1)
            return false;

        String page_name = this.pages_Stack.get(this.pages_Stack.size() - 2);
        this.pages_Stack.remove(this.pages_Stack.size() - 1);

        this.set(page_name, null, false);

        return true;
    }

    @Override
    public void onCreateView()
    {
        if (this.getActiveFragment() != null)
            return;

        if (this.page_Active != null)
            this.set(this.page_Active);

//        if (savedInstanceState != null) {
//            this.page_Active = savedInstanceState.getString(
//                    this.getStateKey(ListPager.StateExts_ActivePage));
//        } else if (this.page_Active == null) {
//            if (this.getPages().getDefault() == null)
//                this.page_Active = null;
//            else
//                this.page_Active = this.getPages().getDefault().getName();
//        }
//
//        if (this.page_Active != null)
//            this.set(this.page_Active);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString(this.getStateKey(ListPager.StateExts_ActivePage),
                this.page_Active);
    }
    /* / Pager Overrides */

}
