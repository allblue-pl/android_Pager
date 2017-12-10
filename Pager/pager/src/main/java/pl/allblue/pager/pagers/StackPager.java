package pl.allblue.pager.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

import pl.allblue.pager.PageInfo;
import pl.allblue.pager.Pages;
import pl.allblue.pager.Pager;

public class StackPager extends Pager
{

    static public final String StateExts_PagesStack = "PagesStack";


    private Pages pages = null;
    private Stack<String> pages_Stack = new Stack<>();


    public StackPager(String pager_tag, AppCompatActivity activity, int page_view_id)
    {
        super(pager_tag, activity, page_view_id);
    }
    public StackPager(String pager_tag, Fragment fragment, int page_view_id)
    {
        super(pager_tag, fragment, page_view_id);
    }

    public void push(String page_name)
    {
        PageInfo pageInfo = this.pages.get(page_name);

        Fragment page_fragment = pageInfo.getPage().onCreate();

        this.getFragmentManager().beginTransaction()
            .replace(this.getViewId(), page_fragment)
            .addToBackStack(pageInfo.getTag())
            .commit();

        this.pages_Stack.push(page_name);

        this.setFragment(page_name, page_fragment);
    }

    public void pop()
    {
        String page_name = this.pages_Stack.pop();
        FragmentManager fm = this.getFragmentManager();

        int back_stack_count = fm.getBackStackEntryCount();
        if (back_stack_count == 0)
            throw new AssertionError("Stack empty.");

        this.getFragmentManager().popBackStack();
    }

    private Fragment getFragment(PageInfo page)
    {
        return page.getPage().onCreate();
    }


    /* FragmentManager Overrides */
    @Override
    public boolean onBackPressed()
    {
        if (super.onBackPressed())
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
            this.push(this.pages.getDefault().getName());
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
