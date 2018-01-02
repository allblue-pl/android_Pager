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

public class StackPager extends Pager
{

    static public final String StateExts_PagesStack = "PagesStack";


    private Stack<String> pages_Stack = new Stack<>();


    public StackPager(String pager_tag, AppCompatActivity activity, int page_view_id)
    {
        super(pager_tag, activity, page_view_id);
    }
    public StackPager(String pager_tag, Fragment fragment, int page_view_id)
    {
        super(pager_tag, fragment, page_view_id);
    }

    public void push(String pageName)
    {
        PageInfo pageInfo = this.getPages().get(pageName);
        Fragment pageFragment = pageInfo.getPage().onPageCreate();
        String pageTag = pageInfo.getTag() + "." + this.pages_Stack.size();

        this.getFragmentManager().beginTransaction()
            .replace(this.getViewId(), pageFragment, pageTag)
            .addToBackStack(pageTag)
            .commit();

        this.pages_Stack.push(pageName);

        this.setActivePage(pageName, pageFragment);
    }

    public void pop()
    {
        if (this.pages_Stack.size() < 2)
            throw new AssertionError("Stack empty.");

        this.pages_Stack.pop();

        int oldPage_Index = this.pages_Stack.size();
        String oldPage_Name = this.getActivePageName();
        Fragment oldPage_Fragment = this.getActiveFragment();

        int newPage_Index = this.pages_Stack.size() - 1;
        String newPage_Name = this.pages_Stack.get(this.pages_Stack.size() - 1);
        String newPage_Tag = this.getPages().get(newPage_Name).getTag() +
                "." + newPage_Index;

        Fragment newPage_Fragment = this.getFragmentManager()
                .findFragmentByTag(newPage_Tag);

        FragmentManager fm = this.getFragmentManager();

        int back_stack_count = fm.getBackStackEntryCount();
        if (back_stack_count == 0)
            throw new AssertionError("Stack empty.");

        this.getPages().get(oldPage_Name).getPage().onPageUnset(oldPage_Fragment);
        this.getFragmentManager().popBackStack();
         this.getPages().get(newPage_Name).getPage().onPageSet(newPage_Fragment);
    }

    private Fragment getFragment(PageInfo page)
    {
        return page.getPage().onPageCreate();
    }


    /* FragmentManager Overrides */
    @Override
    public boolean onPagerBackPressed()
    {
        if (super.onPagerBackPressed())
            return true;

        if (this.pages_Stack.size() <= 1)
            return false;

        this.pop();

        return true;
    }

    public void onCreateView(@Nullable Bundle saved_instance_state)
    {
        if (saved_instance_state != null) {
            String[] pages_stack = saved_instance_state.getStringArray(
                    this.getStateKey(StackPager.StateExts_PagesStack));

            for (int i = 0; i < pages_stack.length; i++)
                this.push(pages_stack[i]);
        } else if (this.pages_Stack.size() == 0)
            this.push(this.getPages().getDefault().getName());
    }

    public void onSaveInstanceState(Bundle out_state)
    {
        String[] pages_stack_array = new String[this.pages_Stack.size()];
        this.pages_Stack.toArray(pages_stack_array);

        out_state.putStringArray(this.getStateKey(
                StackPager.StateExts_PagesStack), pages_stack_array);
    }
    /* / FragmentManager Overrides */

}
