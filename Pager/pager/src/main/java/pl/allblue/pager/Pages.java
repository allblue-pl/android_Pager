package pl.allblue.pager;

import java.util.HashMap;
import java.util.Map;

public class Pages
{

    String pagerTag = null;

    private Map<String, PageInfo> pages = new HashMap<>();

    private String firstPage_Name = null;
    private String defaultPage_Name = null;


    public Pages(String pagerTag)
    {
        this.pagerTag = pagerTag;
    }

    public Pages add(String page_name, Page page_instance)
    {
        PageInfo page = new PageInfo(this.pagerTag, page_name, page_instance);

        this.pages.put(page_name, page);
        if (this.firstPage_Name == null)
            this.firstPage_Name = page_name;

        return this;
    }

//    public PageInfo getActivePage()
//    {
//        return this.activePage_Name == null ?
//                null : this.get(this.activePage_Name);
//    }

    public PageInfo getDefault()
    {
        if (this.defaultPage_Name == null) {
            if (this.pages.size() == 0)
                throw new AssertionError("No pages added.");

            return this.pages.get(this.firstPage_Name);
        }

        return this.pages.get(this.defaultPage_Name);
    }

    public PageInfo get(String page_name)
    {
        if (!this.pages.containsKey(page_name))
            throw new AssertionError("PageInfo `" + page_name + "` does not exist.");

        return this.pages.get(page_name);
    }

    public void setDefault(String page_name)
    {
        this.defaultPage_Name = page_name;
    }

}
