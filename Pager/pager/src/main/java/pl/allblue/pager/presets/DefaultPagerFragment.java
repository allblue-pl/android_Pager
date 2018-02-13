package pl.allblue.pager.presets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.allblue.pager.R;
import pl.allblue.pager.fragments.PagerFragment;

abstract public class DefaultPagerFragment extends PagerFragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        this.getPager().onCreateView(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_defaultpager, container,
                false);

        return view;
    }

}
