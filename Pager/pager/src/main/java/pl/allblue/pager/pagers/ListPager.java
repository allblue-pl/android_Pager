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
import pl.allblue.pager.Pages;

public class ListPager extends Pager
{

    static public final String StateExts_ActivePage = "ActivePage";


    private List<String> pages_Stack = new ArrayList<>();
    private String page_Active = null;


    public ListPager(String pager_tag, AppCompatActivity activity, int page_view_id)
    {
        super(pager_tag, activity, page_view_id);
    }

    public ListPager(String pager_tag, Fragment fragment, int page_view_id)
    {
        super(pager_tag, fragment, page_view_id);
    }

    public void set(String pageName, boolean addToStack)
    {
        PageInfo pageInfo = this.getPages().get(pageName);
        FragmentManager fm = this.getFragmentManager();

        Fragment pageFragment = this.getFragmentManager().findFragmentByTag(
                pageInfo.getTag());
        boolean isNewFragment = false;
        if (pageFragment == null) {
            pageFragment = pageInfo.getPage().onCreate();
            isNewFragment = true;
            Log.d("Pages", "New " + pageInfo.getName());
        } else {
            Log.d("Pages", "Reusing " + pageInfo.getName());
        }

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

        this.setActivePage(pageName, pageFragment);
    }

    public void set(String page_name)
    {
        this.set(page_name, true);
    }


    /* Pager Overrides */
    @Override
    public boolean onBackPressed()
    {
        if (super.onBackPressed())
            return true;

        if (this.pages_Stack.size() <= 1)
            return false;

        String page_name = this.pages_Stack.get(this.pages_Stack.size() - 2);
        this.pages_Stack.remove(this.pages_Stack.size() - 1);

        this.set(page_name, false);

        return true;
    }

    @Override
    public void onCreateView(@Nullable Bundle saved_instance_state)
    {
        if (saved_instance_state != null) {
            this.page_Active = saved_instance_state.getString(
                    this.getStateKey(ListPager.StateExts_ActivePage));
        } else if (this.page_Active == null)
            this.page_Active = this.getPages().getDefault().getName();

        Log.d("ListPager", "Creating view: " + this.page_Active);
        this.set(this.page_Active);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString(this.getStateKey(ListPager.StateExts_ActivePage),
                this.page_Active);
    }
    /* / Pager Overrides */

}
